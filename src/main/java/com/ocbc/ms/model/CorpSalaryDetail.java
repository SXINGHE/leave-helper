package com.ocbc.ms.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CorpSalaryDetail {

    private String companyName;
    private BigDecimal corpAverageSalary;

}
