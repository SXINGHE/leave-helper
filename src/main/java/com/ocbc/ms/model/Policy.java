package com.ocbc.ms.model;

import lombok.Data;

import java.util.List;

/**
 * we should find policy by city name and Company name
 */
@Data
public class Policy {

    private String cityName;
    private String companyName;

    private List<MaternityLeaveDatePolicy> maternityLeavePolicy;

}
