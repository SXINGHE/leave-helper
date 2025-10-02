package com.ocbc.ms.dto.leave;

import com.ocbc.ms.dto.rule.DystociaRule;
import lombok.Data;

import java.util.List;

@Data
public class DystociaLeavePolicy extends LeavePolicy{

    /**
     * 标准难产假天数
     */
    private int standardLeaveDays;

    /**
     * 难产假特殊规则
     */
    private List<DystociaRule> dystociaRules;
}
