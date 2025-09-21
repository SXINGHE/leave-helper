package com.ocbc.ms.service.impl;


import com.ocbc.ms.dto.workday.DateCalculationRequest;
import com.ocbc.ms.dto.workday.DateCalculationResponse;
import com.ocbc.ms.dto.workday.HolidayCreateRequest;
import com.ocbc.ms.entity.City;
import com.ocbc.ms.entity.Holiday;
import com.ocbc.ms.repository.CityRepository;
import com.ocbc.ms.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WorkdayCalculatorService {
    
    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private HolidayRepository holidayRepository;
    
    public DateCalculationResponse calculateDate(DateCalculationRequest request) {
        City city = cityRepository.findByCityCode(request.getCityCode())
                .orElseThrow(() -> new RuntimeException("城市代码不存在: " + request.getCityCode()));
        
        LocalDate startDate = request.getStartDate();
        Integer days = request.getDays();
        DateCalculationRequest.DateType dateType = request.getDateType();
        Boolean skipHoliday = request.getSkipHoliday();
        
        List<LocalDate> skippedHolidays = new ArrayList<>();
        List<LocalDate> skippedWeekends = new ArrayList<>();
        
        if (dateType == DateCalculationRequest.DateType.NATURAL) {
            // 自然日计算
            LocalDate endDate = startDate.plusDays(days);
            return new DateCalculationResponse(endDate, days, skippedHolidays, skippedWeekends);
        } else {
            // 工作日计算
            return calculateWorkdays(startDate, days, request.getCityCode(), skipHoliday, skippedHolidays, skippedWeekends);
        }
    }
    
    private DateCalculationResponse calculateWorkdays(LocalDate startDate, Integer days, String cityCode, 
                                                     Boolean skipHoliday, List<LocalDate> skippedHolidays, 
                                                     List<LocalDate> skippedWeekends) {
        LocalDate currentDate = startDate;
        int workdaysAdded = 0;
        int actualDays = 0;
        
        // 获取可能涉及的节假日范围（预估范围，实际可能需要更大）
        LocalDate estimatedEndDate = startDate.plusDays(days * 2); // 预估范围
        List<Holiday> holidays = holidayRepository.findHolidaysByCityAndDateRange(cityCode, startDate, estimatedEndDate);
        List<Holiday> workdays = holidayRepository.findWorkdaysByCityAndDateRange(cityCode, startDate, estimatedEndDate);
        
        // 转换为Set以提高查询效率
        Set<LocalDate> holidayDates = holidays.stream()
                .filter(h -> !h.getIsWorkday())
                .map(Holiday::getHolidayDate)
                .collect(Collectors.toSet());
        
        Set<LocalDate> compensationWorkdays = workdays.stream()
                .map(Holiday::getHolidayDate)
                .collect(Collectors.toSet());
        
        while (workdaysAdded < days) {
            currentDate = currentDate.plusDays(1);
            actualDays++;
            
            boolean isWeekend = isWeekend(currentDate);
            boolean isHoliday = holidayDates.contains(currentDate);
            boolean isCompensationWorkday = compensationWorkdays.contains(currentDate);
            
            // 判断是否为工作日
            boolean isWorkday = !isWeekend && !isHoliday || isCompensationWorkday;
            
            if (isWorkday) {
                workdaysAdded++;
            } else {
                // 记录跳过的日期
                if (isHoliday && !isCompensationWorkday) {
                    if (skipHoliday) {
                        skippedHolidays.add(currentDate);
                    } else {
                        // 如果不顺延，遇到节假日就停止
                        break;
                    }
                } else if (isWeekend && !isCompensationWorkday) {
                    skippedWeekends.add(currentDate);
                }
            }
            
            // 防止无限循环，设置最大计算天数
            if (actualDays > days * 5) {
                throw new RuntimeException("计算超时，请检查参数设置");
            }
        }
        
        DateCalculationResponse response = new DateCalculationResponse(currentDate, actualDays, skippedHolidays, skippedWeekends);
        response.setMessage(String.format("从%s开始，计算%d个工作日后的日期为%s，实际跨越%d天", 
                startDate, days, currentDate, actualDays));
        
        return response;
    }
    
    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
    
    public boolean isWorkday(LocalDate date, String cityCode) {
        // 检查是否为周末
        if (isWeekend(date)) {
            // 检查是否为补班日
            List<Holiday> workdays = holidayRepository.findHolidaysByCityAndDate(cityCode, date);
            return workdays.stream().anyMatch(Holiday::getIsWorkday);
        }
        
        // 检查是否为节假日
        List<Holiday> holidays = holidayRepository.findHolidaysByCityAndDate(cityCode, date);
        boolean isHoliday = holidays.stream().anyMatch(h -> !h.getIsWorkday());
        
        return !isHoliday;
    }
    
    public List<Holiday> getHolidaysByCity(String cityCode, Integer year) {
        return holidayRepository.findHolidaysByCityAndYear(cityCode, year);
    }
    
    public Holiday createHoliday(HolidayCreateRequest request) {
        // 验证城市是否存在
        City city = cityRepository.findByCityCode(request.getCityCode())
                .orElseThrow(() -> new RuntimeException("城市代码不存在: " + request.getCityCode()));
        
        // 检查该日期是否已存在节假日记录
        List<Holiday> existingHolidays = holidayRepository.findHolidaysByCityAndDate(
                request.getCityCode(), request.getHolidayDate());
        
        if (!existingHolidays.isEmpty()) {
            throw new RuntimeException("该城市在此日期已存在节假日记录: " + request.getHolidayDate());
        }
        
        // 创建新的节假日记录
        Holiday holiday = new Holiday();
        holiday.setCity(city);  // 设置City对象而不是cityCode字符串
        holiday.setHolidayDate(request.getHolidayDate());
        holiday.setHolidayName(request.getHolidayName());
        holiday.setIsWorkday(request.getIsWorkday());
        holiday.setYear(request.getHolidayDate().getYear());  // 从日期中提取年份
        
        return holidayRepository.save(holiday);
    }
}
