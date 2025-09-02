package com.ocbc.ms.service;

import com.ocbc.ms.constants.DayTypeEnum;

import java.time.LocalDate;

public interface CalendarService {

    LocalDate getEndDate(LocalDate startDate, DayTypeEnum dayTypeEnum, boolean delayForPublicHoliday);

    void updateCalendar();
}
