package com.ocbc.ms.dto.leave;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class LeavePolicy {

    /**
     * 公休日是否延迟
     */
    @Schema(description = "遇到公共假期是否顺延", example = "false")
    private boolean delayForPublicHoliday = false;
    /**
     * 目前全部为calendarDay
     * true -> calendarDay
     * false -> workday
     */
    @Schema(description = "是否为日历日？目前所有城市均为日历日", example = "true")
    private boolean calendarDay = true;
}
