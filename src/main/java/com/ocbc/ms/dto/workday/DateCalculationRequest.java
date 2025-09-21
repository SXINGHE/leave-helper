package com.ocbc.ms.dto.workday;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public class DateCalculationRequest {
    
    @NotNull(message = "起始日期不能为空")
    private LocalDate startDate;
    
    @NotNull(message = "天数不能为空")
    @Positive(message = "天数必须为正数")
    private Integer days;
    
    @NotNull(message = "日期类型不能为空")
    private DateType dateType;
    
    @NotNull(message = "是否顺延参数不能为空")
    private Boolean skipHoliday;
    
    private String cityCode = "BJ"; // 默认北京
    
    public enum DateType {
        NATURAL("自然日"),
        WORKDAY("工作日");
        
        private final String description;
        
        DateType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    // Constructors
    public DateCalculationRequest() {}
    
    public DateCalculationRequest(LocalDate startDate, Integer days, DateType dateType, Boolean skipHoliday) {
        this.startDate = startDate;
        this.days = days;
        this.dateType = dateType;
        this.skipHoliday = skipHoliday;
    }
    
    // Getters and Setters
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public Integer getDays() { return days; }
    public void setDays(Integer days) { this.days = days; }
    
    public DateType getDateType() { return dateType; }
    public void setDateType(DateType dateType) { this.dateType = dateType; }
    
    public Boolean getSkipHoliday() { return skipHoliday; }
    public void setSkipHoliday(Boolean skipHoliday) { this.skipHoliday = skipHoliday; }
    
    public String getCityCode() { return cityCode; }
    public void setCityCode(String cityCode) { this.cityCode = cityCode; }
}
