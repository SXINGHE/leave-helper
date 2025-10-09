package com.ocbc.ms.controller;


import com.ocbc.ms.dto.DateCalculateRequest;
import com.ocbc.ms.dto.CalculateResponse;
import com.ocbc.ms.dto.MoneyCalculateRequest;
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



}
