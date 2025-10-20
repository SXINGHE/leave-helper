package com.ocbc.ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "Request for calculating maternity leave allowance")
public class MoneyCalculateRequest {

    @Schema(description = "Staff name", example = "Zhang San")
    private String staffName;

    /**
     * drop down list
     */
    @Schema(description = "City name for policy lookup", example = "Guangzhou")
    private String cityCode;
    private String cityName;
    /**
     * E2P/OC
     */
    @Schema(description = "Company name", example = "E2P")
    private String companyName;
    @Schema(description = "Leave start date", example = "2024-03-01")
    private LocalDate leaveStartDate;
    @Schema(description = "Leave end date", example = "2024-06-30")
    private LocalDate leaveEndDate;
    @Schema(description = "Calendar code", example = "CN")
    private String calendarCode;
    @Schema(description = "leave detail", example = "from previous step response")
    private LeaveDetail leaveDetail;
    /**
     * 过去12个月平均月薪
     */
    @Schema(description = "Average salary of past 12 months", example = "8000.00")
    private BigDecimal averageSalary;
    /**
     * 当前月薪
     */
    @Schema(description = "Current monthly salary", example = "9000.00")
    private BigDecimal currentSalary;

    /**
     * when DifferenceCompensationRule.forceCompensation == other
     * will pick this value from request for calculate
     */
    @Schema(description = "Hit force compensation rule flag", example = "false")
    private boolean hitForceCompensationRule;

}
