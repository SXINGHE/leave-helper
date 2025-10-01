package com.ocbc.ms.service.impl;

import com.ocbc.ms.dto.*;
import com.ocbc.ms.model.AllowancePolicy;
import com.ocbc.ms.model.rule.AbortionRule;
import com.ocbc.ms.model.MaternityLeaveDatePolicy;
import com.ocbc.ms.model.rule.OtherExtendedRule;
import com.ocbc.ms.repository.PolicyRepository;
import com.ocbc.ms.service.MaternityLeaveService;
import com.ocbc.ms.util.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 
 */
@Service
@Slf4j
public class MaternityLeaveServiceImpl implements MaternityLeaveService {

    @Resource
    PolicyRepository policyRepository;
    @Resource
    DateUtil dateUtil;

    /**
     * 
     */
    @Override
    public MaternityLeaveCalculateResponse calculateDate(DateCalculateRequest request) {
        MaternityLeaveCalculateResponse response = new MaternityLeaveCalculateResponse();
        response.setLeaveEndDate(request.getLeaveStartDate());

        var policyOpt = policyRepository.findByCityName(request.getCityName());

        if (policyOpt.isEmpty()) {
            throw new RuntimeException("Policy not found");
        }

        var policy = policyOpt.get();

        if (request.isAbortion()) {
            /*
                计算流产假
             */
            calculateAbortionLeave(request, response, policy.getAbortionPolicy());
        } else {
            /*
                按顺序计算产假
             */
            calculateStatutoryLeave(request, response, policy.getStatutoryPolicy());
            if (!CollectionUtils.isEmpty(request.getDystociaCodeList())) {
                calculateDystociaLeave(request, response, policy.getDystociaPolicy());
            } else {
                response.getCalculateComments().getDescriptionList().add("2.难产假: 无");
            }
            if (request.getInfantNumber() >= 1) {
                calculateMoreInfantLeave(request, response, policy.getMoreInfantPolicy());
            } else {
                response.getCalculateComments().getDescriptionList().add("3.多胎假: 无");
            }
            calculateOtherExtendedLeave(request, response, policy.getOtherExtendedPolicy());
        }
        return response;
    }

    @Override
    public MaternityLeaveCalculateResponse calculateMoney(MoneyCalculateRequest request) {
        MaternityLeaveCalculateResponse response = new MaternityLeaveCalculateResponse();
        var descList = response.getCalculateComments().getDescriptionList();
        descList.add("计算生育津贴及其补差开始，当前城市为" + request.getCityName());

        var policyOpt = policyRepository.findByCityName(request.getCityName());
        if (policyOpt.isEmpty()) {
            throw new RuntimeException("Policy not found");
        }
        var policy = policyOpt.get();
        /*
            step.1 计算政府发放金额
            step.2 计算生育津贴
            step.3 计算产假期间工资
            step.4 计算需补差
         */
        BigDecimal govAllowance = getGovAllowance(policy.getAllowancePolicy(), descList);
        BigDecimal maternityAllowance = getMaternityAllowance(policy.getAllowancePolicy(), descList, request);
        BigDecimal salary = getSalary(descList, request, response.getAllowanceDetail());

        var compensation = getCompensation(govAllowance, maternityAllowance, salary);
        descList.add("4.需补差：" + compensation);

        return null;
    }

    private BigDecimal getCompensation(BigDecimal govAllowance, BigDecimal maternityAllowance, BigDecimal salary) {
        if (govAllowance.compareTo(maternityAllowance) >= 0) {
            return govAllowance.subtract(salary);
        } else {
            return maternityAllowance.subtract(salary);
        }
    }

    /**
     * 
     */
    private BigDecimal getSalary(List<String> descList, MoneyCalculateRequest request,
                                 AllowanceDetail allowanceDetail) {
        var descPrefix = "3.产假期间工资计算: ";
        descList.add(descPrefix + "开始");
        calculateFirstMonthSalary(descList, request, allowanceDetail);
        calculateLastMonthSalary(descList, request, allowanceDetail);
        calculateOtherMonthSalary(descList, request, allowanceDetail);
        return BigDecimal.ZERO;
    }

