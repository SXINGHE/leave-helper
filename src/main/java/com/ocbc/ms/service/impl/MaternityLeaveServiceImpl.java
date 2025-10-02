package com.ocbc.ms.service.impl;

import com.ocbc.ms.dto.*;
import com.ocbc.ms.dto.allowance.AllowancePolicy;
import com.ocbc.ms.dto.leave.*;
import com.ocbc.ms.repository.PolicyRepository;
import com.ocbc.ms.service.MaternityLeaveService;
import com.ocbc.ms.util.CalculateDateUtil;
import com.ocbc.ms.util.CalculateMoneyUtil;
import com.ocbc.ms.util.DateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    @Resource
    CalculateDateUtil calculateDateUtil;
    @Resource
    CalculateMoneyUtil calculateMoneyUtil;

    /**
     * 
     */
    @Override
    public CalculateResponse calculateDate(DateCalculateRequest request) {
        CalculateResponse response = new CalculateResponse();

        try {
            var leaveDetail = response.getLeaveDetail();
            leaveDetail.setLeaveStartDate(request.getLeaveStartDate());
            leaveDetail.setLeaveEndDate(request.getLeaveStartDate());
            var calculateComments = response.getCalculateComments();

            var policyOpt = policyRepository.findByCityName(request.getCityName());

            if (policyOpt.isEmpty()) {
                throw new RuntimeException("Policy not found");
            }

            calculateComments.getDescriptionList().add("假期计算开始");

            var policy = policyOpt.get();

            if (request.isAbortion()) {
                calculateComments.getDescriptionList().add("进入计算流产假流程");
                /*
                    计算流产假
                 */
                calculateDateUtil.calculateAbortionLeave(request, leaveDetail, calculateComments,policy.getAbortionPolicy());
            } else {
                calculateComments.getDescriptionList().add("进入计算产假流程");
                /*
                    按顺序计算产假
                 */
                calculateDateUtil.calculateStatutoryLeave(leaveDetail, calculateComments, policy.getStatutoryPolicy());
                if (request.isDystocia()) {
                    calculateDateUtil.calculateDystociaLeave(request, leaveDetail, calculateComments, policy.getDystociaPolicy());
                } else {
                    calculateComments.getDescriptionList().add("2.难产假: 无");
                }
                if (request.getInfantNumber() > 1) {
                    calculateDateUtil.calculateMoreInfantLeave(request, leaveDetail, calculateComments, policy.getMoreInfantPolicy());
                } else {
                    calculateComments.getDescriptionList().add("3.多胎假: 无");
                }
                calculateDateUtil.calculateOtherExtendedLeave(request, leaveDetail, calculateComments, policy.getOtherExtendedPolicy());
            }
        }catch (Exception e){
            log.error("MaternityLeaveServiceImpl calculateDate error", e);
            throw new RuntimeException("MaternityLeaveServiceImpl calculateDate error", e);
        }

        return response;
    }

    @Override
    public CalculateResponse calculateMoney(MoneyCalculateRequest request) {
        CalculateResponse response = new CalculateResponse();
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
        if ("Yes".equals(policy.getAllowancePolicy().getDifferenceCompensationRule().getForceCompensation())
            || ("Other".equalsIgnoreCase(policy.getAllowancePolicy().getDifferenceCompensationRule().getForceCompensation())
                    && request.isHitForceCompensationRule())) {
            var compensation = getCompensation(govAllowance, maternityAllowance, salary);
            descList.add("4.需补差：" + compensation);
        } else {
            descList.add("4.根据政策要求，此次产假津贴无需补差");
        }
        return response;
    }

    private BigDecimal getCompensation(BigDecimal govAllowance, BigDecimal maternityAllowance, BigDecimal salary) {
        if (salary.compareTo(govAllowance) >= 0 && salary.compareTo(maternityAllowance) >= 0) {
            return BigDecimal.ZERO;
        }
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
        var totalSalary = allowanceDetail.getFirstMonthSalary()
                .add(allowanceDetail.getLastMonthSalary())
                .add(allowanceDetail.getOtherMonthSalary());
        descList.add(descPrefix + "总工资为" + totalSalary.toPlainString());
        return totalSalary;
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

}
