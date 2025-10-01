package com.ocbc.ms.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AllowanceDetail {

    private BigDecimal allowance;

    private BigDecimal compensation;

}
