package com.ocbc.ms.dto.allowance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema
public class CorpSalaryDetail {

    private String companyName;
    private BigDecimal corpAverageSalary;

}
