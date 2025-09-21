package com.ocbc.ms.model;


import lombok.Data;

@Data
public class MaternityLeaveDatePolicy {


    private int leaveDays;

    /**
     * true -> workDay
     * false -> calendarDay
     */
    private boolean isWorkDay;

    /**
     * priority of the policy
     * 0 - highest
     * 10 - lowest
     */
    private int priority;

    private String leaveType;
    private String description;


}
