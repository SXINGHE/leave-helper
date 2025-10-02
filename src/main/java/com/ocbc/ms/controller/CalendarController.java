package com.ocbc.ms.controller;

import com.ocbc.ms.dto.calendar.SpecialDateSetupRequest;
import com.ocbc.ms.dto.calendar.SpecialDateSetupResponse;
import com.ocbc.ms.entity.CalendarType;
import com.ocbc.ms.entity.SpecialDate;
import com.ocbc.ms.repository.CalendarTypeRepository;
import com.ocbc.ms.service.impl.SpecialDateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Calendar", description = "Calendar and special dates management APIs")
@RestController
@RequestMapping("/api/v1/calendar")
@RequiredArgsConstructor
public class CalendarController {

    @Autowired
    private SpecialDateService specialDateService;

    @Autowired
    private CalendarTypeRepository calendarTypeRepository;


    @Operation(summary = "Get all calendar types", description = "Retrieve all available calendar types")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved calendar types")
    @GetMapping("/calendar-types")
    public ResponseEntity<List<CalendarType>> getAllCalendarType() {
        List<CalendarType> calendarTypeList = calendarTypeRepository.findAll();
        return ResponseEntity.ok(calendarTypeList);
    }

    @Operation(summary = "Get special dates by calendar code", description = "Retrieve holidays and special working days for a specific calendar and year")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved special dates"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping("/special-dates/{calendarCode}")
    public ResponseEntity<List<SpecialDate>> getHolidays(
            @Parameter(description = "Calendar code (e.g., CN for China)") @PathVariable String calendarCode,
            @Parameter(description = "Year to query") @RequestParam(defaultValue = "2024") Integer year) {
        try {
            List<SpecialDate> holidays = specialDateService.getHolidaysByCodeAndYear(calendarCode, year);
            return ResponseEntity.ok(holidays);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Setup calendar special dates", description = "Configure holidays and special working days for a calendar")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully setup calendar"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/setup-calendar")
    public ResponseEntity<SpecialDateSetupResponse> setupCalendar(@Valid @RequestBody SpecialDateSetupRequest request) {
        try {
            return ResponseEntity.ok(specialDateService.setupCalendar(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
