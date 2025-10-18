package com.ocbc.ms.controller;


import com.ocbc.ms.dto.DateCalculateRequest;
import com.ocbc.ms.dto.CalculateResponse;
import com.ocbc.ms.dto.MoneyCalculateRequest;
import com.ocbc.ms.dto.history.SaveCalculationRequest;
import com.ocbc.ms.entity.LeaveHistory;
import com.ocbc.ms.repository.LeaveHistoryRepository;
import com.ocbc.ms.service.MaternityLeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


/**
 * calculate maternity
 * and
 * inquiry for calculate history
 */
@Tag(name = "Maternity Leave", description = "Maternity leave calculation APIs")
@RestController
@RequestMapping("/v1/maternity-leave")
@RequiredArgsConstructor
public class MaternityLeaveController {

    @Resource
    MaternityLeaveService maternityLeaveService;

    @Resource
    LeaveHistoryRepository leaveHistoryRepository;


    @Operation(summary = "Calculate maternity leave dates", description = "Calculate leave start date, end date and total days based on birth date and policy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully calculated leave dates"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PostMapping("/calculateDate")
    public ResponseEntity<CalculateResponse> calculateDate(@RequestBody DateCalculateRequest request) {
        return new ResponseEntity<>(maternityLeaveService.calculateDate(request), HttpStatus.OK);
    }


    @Operation(summary = "Calculate maternity leave allowance", description = "Calculate maternity allowance, compensation and salary details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully calculated allowance"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PostMapping("/calculateMoney")
    public ResponseEntity<CalculateResponse> calculateMoney(@RequestBody MoneyCalculateRequest request) {
        return new ResponseEntity<>(maternityLeaveService.calculateMoney(request), HttpStatus.OK);
    }

    @Operation(summary = "save calculate result", description = "Calculate maternity allowance, compensation and salary details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully calculated allowance"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PostMapping("/saveCalculateHistory")
    public ResponseEntity<LeaveHistory> saveCalculateHistory(@RequestBody SaveCalculationRequest request) {
        if (Objects.nonNull(request.getId())) {
            var leaveHistory = leaveHistoryRepository.findById(request.getId()).orElse(null);
            if (Objects.nonNull(leaveHistory)) {
                getLeaveHistory(request, leaveHistory);
                return new ResponseEntity<>(leaveHistoryRepository.save(leaveHistory), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            var leaveHistory = new LeaveHistory();
            getLeaveHistory(request, leaveHistory);
            return new ResponseEntity<>(leaveHistoryRepository.save(leaveHistory), HttpStatus.OK);
        }
    }

    private void getLeaveHistory(SaveCalculationRequest request, LeaveHistory leaveHistory) {
        leaveHistory.setStaffName(request.getStaffName());
        leaveHistory.setCityCode(request.getCityCode());
        leaveHistory.setLeaveStartDate(request.getLeaveStartDate());
        leaveHistory.setLeaveDetail(request.getLeaveDetail());
        leaveHistory.setAllowanceDetail(request.getAllowanceDetail());
        leaveHistory.setCalculateComments(request.getCalculateComments());
        if (Objects.nonNull(request.getLeaveDetail()) && request.getLeaveDetail().getAbortionLeaveDays() > 0) {
            leaveHistory.setAbortion(true);
        }
    }
}
