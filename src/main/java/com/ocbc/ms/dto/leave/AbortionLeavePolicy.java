package com.ocbc.ms.dto.leave;

import com.ocbc.ms.dto.rule.AbortionRule;
import lombok.Data;

import java.util.List;

@Data
public class AbortionLeavePolicy extends LeavePolicy {

    private List<AbortionRule> abortionRules;

}
