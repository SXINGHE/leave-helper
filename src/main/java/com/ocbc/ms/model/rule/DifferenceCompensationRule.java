package com.ocbc.ms.model.rule;

import lombok.Data;

import java.util.List;

@Data
public class DifferenceCompensationRule {


    private String ruleDescription;

    /**
     * Yes/No/Others
     */
    private String forceCompensation;

    /**
     * if others
     * will return this description back to MFE in maternity leave calculate response
     * mfe will display this description to user in money calculate page
     */
    private List<String> otherCompensationRuleDesc;



}
