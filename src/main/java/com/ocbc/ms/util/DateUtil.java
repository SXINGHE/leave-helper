package com.ocbc.ms.util;

import com.ocbc.ms.entity.SpecialDate;
import com.ocbc.ms.repository.SpecialDateRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

}
