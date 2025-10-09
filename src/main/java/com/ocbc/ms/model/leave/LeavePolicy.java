package com.ocbc.ms.dto.leave;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class LeavePolicy {

    /**
     * 公休日是否延迟
     */
    @Schema(description = "遇到公共机器是否顺延", example = "false")
    private boolean delayForPublicHoliday = false;
    /**
     * 目前全部为calendarDay
     * true -> calendarDay
     * false -> workday
     */
    @Schema(description = "是否为日历日？目前所有城市均为日历日", example = "true")
    private boolean calendarDay = true;

    /**
     * 目前仅厦门有最大产假天数，为180天
     */
    @Schema(description = "最大产假天数，目前仅厦门有此值", example = "180")
    private Integer maxLeaveDays;
}
