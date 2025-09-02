package com.ocbc.ms.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AllowanceRules {

    /**
     * eg. ShangHai ---> 3 * average salary
     */
    private BigDecimal maxCitySalary;

    private BigDecimal socialSecurityRate;

    private BigDecimal cpfRate;

}
