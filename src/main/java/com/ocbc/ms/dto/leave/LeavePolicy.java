package com.ocbc.ms.dto.leave;

import lombok.Data;

@Data
public class LeavePolicy {

    /**
     * 公休日是否延迟
     */
    private boolean delayForPublicHoliday = false;
    /**
     * 目前全部为calendarDay
     * true -> calendarDay
     * false -> workday
     */
    private boolean calendarDay = true;

    /**
     * 目前仅厦门有最大产假天数，为180天
     */
    private Integer maxLeaveDays;
}