    private void calculateFirstMonthSalary(List<String> descList, MoneyCalculateRequest request,
                                           AllowanceDetail allowanceDetail) {
        var descPrefix = "3.1 产假期间第一个月工资计算: ";
        descList.add(descPrefix + "开始");
        int initSalaryDays = dateUtil.getRealSalaryDays(dateUtil.getFirstDayOfMonth(request.getLeaveStartDate()), dateUtil.getLastDayOfMonth(request.getLeaveStartDate()),request.getCalendarCode());
        descList.add(descPrefix + "初始计薪天数为" + initSalaryDays);
        var dailySalary = request.getCurrentSalary().divide(new BigDecimal(initSalaryDays), 2, RoundingMode.HALF_UP);
        descList.add(descPrefix + "每日工资为" + dailySalary.toPlainString());
        int adjustDays = dateUtil.getRealSalaryDays(request.getLeaveStartDate(), dateUtil.getLastDayOfMonth(request.getLeaveStartDate()),request.getCalendarCode());
        descList.add(descPrefix + "调整天数为" + adjustDays);
        var firstMonthSalary = dailySalary.multiply(new BigDecimal(initSalaryDays - adjustDays));
        descList.add(descPrefix + "第一个月工资为" + firstMonthSalary.toPlainString());
        allowanceDetail.setFirstMonthSalary(firstMonthSalary);
    }

    private void calculateLastMonthSalary(List<String> descList, MoneyCalculateRequest request,
                                           AllowanceDetail allowanceDetail) {
        var descPrefix = "3.2 产假期间最后一个月工资计算: ";
        descList.add(descPrefix + "开始");
        int initSalaryDays = dateUtil.getRealSalaryDays(dateUtil.getFirstDayOfMonth(request.getLeaveEndDate()), dateUtil.getLastDayOfMonth(request.getLeaveEndDate()),request.getCalendarCode());
        descList.add(descPrefix + "初始计薪天数为" + initSalaryDays);
        var dailySalary = request.getCurrentSalary().divide(new BigDecimal(initSalaryDays), 2, RoundingMode.HALF_UP);
        descList.add(descPrefix + "每日工资为" + dailySalary.toPlainString());
        int adjustDays = dateUtil.getRealSalaryDays(dateUtil.getFirstDayOfMonth(request.getLeaveEndDate()), request.getLeaveEndDate(), request.getCalendarCode());
        descList.add(descPrefix + "调整天数为" + adjustDays);
        var lastMonthSalary = dailySalary.multiply(new BigDecimal(initSalaryDays - adjustDays));
        descList.add(descPrefix + "最后一个月工资为" + lastMonthSalary.toPlainString());
        allowanceDetail.setLastMonthSalary(lastMonthSalary);
    }

    private void calculateOtherMonthSalary(List<String> descList, MoneyCalculateRequest request,
                                          AllowanceDetail allowanceDetail) {
        var descPrefix = "3.3 产假期间其余月份工资计算: ";
        descList.add(descPrefix + "开始");
        descList.add(descPrefix + "产假开始日期为" + request.getLeaveStartDate() + " 产假结束日期为" + request.getLeaveEndDate());
        int monthCount = dateUtil.getFullMonthCount(request.getLeaveStartDate(), request.getLeaveEndDate());
        descList.add(descPrefix + "其中去除首月及尾月则完整月份数量为" + monthCount + " 员工当前工资为" + request.getCurrentSalary());
        BigDecimal otherMonthSalary = request.getCurrentSalary().multiply(new BigDecimal(monthCount));
        descList.add(descPrefix + "其他月份工资为" + otherMonthSalary.toPlainString());
        allowanceDetail.setOtherMonthSalary(otherMonthSalary);
    }


