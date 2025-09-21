package com.ocbc.ms.dto.workday;

import java.time.LocalDate;
import java.util.List;

public class DateCalculationResponse {
    
    private LocalDate endDate;
    private Integer actualDays;
    private List<LocalDate> skippedHolidays;
    private List<LocalDate> skippedWeekends;
    private String message;
    
    // Constructors
    public DateCalculationResponse() {}
    
    public DateCalculationResponse(LocalDate endDate, Integer actualDays, List<LocalDate> skippedHolidays, List<LocalDate> skippedWeekends) {
        this.endDate = endDate;
        this.actualDays = actualDays;
        this.skippedHolidays = skippedHolidays;
        this.skippedWeekends = skippedWeekends;
    }
    
    // Getters and Setters
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    
    public Integer getActualDays() { return actualDays; }
    public void setActualDays(Integer actualDays) { this.actualDays = actualDays; }
    
    public List<LocalDate> getSkippedHolidays() { return skippedHolidays; }
    public void setSkippedHolidays(List<LocalDate> skippedHolidays) { this.skippedHolidays = skippedHolidays; }
    
    public List<LocalDate> getSkippedWeekends() { return skippedWeekends; }
    public void setSkippedWeekends(List<LocalDate> skippedWeekends) { this.skippedWeekends = skippedWeekends; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
