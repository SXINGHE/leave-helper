package com.ocbc.ms.model;


import lombok.Data;

@Data
public class MaternityLeaveDatePolicy {


    private int leaveDays;

    /**
     *
     */
    private boolean delayForPublicHoliday;

    /**
     * 目前全部为calendarDay
     * true -> calendarDay
     * false -> workday
     */
    private boolean calendarDay;

    /**
     * priority of the policy
     * 0 - highest
     * 10 - lowest
     */
    private int priority;

    private String leaveType;
    private String description;


}
