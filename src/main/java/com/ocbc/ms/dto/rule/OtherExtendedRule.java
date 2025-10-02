package com.ocbc.ms.dto.rule;

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
