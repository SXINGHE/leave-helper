package com.ocbc.ms.controller;

import com.ocbc.ms.dto.calendar.SpecialDateSetupRequest;
import com.ocbc.ms.dto.calendar.SpecialDateSetupResponse;
import com.ocbc.ms.entity.CalendarType;
import com.ocbc.ms.entity.SpecialDate;
import com.ocbc.ms.repository.CalendarTypeRepository;
import com.ocbc.ms.service.impl.SpecialDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * check if we can find any opensource lib,
 * if no such lib, then we need to build calendar config page
 */
@RestController
@RequestMapping("/api/v1/calendar")
@RequiredArgsConstructor
public class CalendarController {

    @Autowired
    private SpecialDateService specialDateService;

    @Autowired
    private CalendarTypeRepository calendarTypeRepository;


    @GetMapping("/calendar-types")
    public ResponseEntity<List<CalendarType>> getAllCalendarType() {
        List<CalendarType> calendarTypeList = calendarTypeRepository.findAll();
        return ResponseEntity.ok(calendarTypeList);
    }

    @GetMapping("/special-dates/{calendarCode}")
    public ResponseEntity<List<SpecialDate>> getHolidays(@PathVariable String calendarCode,
                                                         @RequestParam(defaultValue = "2024") Integer year) {
        try {
            List<SpecialDate> holidays = specialDateService.getHolidaysByCodeAndYear(calendarCode, year);
            return ResponseEntity.ok(holidays);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/setup-calendar")
    public ResponseEntity<SpecialDateSetupResponse> setupCalendar(@Valid @RequestBody SpecialDateSetupRequest request) {
        try {
            return ResponseEntity.ok(specialDateService.setupCalendar(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
