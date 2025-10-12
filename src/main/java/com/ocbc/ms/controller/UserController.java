package com.ocbc.ms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for user related operations
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for user authentication")
public class UserController {

    /**
     * User login endpoint
     * 
     * @return Success message if login is successful
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return success message")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully logged in"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<String> login() {
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
}