    /**
     * 
     */
    private BigDecimal getMaternityAllowance(AllowancePolicy allowancePolicy, List<String> descList, MoneyCalculateRequest request) {
        var descPrefix = "2.生育津贴计算: ";
        descList.add(descPrefix + "开始");
        var baseSalaryOpt = allowancePolicy.getCorpSalaryDetailList().stream()
                .filter(detail -> StringUtils.endsWithIgnoreCase(detail.getCompanyName(), request.getCompanyName()))
                .findFirst();
        if (baseSalaryOpt.isEmpty()) {
            log.error("Base salary not found");
            throw new RuntimeException("Base salary not found");
        }
//        var allowanceDays = ChronoUnit.DAYS.between(request.getLeaveStartDate(), request.getLeaveEndDate());
        var allowanceDays = allowancePolicy.getAllowanceDays();
        descList.add(descPrefix + "津贴天数为" + allowanceDays);
        var baseSalary = baseSalaryOpt.get();
        descList.add(descPrefix + "公司月（缴费）平均工资为" + baseSalary.getCorpAverageSalary().toPlainString());
        descList.add(descPrefix + "津贴基数分子为" + allowancePolicy.getNumerator() + " 津贴基数分母为 " + allowancePolicy.getDenominator());
        var maternityAllowance = baseSalary.getCorpAverageSalary().multiply(allowancePolicy.getNumerator())
                .multiply(new BigDecimal(allowanceDays))
                .divide(allowancePolicy.getDenominator(), 2, RoundingMode.HALF_UP);
        descList.add(descPrefix + "计算结果为" + maternityAllowance.toPlainString());
        return maternityAllowance;
    }

    private BigDecimal getGovAllowance(AllowancePolicy allowancePolicy, List<String> descriptionList) {
        BigDecimal result = BigDecimal.ZERO;
        if (Objects.nonNull(allowancePolicy.getGovAllowance())) {
            result = allowancePolicy.getGovAllowance();
        }
        descriptionList.add("1.政府发放金额：" + result);
        return result;
    }

