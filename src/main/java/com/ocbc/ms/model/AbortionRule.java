package com.ocbc.ms.model;

import lombok.Data;

@Data
public class AbortionRule {

    /*
     * 是否为宫外孕规则 默认为false
     * 当前仅重庆有此规则
     */
    private boolean ectopicPregnancy;

    private int minRegnancyDays;
    private int maxRegnancyDays;

    private int leaveDays;

}
