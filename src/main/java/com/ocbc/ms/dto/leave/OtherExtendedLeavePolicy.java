package com.ocbc.ms.dto.leave;

import com.ocbc.ms.dto.rule.OtherExtendedRule;
import lombok.Data;

import java.util.List;

@Data
public class OtherExtendedLeavePolicy extends LeavePolicy {

    private int standardLeaveDays;

    private List<OtherExtendedRule> otherExtendedRules;
}
