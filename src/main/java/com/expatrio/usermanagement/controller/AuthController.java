package com.expatrio.usermanagement.controller;

import com.expatrio.usermanagement.model.payload.request.CreateUserRequest;
import com.expatrio.usermanagement.model.payload.request.LoginRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateUserAuthenticationInfoRequest;
import com.expatrio.usermanagement.model.payload.response.LoginResponse;
import com.expatrio.usermanagement.model.payload.response.UserResponse;
import com.expatrio.usermanagement.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.authenticateUser(loginRequest);
        response.setStatus(200);
        response.setTimestamp(Instant.now());
        response.setMessage("Login Successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        UserResponse response = UserResponse.builder()
                .user(authService.createUser(request))
                .status(201)
                .timestamp(Instant.now())
                .message("User created successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/authInfo")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserAuthenticationInfoRequest request) {
        UserResponse response = UserResponse.builder()
                .user(authService.updateUserAuthenticationInformation(request))
                .status(200)
                .timestamp(Instant.now())
                .message("User authentication information updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

}
