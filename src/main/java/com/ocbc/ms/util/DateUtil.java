package com.ocbc.ms.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateUtil {


    /**
     * Todo 添加工作日计算
    */
    public LocalDate getEndDate(LocalDate startDate, int leaveDays, boolean isCalendarDay) {
        return startDate.plusDays(leaveDays);
    }

    /**
     * Todo 添加工作日计算
     */
    public LocalDate getEndDate(LocalDate startDate, int leaveDays, boolean isCalendarDay, boolean delayForPublicHoliday) {
        if (delayForPublicHoliday) {
            /*
                 计算公休后再计算
             */
            return startDate.plusDays(leaveDays);
        } else {
            return startDate.plusDays(leaveDays);
        }
    }

}
