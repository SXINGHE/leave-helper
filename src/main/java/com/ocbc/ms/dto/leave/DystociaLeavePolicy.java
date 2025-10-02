package com.ocbc.ms.dto.leave;

import com.ocbc.ms.dto.rule.DystociaRule;
import lombok.Data;

import java.util.List;

@Data
public class DystociaLeavePolicy extends LeavePolicy{


    /**
     * 难产假特殊规则
     */
    private List<DystociaRule> dystociaRules;
}