    /**
     * 计算法定产假
     */
    private void calculateStatutoryLeave(DateCalculateRequest request, MaternityLeaveCalculateResponse response,
                                         MaternityLeaveDatePolicy statutoryPolicy) {
        LocalDate leaveStartDate = response.getLeaveEndDate();
        response.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, statutoryPolicy.getLeaveDays(), statutoryPolicy.isCalendarDay()));
        response.setCurrentLeaveDays(response.getCurrentLeaveDays() + ChronoUnit.DAYS.between(leaveStartDate, response.getLeaveEndDate()));
        response.getCalculateComments().getDescriptionList().add("1.法定产假，开始日：" + leaveStartDate +  "结束日：" + response.getLeaveEndDate());
    }


    /**
     * 计算难产假
     */
    private void calculateDystociaLeave(DateCalculateRequest request, MaternityLeaveCalculateResponse response,
                                        MaternityLeaveDatePolicy dystociaPolicy) {
        var dystociaRules = dystociaPolicy.getDystociaRules().stream().filter(rule -> request.getDystociaCodeList().contains(rule.getDystociaCode())).toList();
        dystociaRules.forEach(rule -> {
            LocalDate leaveStartDate = response.getLeaveEndDate();
            response.setLeaveEndDate(dateUtil.getEndDate(response.getLeaveEndDate(), rule.getLeaveDays(), dystociaPolicy.isCalendarDay()));
            response.setCurrentLeaveDays(response.getCurrentLeaveDays() + ChronoUnit.DAYS.between(leaveStartDate, response.getLeaveEndDate()));
            response.getCalculateComments().getDescriptionList().add("2.难产假 ("+ rule.getDescription() + ") 开始日：" + leaveStartDate +  "结束日：" + response.getLeaveEndDate());
        });
    }

    /**
     * 计算多胎假
     */
    private void calculateMoreInfantLeave(DateCalculateRequest request, MaternityLeaveCalculateResponse response, MaternityLeaveDatePolicy moreInfantPolicy) {
        LocalDate leaveStartDate = response.getLeaveEndDate();
        response.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, moreInfantPolicy.getLeaveDays() * request.getInfantNumber(), moreInfantPolicy.isCalendarDay()));
        response.setCurrentLeaveDays(response.getCurrentLeaveDays() + ChronoUnit.DAYS.between(leaveStartDate, response.getLeaveEndDate()));
        response.getCalculateComments().getDescriptionList().add("3.多胎假，开始日：" + leaveStartDate +  "结束日：" + response.getLeaveEndDate());
    }

    /**
     * 计算奖励假
     * TODO 添加最大天数校验
     */
    private void calculateOtherExtendedLeave(DateCalculateRequest request, MaternityLeaveCalculateResponse response, MaternityLeaveDatePolicy otherExtendedPolicy) {
        LocalDate leaveStartDate = response.getLeaveEndDate();
        if(CollectionUtils.isEmpty(otherExtendedPolicy.getOtherExtendedRules())) {
            response.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, otherExtendedPolicy.getLeaveDays(), otherExtendedPolicy.isCalendarDay()
                    , otherExtendedPolicy.isDelayForPublicHoliday(), request.getCalendarCode()));
            response.setCurrentLeaveDays(response.getCurrentLeaveDays() + ChronoUnit.DAYS.between(leaveStartDate, response.getLeaveEndDate()));
            response.getCalculateComments().getDescriptionList().add("4.奖励假，开始日：" + leaveStartDate +  "结束日：" + response.getLeaveEndDate());
        } else {
            /*
             * 奖励假根据规则来计算
             */
            var sortedRules = otherExtendedPolicy.getOtherExtendedRules().stream().sorted(Comparator.comparingInt(OtherExtendedRule::getMinDeliverySequence)).toList();
            for (OtherExtendedRule rule : sortedRules) {
                if (request.getDeliverySequence() >= rule.getMinDeliverySequence()) {
                    response.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, rule.getLeaveDays(), otherExtendedPolicy.isCalendarDay()
                            , otherExtendedPolicy.isDelayForPublicHoliday(), request.getCalendarCode()));
                    response.setCurrentLeaveDays(response.getCurrentLeaveDays() + ChronoUnit.DAYS.between(leaveStartDate, response.getLeaveEndDate()));
                    if (StringUtils.isEmpty(rule.getDescription())) {
                        response.getCalculateComments().getDescriptionList().add("4.奖励假，开始日：" + leaveStartDate +  "结束日：" + response.getLeaveEndDate());
                    } else {
                        response.getCalculateComments().getDescriptionList().add("4.奖励假 ("+ rule.getDescription() + ") ，开始日：" + leaveStartDate +  "结束日：" + response.getLeaveEndDate());
                    }
                    break;
                }
            }
        }
    }

    /**
     * 计算流产假
     * TODO recommendAbortionLeaveDays
     * TODO 添加最大天数校验
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
            response.setCurrentLeaveDays(response.getCurrentLeaveDays() + ChronoUnit.DAYS.between(leaveStartDate, response.getLeaveEndDate()));
            response.getCalculateComments().getDescriptionList().add("1.流产假（宫外孕），开始日：" + leaveStartDate +  "结束日：" + response.getLeaveEndDate());
        } else {
            /*
             * 按妊娠天数来计算
             */
            var pregnancyRuleOpt = abortionRules.stream().filter(rule -> request.getRegnancyDays() >= rule.getMinRegnancyDays() && request.getRegnancyDays() <= rule.getMaxRegnancyDays())
                    .findFirst();
            if (pregnancyRuleOpt.isPresent()) {
                var pregnancyRule = pregnancyRuleOpt.get();
                response.setLeaveEndDate(dateUtil.getEndDate(leaveStartDate, pregnancyRule.getLeaveDays(), abortionPolicy.isCalendarDay()));
                response.setCurrentLeaveDays(response.getCurrentLeaveDays() + ChronoUnit.DAYS.between(leaveStartDate, response.getLeaveEndDate()));
                response.getCalculateComments().getDescriptionList().add("1.流产假，开始日：" + leaveStartDate +  "结束日：" + response.getLeaveEndDate());
            } else {
                throw new RuntimeException("未找到合适的流产假规则");
            }
        }
    }

}
