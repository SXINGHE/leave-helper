package com.ocbc.ms.service.impl;


import com.ocbc.ms.dto.calendar.SpecialDateSetupRequest;
import com.ocbc.ms.dto.calendar.SpecialDateSetupResponse;
import com.ocbc.ms.entity.SpecialDate;
import com.ocbc.ms.repository.SpecialDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        SpecialDateSetupResponse response = (SpecialDateSetupResponse) request;
        deletedByHolidaysByCodeAndYear(request.getCalendarCode(), request.getYear());
        List<SpecialDate> newRecords = new ArrayList<>();

        newRecords.addAll(request.getPublicHolidays()
                .stream().map(holiday -> {
                    SpecialDate specialDate = new SpecialDate();
                    specialDate.setCalendarCode(request.getCalendarCode());
                    specialDate.setCalendarDate(holiday.getCalendarDate());
                    specialDate.setDescription(holiday.getDescription());
                    specialDate.setYear(request.getYear());
                    specialDate.setIsWorkday(false);
                    return specialDate;
                })
                .toList());
        newRecords.addAll(request.getExtraWorkdays()
                .stream().map(extraWorkday -> {
                    SpecialDate specialDate = new SpecialDate();
                    specialDate.setCalendarCode(request.getCalendarCode());
                    specialDate.setCalendarDate(extraWorkday.getCalendarDate());
                    specialDate.setDescription(extraWorkday.getDescription());
                    specialDate.setYear(request.getYear());
                    specialDate.setIsWorkday(false);
                    return specialDate;
                })
                .toList());
        specialDateRepository.saveAll(newRecords);
        return response;
    }
}
