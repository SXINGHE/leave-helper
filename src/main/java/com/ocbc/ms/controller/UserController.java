package com.ocbc.ms.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * only for login, no need to implement it for our demo
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    /**
     * login, will do it later
     */
    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
}