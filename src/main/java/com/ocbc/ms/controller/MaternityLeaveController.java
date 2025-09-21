package com.ocbc.ms.controller;


import com.ocbc.ms.dto.MaternityLeaveCalculateRequest;
import com.ocbc.ms.dto.MaternityLeaveCalculateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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


    @PostMapping("/calculateDate")
    public ResponseEntity<MaternityLeaveCalculateResponse> calculateDate(MaternityLeaveCalculateRequest request) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @PostMapping("/calculateMoney")
    public ResponseEntity<MaternityLeaveCalculateResponse> calculateMoney(MaternityLeaveCalculateRequest request) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
