package com.ocbc.ms.service.impl;

import com.ocbc.ms.constants.DayTypeEnum;
import com.ocbc.ms.service.CalendarService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CalendarServiceImpl implements CalendarService {
    @Override
    public LocalDate getEndDate(LocalDate startDate, DayTypeEnum dayTypeEnum, boolean delayForPublicHoliday) {
        return null;
    }

    /**
     * let wind surf help on this later
     */
    @Override
    public void updateCalendar() {

        /**
         * create task to update history data or just mark for history data
         */
    }
}
