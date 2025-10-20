package com.ocbc.ms.controller;


import com.ocbc.ms.dto.policy.PolicyDto;
import com.ocbc.ms.entity.City;
import com.ocbc.ms.repository.CityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.ocbc.ms.repository.PolicyRepository;
import com.ocbc.ms.entity.Policy;
import com.ocbc.ms.dto.policy.PolicyCreateRequest;
import com.ocbc.ms.dto.policy.PolicyUpdateRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Policy", description = "Maternity leave policy management APIs")
@RestController
@RequestMapping("/v1/policy")
@RequiredArgsConstructor
public class PolicyController {
 
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private CityRepository cityRepository;

    @Operation(summary = "Create new policy", description = "Create a new maternity leave policy for a city")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created policy"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/create")
    public ResponseEntity<Policy> createPolicy(@RequestBody PolicyCreateRequest request) {
        try {
            System.out.println("create policy");
            Policy policy = getPolicy(request);
            // PolicyCreateRequest 继承自 Policy，可直接保存
            Policy saved = policyRepository.save(policy);
            System.out.println("create policy successful");
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            System.out.println("create policy failed");
            return ResponseEntity.badRequest().build();
        }
    }

    private static Policy getPolicy(PolicyCreateRequest request) {
        Policy policy = new Policy();
        policy.setCityCode(request.getCityCode());
        policy.setStatutoryPolicy(request.getStatutoryPolicy());
        policy.setDystociaPolicy(request.getDystociaPolicy());
        policy.setMoreInfantPolicy(request.getMoreInfantPolicy());
        policy.setOtherExtendedPolicy(request.getOtherExtendedPolicy());
        policy.setAbortionPolicy(request.getAbortionPolicy());
        policy.setAllowancePolicy(request.getAllowancePolicy());
        return policy;
    }

    @Operation(summary = "Update existing policy", description = "Update maternity leave policy by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated policy"),
        @ApiResponse(responseCode = "404", description = "Policy not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/update")
    public ResponseEntity<Policy> updatePolicy(@RequestBody PolicyUpdateRequest request) {
        try {
            return policyRepository.findById(request.getId())
                    .map(existing -> {
                        // 仅更新可变字段，保持 id 不变
                        existing.setCityCode(request.getCityCode());
                        existing.setStatutoryPolicy(request.getStatutoryPolicy());
                        existing.setDystociaPolicy(request.getDystociaPolicy());
                        existing.setMoreInfantPolicy(request.getMoreInfantPolicy());
                        existing.setOtherExtendedPolicy(request.getOtherExtendedPolicy());
                        existing.setAbortionPolicy(request.getAbortionPolicy());
                        existing.setAllowancePolicy(request.getAllowancePolicy());
                        existing.setMaxLeaveDays(request.getMaxLeaveDays());
                        Policy saved = policyRepository.save(existing);
                        return ResponseEntity.ok(saved);
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Fetch policy by city name", description = "政策配置页面需要，产假计算页面同样需要call此接口获取可能的特殊参数")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get policy"),
            @ApiResponse(responseCode = "404", description = "Policy not found"),
    })
    @GetMapping("/fetch")
    public ResponseEntity<PolicyDto> policyFetch(@RequestParam("cityCode") String cityCode) {
        Policy policy = policyRepository.findByCityCode(cityCode).orElse(null);
        PolicyDto policyDto = new PolicyDto();
        policyDto.setId(policy.getId());
        policyDto.setCityCode(policy.getCityCode());
        policyDto.setStatutoryPolicy(policy.getStatutoryPolicy());
        policyDto.setDystociaPolicy(policy.getDystociaPolicy());
        policyDto.setMoreInfantPolicy(policy.getMoreInfantPolicy());
        policyDto.setOtherExtendedPolicy(policy.getOtherExtendedPolicy());
        policyDto.setAbortionPolicy(policy.getAbortionPolicy());
        policyDto.setAllowancePolicy(policy.getAllowancePolicy());
        return new ResponseEntity<>(policyDto, HttpStatus.OK);
    }

    @Operation(summary = "Delete policy by ID", description = "Soft delete a policy by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted policy"),
            @ApiResponse(responseCode = "404", description = "Policy not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        policyRepository.hardDeleteById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get All Policy", description = "获取所有政策配置")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get policy"),
            @ApiResponse(responseCode = "404", description = "Policy not found"),
    })
    @GetMapping("/getAllPolicy")
    public ResponseEntity<List<PolicyDto>> getAllPolicy() {
        List<PolicyDto> response = new ArrayList<>();

        List<City> cities = cityRepository.findAll();

        var cityMap = cities.stream().collect(Collectors.toMap(City::getCityCode, City::getCityName));


        List<Policy> policies = policyRepository.findAll();
        policies.forEach(policy -> {
            PolicyDto policyDto = new PolicyDto();
            policyDto.setId(policy.getId());
            policyDto.setCityCode(policy.getCityCode());
            policyDto.setCityName(cityMap.get(policy.getCityCode()));
            policyDto.setStatutoryPolicy(policy.getStatutoryPolicy());
            policyDto.setDystociaPolicy(policy.getDystociaPolicy());
            policyDto.setMoreInfantPolicy(policy.getMoreInfantPolicy());
            policyDto.setOtherExtendedPolicy(policy.getOtherExtendedPolicy());
            policyDto.setAbortionPolicy(policy.getAbortionPolicy());
            policyDto.setAllowancePolicy(policy.getAllowancePolicy());
            response.add(policyDto);
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
