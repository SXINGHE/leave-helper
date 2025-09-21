package com.ocbc.ms.service.impl;

import com.ocbc.ms.dto.LeaveCalculateDetail;
import com.ocbc.ms.dto.MaternityLeaveCalculateRequest;
import com.ocbc.ms.dto.MaternityLeaveCalculateResponse;
import com.ocbc.ms.model.MaternityLeaveDatePolicy;
import com.ocbc.ms.repository.PolicyRepository;
import com.ocbc.ms.service.MaternityLeaveService;
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



    @Override
    public MaternityLeaveCalculateResponse calculateDate(MaternityLeaveCalculateRequest request) {
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
            }
            if (request.getInfantNumber() >= 1) {
                calculateMoreInfantLeave(request, response, policy.getMoreInfantPolicy());
            }
            calculateOtherExtendedLeave(request, response, policy.getOtherExtendedPolicy());
        }
        return response;
    }

    /**
     * 计算奖励假
     */
    private void calculateOtherExtendedLeave(MaternityLeaveCalculateRequest request, MaternityLeaveCalculateResponse response, MaternityLeaveDatePolicy otherExtendedPolicy) {

    }

    /**
     * 计算多胎假
     */
    private void calculateMoreInfantLeave(MaternityLeaveCalculateRequest request, MaternityLeaveCalculateResponse response, MaternityLeaveDatePolicy moreInfantPolicy) {

    }

    /**
     * 计算流产假
     */
    private void calculateAbortionLeave(MaternityLeaveCalculateRequest request, MaternityLeaveCalculateResponse response,
                                        MaternityLeaveDatePolicy abortionPolicy) {

    }


    /**
     * 计算法定产假
     */
    private void calculateStatutoryLeave(MaternityLeaveCalculateRequest request, MaternityLeaveCalculateResponse response,
                                        MaternityLeaveDatePolicy statutoryPolicy) {

    }

    /**
     * 计算难产假
     */
    private void calculateDystociaLeave(MaternityLeaveCalculateRequest request, MaternityLeaveCalculateResponse response,
                                         MaternityLeaveDatePolicy dystociaPolicy) {

    }



}
