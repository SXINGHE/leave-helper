package com.ocbc.ms.controller;

import com.ocbc.ms.dto.workday.DateCalculationRequest;
import com.ocbc.ms.dto.workday.DateCalculationResponse;
import com.ocbc.ms.dto.workday.HolidayCreateRequest;
import com.ocbc.ms.entity.City;
import com.ocbc.ms.entity.Holiday;
import com.ocbc.ms.repository.CityRepository;
import com.ocbc.ms.service.impl.WorkdayCalculatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/workday")
@CrossOrigin(origins = "*")
public class WorkdayController {
    
    @Autowired
    private WorkdayCalculatorService workdayCalculatorService;
    
    @Autowired
    private CityRepository cityRepository;
    
    @PostMapping("/calculate")
    public ResponseEntity<DateCalculationResponse> calculateDate(@Valid @RequestBody DateCalculationRequest request) {
        try {
            DateCalculationResponse response = workdayCalculatorService.calculateDate(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            DateCalculationResponse errorResponse = new DateCalculationResponse();
            errorResponse.setMessage("计算失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @GetMapping("/cities")
    public ResponseEntity<List<City>> getCities() {
        List<City> cities = cityRepository.findAll();
        return ResponseEntity.ok(cities);
    }
    
    @GetMapping("/holidays/{cityCode}")
    public ResponseEntity<List<Holiday>> getHolidays(@PathVariable String cityCode,
                                                     @RequestParam(defaultValue = "2024") Integer year) {
        try {
            List<Holiday> holidays = workdayCalculatorService.getHolidaysByCity(cityCode, year);
            return ResponseEntity.ok(holidays);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/isWorkday")
    public ResponseEntity<Boolean> isWorkday(@RequestParam String date, 
                                           @RequestParam(defaultValue = "BJ") String cityCode) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            boolean isWorkday = workdayCalculatorService.isWorkday(localDate, cityCode);
            return ResponseEntity.ok(isWorkday);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/holidays")
    public ResponseEntity<Holiday> createHoliday(@Valid @RequestBody HolidayCreateRequest request) {
        try {
            Holiday holiday = workdayCalculatorService.createHoliday(request);
            return ResponseEntity.ok(holiday);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
