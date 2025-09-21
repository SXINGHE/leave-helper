package com.ocbc.ms.model;


import lombok.Data;

import java.util.List;

@Data
public class MaternityLeaveDatePolicy {


    private int leaveDays;
    /**
     *
     */
    private boolean delayForPublicHoliday = false;
    /**
     * 目前全部为calendarDay
     * true -> calendarDay
     * false -> workday
     */
    private boolean calendarDay = true;

    /**
     * 难产假特殊规则
     */




    /**
     * 流产假特殊规则
     */
    private List<AbortionRule> abortionRules;
    /**
     * 奖励假特殊规则
     */


}
