package com.ocbc.ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Allowance calculation details")
public class AllowanceDetail {

    @Schema(description = "Total allowance amount", example = "24000.00")
    private BigDecimal allowance;

    @Schema(description = "Compensation amount", example = "3000.00")
    private BigDecimal compensation;

    @Schema(description = "First month salary", example = "8000.00")
    private BigDecimal firstMonthSalary;
    @Schema(description = "Last month salary", example = "8000.00")
    private BigDecimal lastMonthSalary;
    @Schema(description = "Other months salary", example = "16000.00")
    private BigDecimal otherMonthSalary;
    @Schema(description = "Total salary during leave", example = "32000.00")
    private BigDecimal totalSalary;

}
