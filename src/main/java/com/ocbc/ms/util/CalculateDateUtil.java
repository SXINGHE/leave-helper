package com.ocbc.ms.util;

import com.ocbc.ms.dto.CalculateComments;
import com.ocbc.ms.dto.CalculateResponse;
import com.ocbc.ms.dto.DateCalculateRequest;
import com.ocbc.ms.dto.LeaveDetail;
import com.ocbc.ms.dto.leave.*;
import com.ocbc.ms.dto.rule.AbortionRule;
import com.ocbc.ms.dto.rule.OtherExtendedRule;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

@Component
public class CalculateDateUtil {

    @Resource
    DateUtil dateUtil;


    private void updateCurrentLeaveDays(LeaveDetail leaveDetail, LocalDate leaveStartDate) {
        leaveDetail.setCurrentLeaveDays(Math.toIntExact(leaveDetail.getCurrentLeaveDays() + ChronoUnit.DAYS.between(leaveStartDate, leaveDetail.getLeaveEndDate())));
    }


    /**
     * 计算法定产假
     */
    public void calculateStatutoryLeave(LeaveDetail leaveDetail, CalculateComments calculateComments,
                                         StatutoryLeavePolicy statutoryPolicy) {
        LocalDate leaveStartDate = leaveDetail.getLeaveEndDate();
        leaveDetail.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, statutoryPolicy.getLeaveDays(), statutoryPolicy.isCalendarDay()));
        updateCurrentLeaveDays(leaveDetail, leaveStartDate);

        calculateComments.getDescriptionList().add("1.法定产假，天数：" + statutoryPolicy.getLeaveDays());
        calculateComments.getDescriptionList().add("1.法定产假，开始日：" + leaveStartDate +  "结束日：" + leaveDetail.getLeaveEndDate());
    }

    /**
     * 计算难产假
     */
    public void calculateDystociaLeave(DateCalculateRequest request, LeaveDetail leaveDetail,CalculateComments calculateComments,
                                        DystociaLeavePolicy dystociaPolicy) {

        if (CollectionUtils.isEmpty(request.getDystociaCodeList())) {
            LocalDate leaveStartDate = leaveDetail.getLeaveEndDate();
            leaveDetail.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, dystociaPolicy.getStandardLeaveDays(), dystociaPolicy.isCalendarDay()));
            updateCurrentLeaveDays(leaveDetail, leaveStartDate);
            calculateComments.getDescriptionList().add("2.难产假天数" + dystociaPolicy.getStandardLeaveDays());
            calculateComments.getDescriptionList().add("2.难产假 (标准难产假) 开始日：" + leaveStartDate +  "结束日：" + leaveDetail.getLeaveEndDate());
        } else {
            var dystociaRules = dystociaPolicy.getDystociaRules().stream().filter(rule -> request.getDystociaCodeList().contains(rule.getDystociaCode())).toList();
            dystociaRules.forEach(rule -> {
                LocalDate leaveStartDate = leaveDetail.getLeaveEndDate();
                leaveDetail.setLeaveEndDate(dateUtil.getEndDate(leaveDetail.getLeaveEndDate(), rule.getLeaveDays(), dystociaPolicy.isCalendarDay()));
                updateCurrentLeaveDays(leaveDetail, leaveStartDate);
                calculateComments.getDescriptionList().add("2.难产假天数" + rule.getLeaveDays());
                calculateComments.getDescriptionList().add("2.难产假 ("+ rule.getDescription() + ") 开始日：" + leaveStartDate +  "结束日：" + leaveDetail.getLeaveEndDate());
            });
        }


    }

    /**
     * 计算多胎假
     */
    public void calculateMoreInfantLeave(DateCalculateRequest request, LeaveDetail leaveDetail,CalculateComments calculateComments, MoreInfantLeavePolicy moreInfantPolicy) {
        LocalDate leaveStartDate = leaveDetail.getLeaveEndDate();
        leaveDetail.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, moreInfantPolicy.getExtraInfantLeaveDays() * (request.getInfantNumber() - 1), moreInfantPolicy.isCalendarDay()));
        updateCurrentLeaveDays(leaveDetail, leaveStartDate);
        calculateComments.getDescriptionList().add("3.多胎假，每多生育一胎奖励：" + moreInfantPolicy.getExtraInfantLeaveDays() +  "天，当前生育胎数为：" + request.getInfantNumber());
        calculateComments.getDescriptionList().add("3.多胎假，开始日：" + leaveStartDate +  "结束日：" + leaveDetail.getLeaveEndDate());
    }



    /**
     * 计算流产假
     * TODO recommendAbortionLeaveDays
     * TODO 添加最大天数校验
     */
    public void calculateAbortionLeave(DateCalculateRequest request, LeaveDetail leaveDetail, CalculateComments calculateComments,
                                        AbortionLeavePolicy abortionPolicy) {
        var abortionRules = abortionPolicy.getAbortionRules();
        LocalDate leaveStartDate = leaveDetail.getLeaveEndDate();
        if (request.isEctopicPregnancy() && abortionRules.stream().anyMatch(AbortionRule::isEctopicPregnancy)) {
            /*
             * 宫外孕计算优先级最高
             */
            var ectopicPregnancyRule = abortionRules.stream().filter(AbortionRule::isEctopicPregnancy).findFirst().get();
            leaveDetail.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, ectopicPregnancyRule.getLeaveDays(), abortionPolicy.isCalendarDay()));
            updateCurrentLeaveDays(leaveDetail, leaveStartDate);
            calculateComments.getDescriptionList().add("1.流产假（宫外孕），开始日：" + leaveStartDate +  "结束日：" + leaveDetail.getLeaveEndDate());
        } else {
            /*
             * 按妊娠天数来计算
             */
            var pregnancyRuleOpt = abortionRules.stream().filter(rule -> request.getRegnancyDays() >= rule.getMinRegnancyDays() && request.getRegnancyDays() <= rule.getMaxRegnancyDays())
                    .findFirst();
            if (pregnancyRuleOpt.isPresent()) {
                var pregnancyRule = pregnancyRuleOpt.get();
                leaveDetail.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, pregnancyRule.getLeaveDays(), abortionPolicy.isCalendarDay()));
                updateCurrentLeaveDays(leaveDetail, leaveStartDate);
                calculateComments.getDescriptionList().add("1.流产假，开始日：" + leaveStartDate +  "结束日：" + leaveDetail.getLeaveEndDate());
            } else {
                throw new RuntimeException("未找到适用于本次请求的流产假规则");
            }
        }
    }

    /**
     * 计算奖励假
     * TODO 添加最大天数校验
     */
    public void calculateOtherExtendedLeave(DateCalculateRequest request, LeaveDetail leaveDetail, CalculateComments calculateComments, OtherExtendedLeavePolicy otherExtendedPolicy) {
        LocalDate leaveStartDate = leaveDetail.getLeaveEndDate();
        if(CollectionUtils.isEmpty(request.getExtendedCodeList())) {
            leaveDetail.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, otherExtendedPolicy.getStandardLeaveDays(), otherExtendedPolicy.isCalendarDay()
                    , otherExtendedPolicy.isDelayForPublicHoliday(), request.getCalendarCode()));
            updateCurrentLeaveDays(leaveDetail, leaveStartDate);
            calculateComments.getDescriptionList().add("4.奖励假，开始日：" + leaveStartDate +  "结束日：" + leaveDetail.getLeaveEndDate());
        } else {
            /*
             * 奖励假根据规则来计算
             */
            var sortedRules = otherExtendedPolicy.getOtherExtendedRules().stream().sorted(Comparator.comparingInt(OtherExtendedRule::getMinDeliverySequence)).toList();
            for (OtherExtendedRule rule : sortedRules) {
                if (request.getDeliverySequence() >= rule.getMinDeliverySequence()) {
                    leaveDetail.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, rule.getLeaveDays(), otherExtendedPolicy.isCalendarDay()
                            , otherExtendedPolicy.isDelayForPublicHoliday(), request.getCalendarCode()));
                    updateCurrentLeaveDays(leaveDetail, leaveStartDate);
                    if (StringUtils.isEmpty(rule.getDescription())) {
                        calculateComments.getDescriptionList().add("4.奖励假，开始日：" + leaveStartDate +  "结束日：" + leaveDetail.getLeaveEndDate());
                    } else {
                        calculateComments.getDescriptionList().add("4.奖励假 ("+ rule.getDescription() + ") ，开始日：" + leaveStartDate +  "结束日：" + leaveDetail.getLeaveEndDate());
                    }
                    break;
                }
            }
        }
    }


}
