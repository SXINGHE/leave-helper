package com.ocbc.ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Leave date and duration details")
public class LeaveDetail {

    @Schema(description = "Leave start date", example = "2024-03-01")
    private LocalDate leaveStartDate;

    @Schema(description = "Leave end date", example = "2024-06-30")
    private LocalDate leaveEndDate;

    @Schema(description = "Total leave days", example = "98")
    private Integer currentLeaveDays = 0;

    private int abortionLeaveDays;
    private int statutoryLeaveDays;
    private int dystociaLeaveDays;
    private int moreInfantLeaveDays;
    private int otherExtendedLeaveDays;
    private int totalLeaveDays;


}
