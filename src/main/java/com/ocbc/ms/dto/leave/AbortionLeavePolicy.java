package com.ocbc.ms.dto.leave;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ocbc.ms.dto.rule.LeaveRule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "流产假政策数据")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbortionLeavePolicy extends LeavePolicy {

    @Schema(description = "流产假规则列表")
    private List<LeaveRule> abortionRules;

}
