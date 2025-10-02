package com.ocbc.ms.dto.leave;

import com.ocbc.ms.dto.rule.LeaveRule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema
public class OtherExtendedLeavePolicy extends LeavePolicy {

    @Schema(description = "其他延长产假天数", example = "10")
    private int standardLeaveDays;
    @Schema(description = "其他延长产假规则列表,默认为空")
    private List<LeaveRule> otherExtendedRules;
}
