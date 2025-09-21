package com.ocbc.ms.service.impl;

import com.ocbc.ms.dto.LeaveCalculateDetail;
import com.ocbc.ms.dto.MaternityLeaveCalculateRequest;
import com.ocbc.ms.dto.MaternityLeaveCalculateResponse;
import com.ocbc.ms.model.MaternityLeaveDatePolicy;
import com.ocbc.ms.repository.PolicyRepository;
import com.ocbc.ms.service.MaternityLeaveService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

@Service
public class MaternityLeaveServiceImpl implements MaternityLeaveService {

    @Resource
    PolicyRepository policyRepository;



    @Override
    public MaternityLeaveCalculateResponse calculateDate(MaternityLeaveCalculateRequest request) {
        MaternityLeaveCalculateResponse response = new MaternityLeaveCalculateResponse();
        response.setLeaveCalculateDetail(new LeaveCalculateDetail());
        response.getLeaveCalculateDetail().setDescriptionList(new ArrayList<>());
        var policyOpt = policyRepository.findByCityNameAndCompanyName(request.getCityName(), request.getCompanyName());

        if (policyOpt.isEmpty()) {
            throw new RuntimeException("Policy not found");
        }

        var policy = policyOpt.get();

        var validDatePolicy = policy.getMaternityLeavePolicy().stream().filter(p ->
                p.getLeaveType().equalsIgnoreCase(request.getLeaveType()))
                .sorted(Comparator.comparing(MaternityLeaveDatePolicy::getPriority));


        LocalDate tempStartDate = request.getLeaveStartDate();

        validDatePolicy.forEach(datePolicy -> {




        });


        return null;
    }



}
