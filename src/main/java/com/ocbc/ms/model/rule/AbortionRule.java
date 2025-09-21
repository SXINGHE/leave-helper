package com.ocbc.ms.model.rule;

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

    /**
     * TODO
     * 当计算请求的recommendAbortionLeaveDays 不为空时使用
     */
    private int minLeaveDays;
    /**
     * TODO
     * 当计算请求的recommendAbortionLeaveDays 不为空时使用
     */
    private int maxLeaveDays;
    /**
     * TODO
     * 当计算请求的recommendAbortionLeaveDays为空时使用
     */
    private int leaveDays;

}
