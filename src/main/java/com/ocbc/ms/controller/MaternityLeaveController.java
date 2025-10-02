package com.ocbc.ms.controller;


import com.ocbc.ms.dto.DateCalculateRequest;
import com.ocbc.ms.dto.CalculateResponse;
import com.ocbc.ms.dto.MoneyCalculateRequest;
import com.ocbc.ms.service.MaternityLeaveService;
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
@RestController
@RequestMapping("/api/v1/maternity-leave")
@RequiredArgsConstructor
public class MaternityLeaveController {

    @Resource
    MaternityLeaveService maternityLeaveService;


    @PostMapping("/calculateDate")
    public ResponseEntity<CalculateResponse> calculateDate(@RequestBody DateCalculateRequest request) {
        return new ResponseEntity<>(maternityLeaveService.calculateDate(request), HttpStatus.OK);
    }


    @PostMapping("/calculateMoney")
    public ResponseEntity<CalculateResponse> calculateMoney(@RequestBody MoneyCalculateRequest request) {
        return new ResponseEntity<>(maternityLeaveService.calculateMoney(request), HttpStatus.OK);
    }
}
