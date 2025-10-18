package com.ocbc.ms.service.impl;


import com.ocbc.ms.dto.calendar.SpecialDateSetupRequest;
import com.ocbc.ms.dto.calendar.SpecialDateSetupResponse;
import com.ocbc.ms.entity.SpecialDate;
import com.ocbc.ms.repository.SpecialDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecialDateService {

    @Autowired
    private SpecialDateRepository specialDateRepository;

    public List<SpecialDate> getHolidaysByCodeAndYear(String calendarCode, Integer year) {
        return specialDateRepository.findByCalendarCodeAndYear(calendarCode, year);
    }

    public void deletedByHolidaysByCodeAndYear(String calendarCode, Integer year) {
        var records = specialDateRepository.findByCalendarCodeAndYear(calendarCode, year);
        records.forEach(record -> record.setIsActive(false));
        specialDateRepository.saveAll(records);
    }

    public SpecialDateSetupResponse setupCalendar(SpecialDateSetupRequest request) {
        /*
            step1. find all record by calendar type and year
            step2. set step1 records is_active to false
            step3. insert new records
         */
        SpecialDateSetupResponse response = new SpecialDateSetupResponse();
        response.setCalendarCode(request.getCalendarCode());
        response.setExtraWorkdays(request.getExtraWorkdays());
        response.setPublicHolidays(request.getPublicHolidays());
        List<SpecialDate> upsertRecords = new ArrayList<>();

        if (!CollectionUtils.isEmpty(request.getPublicHolidays())) {
            request.getPublicHolidays().forEach(holiday -> {
                var dateOpt = specialDateRepository.findByCalendarCodeAndDate(request.getCalendarCode(), holiday.getCalendarDate());
                if (dateOpt.isPresent()) {
                    var date = dateOpt.get();
                    date.setIsActive(true);
                    date.setDescription(holiday.getDescription());
                    date.setYear(holiday.getCalendarDate().getYear());
                    date.setIsWorkday(false);
                    upsertRecords.add(date);
                } else {
                    SpecialDate specialDate = new SpecialDate();
                    specialDate.setCalendarCode(request.getCalendarCode());
                    specialDate.setCalendarDate(holiday.getCalendarDate());
                    specialDate.setDescription(holiday.getDescription());
                    specialDate.setYear(holiday.getCalendarDate().getYear());
                    specialDate.setIsWorkday(false);
                    upsertRecords.add(specialDate);
                }
            });
        }

        if (!CollectionUtils.isEmpty(request.getExtraWorkdays())) {
            request.getExtraWorkdays().forEach(workday -> {
                var dateOpt = specialDateRepository.findByCalendarCodeAndDate(request.getCalendarCode(), workday.getCalendarDate());
                if (dateOpt.isPresent()) {
                    var date = dateOpt.get();
                    date.setIsActive(true);
                    date.setDescription(workday.getDescription());
                    date.setYear(workday.getCalendarDate().getYear());
                    date.setIsWorkday(true);
                    upsertRecords.add(date);
                } else {
                    SpecialDate specialDate = new SpecialDate();
                    specialDate.setCalendarCode(request.getCalendarCode());
                    specialDate.setCalendarDate(workday.getCalendarDate());
                    specialDate.setDescription(workday.getDescription());
                    specialDate.setYear(workday.getCalendarDate().getYear());
                    specialDate.setIsWorkday(true);
                    upsertRecords.add(specialDate);
                }
            });
        }
        specialDateRepository.saveAll(upsertRecords);
        return response;
    }


}
