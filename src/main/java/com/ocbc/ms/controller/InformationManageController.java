package com.ocbc.ms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CRUD for city/company/policy
 */
@RestController
@RequestMapping("/api/v1/info-manage")
@RequiredArgsConstructor
public class InformationManageController {

    @PostMapping("/import-salary")
    public ResponseEntity<String> importSalary() {
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }



    @PostMapping("/policy/inquiry")
    public ResponseEntity<String> policyInquiry() {
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }





    @PostMapping("/getComment")
    public ResponseEntity<String> getComment() {
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


}
