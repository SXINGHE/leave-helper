package com.ocbc.ms.dto.leave;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "多胎产假政策数据")
public class MoreInfantLeavePolicy extends LeavePolicy {
    /**
     * 额外多胎产假天数
     */
    @Schema(description = "额外多胎产假天数", example = "10")
    private int extraInfantLeaveDays;
}
