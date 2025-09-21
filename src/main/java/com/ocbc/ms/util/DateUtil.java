package com.ocbc.ms.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateUtil {


    public LocalDate getEndDate(LocalDate startDate, int leaveDays, boolean isCalendarDay) {
        if (isCalendarDay) {
            return startDate.plusDays(leaveDays);
        } else {
            /*
                Todo
             */
            return startDate.plusDays(leaveDays - 1);
        }
    }

}
