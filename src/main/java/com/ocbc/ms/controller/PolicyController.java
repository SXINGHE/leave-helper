package com.ocbc.ms.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.ocbc.ms.repository.PolicyRepository;
import com.ocbc.ms.entity.Policy;
import com.ocbc.ms.dto.policy.PolicyCreateRequest;
import com.ocbc.ms.dto.policy.PolicyUpdateRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/policy")
@RequiredArgsConstructor
public class PolicyController {
 
    @Autowired
    private PolicyRepository policyRepository;

    @PostMapping("/create")
    public ResponseEntity<Policy> createPolicy(@Valid @RequestBody PolicyCreateRequest request) {
        try {
            // PolicyCreateRequest 继承自 Policy，可直接保存
            Policy saved = policyRepository.save(request);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable Long id, @Valid @RequestBody PolicyUpdateRequest request) {
        try {
            return policyRepository.findById(id)
                    .map(existing -> {
                        // 仅更新可变字段，保持 id 不变
                        existing.setCityName(request.getCityName());
                        existing.setStatutoryPolicy(request.getStatutoryPolicy());
                        existing.setDystociaPolicy(request.getDystociaPolicy());
                        existing.setMoreInfantPolicy(request.getMoreInfantPolicy());
                        existing.setOtherExtendedPolicy(request.getOtherExtendedPolicy());
                        existing.setAbortionPolicy(request.getAbortionPolicy());
                        existing.setAllowancePolicy(request.getAllowancePolicy());
                        Policy saved = policyRepository.save(existing);
                        return ResponseEntity.ok(saved);
                    })
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
