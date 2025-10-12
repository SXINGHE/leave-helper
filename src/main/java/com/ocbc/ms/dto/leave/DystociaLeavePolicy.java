package com.ocbc.ms.dto.leave;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocbc.ms.dto.rule.LeaveRule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "难产假政策数据")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DystociaLeavePolicy extends LeavePolicy{

    /**
     * 标准难产假天数
     */
    @Schema(description = "标准难产假天数", example = "15")
    private int standardLeaveDays;

    /**
     * 难产假特殊规则
     */
    @Schema(description = "难产假特殊规则")
    private List<LeaveRule> dystociaRules;
}
