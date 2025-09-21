package com.ocbc.ms.service.impl;

import com.ocbc.ms.dto.LeaveCalculateDetail;
import com.ocbc.ms.dto.DateCalculateRequest;
import com.ocbc.ms.dto.MaternityLeaveCalculateResponse;
import com.ocbc.ms.model.AbortionRule;
import com.ocbc.ms.model.MaternityLeaveDatePolicy;
import com.ocbc.ms.repository.PolicyRepository;
import com.ocbc.ms.service.MaternityLeaveService;
import com.ocbc.ms.util.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.time.LocalDate;
import java.util.ArrayList;


@Service
@Slf4j
public class MaternityLeaveServiceImpl implements MaternityLeaveService {

    @Resource
    PolicyRepository policyRepository;
    @Resource
    DateUtil dateUtil;



    @Override
    public MaternityLeaveCalculateResponse calculateDate(DateCalculateRequest request) {
        MaternityLeaveCalculateResponse response = new MaternityLeaveCalculateResponse();
        response.setLeaveCalculateDetail(new LeaveCalculateDetail());
        response.getLeaveCalculateDetail().setDescriptionList(new ArrayList<>());
        response.setLeaveEndDate(request.getLeaveStartDate());
        var policyOpt = policyRepository.findByCityNameAndCompanyName(request.getCityName(), request.getCompanyName());

        if (policyOpt.isEmpty()) {
            throw new RuntimeException("Policy not found");
        }

        var policy = policyOpt.get();

        if (request.isAbortion()) {
            calculateAbortionLeave(request, response, policy.getAbortionPolicy());
        } else {
            calculateStatutoryLeave(request, response, policy.getStatutoryPolicy());
            if (!CollectionUtils.isEmpty(request.getDystociaTypes())) {
                calculateDystociaLeave(request, response, policy.getDystociaPolicy());
            } else {
                response.getLeaveCalculateDetail().getDescriptionList().add("2.难产假: 无");
            }
            if (request.getInfantNumber() >= 1) {
                calculateMoreInfantLeave(request, response, policy.getMoreInfantPolicy());
            } else {
                response.getLeaveCalculateDetail().getDescriptionList().add("3.多胎假: 无");
            }
            calculateOtherExtendedLeave(request, response, policy.getOtherExtendedPolicy());
        }
        return response;
    }


    /**
     * 计算法定产假
     */
    private void calculateStatutoryLeave(DateCalculateRequest request, MaternityLeaveCalculateResponse response,
                                         MaternityLeaveDatePolicy statutoryPolicy) {
        LocalDate leaveStartDate = response.getLeaveEndDate();
        response.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, statutoryPolicy.getLeaveDays(), statutoryPolicy.isCalendarDay()));
        response.getLeaveCalculateDetail().getDescriptionList().add("1.法定产假，开始日：" + leaveStartDate +  "结束日：" + leaveStartDate);
    }


    /**
     * 计算奖励假
     */
    private void calculateOtherExtendedLeave(DateCalculateRequest request, MaternityLeaveCalculateResponse response, MaternityLeaveDatePolicy otherExtendedPolicy) {

    }

    /**
     * 计算多胎假
     */
    private void calculateMoreInfantLeave(DateCalculateRequest request, MaternityLeaveCalculateResponse response, MaternityLeaveDatePolicy moreInfantPolicy) {
        LocalDate leaveStartDate = response.getLeaveEndDate();
        response.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, moreInfantPolicy.getLeaveDays() * request.getInfantNumber(), moreInfantPolicy.isCalendarDay()));
        response.getLeaveCalculateDetail().getDescriptionList().add("3.多胎假，开始日：" + leaveStartDate +  "结束日：" + leaveStartDate);
    }

    /**
     * 计算流产假
     */
    private void calculateAbortionLeave(DateCalculateRequest request, MaternityLeaveCalculateResponse response,
                                        MaternityLeaveDatePolicy abortionPolicy) {
        var abortionRules = abortionPolicy.getAbortionRules();
        LocalDate leaveStartDate = response.getLeaveEndDate();
        if (request.isEctopicPregnancy() && abortionRules.stream().anyMatch(AbortionRule::isEctopicPregnancy)) {
            /*
             * 宫外孕计算优先级最高
             */
            var ectopicPregnancyRule = abortionRules.stream().filter(AbortionRule::isEctopicPregnancy).findFirst().get();
            response.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, ectopicPregnancyRule.getLeaveDays(), abortionPolicy.isCalendarDay()));
            response.getLeaveCalculateDetail().getDescriptionList().add("1.流产假（宫外孕），开始日：" + leaveStartDate +  "结束日：" + leaveStartDate);
        } else {
            /*
             * 按妊娠天数来计算
             */
            var pregnancyRuleOpt = abortionRules.stream().filter(rule -> request.getRegnancyDays() >= rule.getMinRegnancyDays() && request.getRegnancyDays() <= rule.getMaxRegnancyDays())
                    .findFirst();
            if (pregnancyRuleOpt.isPresent()) {
                var pregnancyRule = pregnancyRuleOpt.get();
                response.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, pregnancyRule.getLeaveDays(), abortionPolicy.isCalendarDay()));
                response.getLeaveCalculateDetail().getDescriptionList().add("1.流产假，开始日：" + leaveStartDate +  "结束日：" + leaveStartDate);
            } else {
                throw new RuntimeException("未找到合适的流产假规则");
            }
        }
    }

    /**
     * 计算难产假
     */
    private void calculateDystociaLeave(DateCalculateRequest request, MaternityLeaveCalculateResponse response,
                                        MaternityLeaveDatePolicy dystociaPolicy) {

    }



}
