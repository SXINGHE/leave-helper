package com.ocbc.ms.util;

import com.ocbc.ms.entity.SpecialDate;
import com.ocbc.ms.repository.SpecialDateRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DateUtil {

    @Resource
    SpecialDateRepository specialDateRepository;

    /**
     * Todo 添加工作日计算
     */
    public LocalDate getEndDate(LocalDate startDate, int leaveDays, boolean isCalendarDay) {
        return startDate.plusDays(leaveDays);
    }

    /**
     * Todo 添加工作日计算
     */
    public LocalDate getEndDate(LocalDate startDate, int leaveDays, boolean isCalendarDay, boolean delayForPublicHoliday, String calendarCode) {
        if (delayForPublicHoliday) {
            var specialDates = specialDateRepository.getAllHolidayByStartDate(calendarCode, startDate);
            List<LocalDate> publicHolidays = specialDates.stream().filter(specialDate -> !specialDate.getIsWorkday())
                    .map(SpecialDate::getCalendarDate).toList(); // 你需要实现这个方法
            return calculateEndDateWithHolidayDelay(startDate, leaveDays, publicHolidays);
        } else {
            return startDate.plusDays(leaveDays);
        }
    }

    /**
     * 确认假期顺延规则
     *
     * @return 顺延公共假期后结束日期
     */
    public LocalDate calculateEndDateWithHolidayDelay(LocalDate startDate, int leaveDays, List<LocalDate> publicHolidays) {
        // 转换为Set以提高查询效率
        Set<LocalDate> holidaySet = new HashSet<>(publicHolidays);
        LocalDate currentDate = startDate;
        int remainingDays = leaveDays;
        while (remainingDays > 0) {
            currentDate = currentDate.plusDays(1);
            // 如果当前日期不是公共假期，则减少剩余天数
            if (!holidaySet.contains(currentDate)) {
                remainingDays--;
            }
        }
        return currentDate;
    }

    /**
     * 获取一个月的最后一天
     */
    public LocalDate getLastDayOfMonth(LocalDate date) {
        return date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));
    }

    /**
     * 获取一个月的第一天
     */
    public LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.withDayOfMonth(1);
    }

    /**
     * 计算两个日期之间的完整月份数量。
     * 规则：不包含开始月份；不包含结束月份（即使 endDate 为该月最后一天也不计入）。
     * 例如：2025-09-16 到 2025-11-07 => 1（仅 10 月）。
     */
    public int getFullMonthCount(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        if (startDate.isAfter(endDate)) {
            return 0;
        }

        // 第一个完整月份从“开始日期所在月的下一个月的第一天”开始
        LocalDate firstFullMonthStart = getFirstDayOfMonth(startDate).plusMonths(1);

        // 永远不包含结束月份
        LocalDate lastBoundary = getFirstDayOfMonth(endDate);

        // 计算月份差值（以月初为基准），若为负则返回 0
        int months = (lastBoundary.getYear() - firstFullMonthStart.getYear()) * 12
                + (lastBoundary.getMonthValue() - firstFullMonthStart.getMonthValue());
        return Math.max(0, months);
    }

    public int getRealSalaryDays(LocalDate startDate, LocalDate endDate, String calendarCode) {
        int initSalaryDays = getInitSalaryDays(startDate, endDate);
        int extraWorkDays = getExtraWorkDays(startDate, endDate, calendarCode);
        int holidayDays = getHolidayDays(startDate, endDate, calendarCode);
        return initSalaryDays + extraWorkDays - holidayDays;
    }

    private int getExtraWorkDays(LocalDate startDate, LocalDate endDate, String calendarCode) {
        List<SpecialDate> extraWorkDays = specialDateRepository.findExtraWorkDaysByCalendarCodeAndYear(calendarCode, startDate, endDate);
        if (CollectionUtils.isEmpty(extraWorkDays)) {
            return 0;
        }
        return extraWorkDays.size();
    }

    private int getHolidayDays(LocalDate startDate, LocalDate endDate, String calendarCode) {
        List<SpecialDate> holidays = specialDateRepository.findHolidayByCalendarCodeAndYear(calendarCode, startDate, endDate);
        if (CollectionUtils.isEmpty(holidays)) {
            return 0;
        }
        var realHolidays = holidays.stream().filter(specialDate -> !isDefaultWorkday(specialDate.getCalendarDate())).toList();
        return realHolidays.size();
    }


    private boolean isDefaultWorkday(LocalDate date) {
        DayOfWeek dow = date.getDayOfWeek();
        return dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY;
    }


    public int getInitSalaryDays(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        if (startDate.isAfter(endDate)) {
            return 0;
        }
        int workdays = 0;
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) { // inclusive
            if (isDefaultWorkday(date)) {
                workdays++;
            }
            date = date.plusDays(1);
        }
        return workdays;
    }


}
