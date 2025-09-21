package com.ocbc.ms.dto.workday;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class HolidayCreateRequest {
    
    @NotNull(message = "节假日日期不能为空")
    private LocalDate holidayDate;
    
    @NotBlank(message = "节假日名称不能为空")
    private String holidayName;
    
    @NotNull(message = "是否为工作日标识不能为空")
    private Boolean isWorkday;
    
    @NotBlank(message = "城市代码不能为空")
    private String cityCode;
    
    // Constructors
    public HolidayCreateRequest() {}
    
    public HolidayCreateRequest(LocalDate holidayDate, String holidayName, Boolean isWorkday, String cityCode) {
        this.holidayDate = holidayDate;
        this.holidayName = holidayName;
        this.isWorkday = isWorkday;
        this.cityCode = cityCode;
    }
    
    // Getters and Setters
    public LocalDate getHolidayDate() {
        return holidayDate;
    }
    
    public void setHolidayDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }
    
    public String getHolidayName() {
        return holidayName;
    }
    
    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }
    
    public Boolean getIsWorkday() {
        return isWorkday;
    }
    
    public void setIsWorkday(Boolean isWorkday) {
        this.isWorkday = isWorkday;
    }
    
    public String getCityCode() {
        return cityCode;
    }
    
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
