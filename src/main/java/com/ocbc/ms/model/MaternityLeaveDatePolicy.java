package com.ocbc.ms.model;


import com.ocbc.ms.model.rule.AbortionRule;
import com.ocbc.ms.model.rule.DystociaRule;
import com.ocbc.ms.model.rule.OtherExtendedRule;
import lombok.Data;

import java.util.List;

@Data
public class MaternityLeaveDatePolicy {


    private int leaveDays;
    /**
     *
     */
    private boolean delayForPublicHoliday = false;
    /**
     * 目前全部为calendarDay
     * true -> calendarDay
     * false -> workday
     */
    private boolean calendarDay = true;




    /**
     * 流产假特殊规则
     */
    private List<AbortionRule> abortionRules;

    /**
     * 难产假特殊规则
     */
    private List<DystociaRule> dystociaRules;
    /**
     * 奖励假特殊规则，可以为空
     */
    private List<OtherExtendedRule> otherExtendedRules;

    /**
     * 目前仅厦门有最大产假天数，为180天
     */
    private int maxLeaveDays;
}
