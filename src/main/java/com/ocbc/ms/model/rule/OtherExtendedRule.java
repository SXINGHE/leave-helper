package com.ocbc.ms.model.rule;

import lombok.Data;

@Data
public class OtherExtendedRule {

    /**
     * 第几胎？
     */
    private int minDeliverySequence;
    private int leaveDays;
    private String description;
}
