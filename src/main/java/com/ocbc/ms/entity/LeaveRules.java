package com.ocbc.ms.entity;

import com.ocbc.ms.constants.DayTypeEnum;
import lombok.Data;

@Data
public class LeaveRules {

    /**
     * from high to low
     *       0  --> 10
     */
    private int priority;

    private DayTypeEnum dayType;

    private boolean delayForPublicHoliday;

    /**
     * 多少天
     */
    private int quantity;

    /**
     * 根据输入判断是否命中此rule （难产假/多胎假等）
     * 如果为空则不需要进行此判断
     */
    private String hitRules;

}
