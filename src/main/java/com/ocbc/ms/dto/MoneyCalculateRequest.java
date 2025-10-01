package com.ocbc.ms.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class MoneyCalculateRequest {

    private String staffName;

    /**
     * drop down list
     */
    private String cityName;
    /**
     * E2P/OC
     */
    private String companyName;
    private LocalDate leaveStartDate;
    private LocalDate leaveEndDate;
    private String calendarCode;
    /**
     * 过去12个月平均月薪
     */
    private BigDecimal averageSalary;

    /**
     * when DifferenceCompensationRule.forceCompensation == other
     * will pick this value from request for calculate
     */
    private boolean hitForceCompensationRule;

}
