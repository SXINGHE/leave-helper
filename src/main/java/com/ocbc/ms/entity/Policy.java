package com.ocbc.ms.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * we should find policy by city name and Company name
 */
@Data
//@Entity
public class Policy {

//    @Id
    private UUID id;

    private String cityName;
    private String corpName;

    private BigDecimal averageCitySalary;
    private BigDecimal averageCorpSalary;

    /**
     * jsonb
     */
    private List<LeaveRules> rulesList;
    /**
     * jsonb
     */
    private AllowanceRules allowanceRules;

}
