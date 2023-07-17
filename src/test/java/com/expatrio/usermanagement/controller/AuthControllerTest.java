package com.expatrio.usermanagement.controller;

import com.expatrio.usermanagement.model.payload.request.CreateUserRequest;
import com.expatrio.usermanagement.model.payload.request.LoginRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateUserAuthenticationInfoRequest;
import com.expatrio.usermanagement.model.payload.response.LoginResponse;
import com.expatrio.usermanagement.model.payload.response.UserResponse;
import com.expatrio.usermanagement.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Mock
    AuthService authService;
    @InjectMocks
    AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_ValidLoginRequest_ReturnsLoginResponseWith200Status() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("testuser", "password");
        LoginResponse expectedResponse = LoginResponse.builder()
                .token("jwtToken")
                .status(200)
                .timestamp(Instant.now())
                .message("Login Successful")
                .build();

        when(authService.authenticateUser(any(LoginRequest.class))).thenReturn(expectedResponse);

        // Act
        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());

        verify(authService, times(1)).authenticateUser(loginRequest);
    }

    @Test
    void createUser_ValidCreateUserRequest_ReturnsUserResponseWith201Status() {
        // Arrange
        CreateUserRequest createUserRequest = new CreateUserRequest();
        UserResponse expectedResponse = UserResponse.builder()
                .user(null) // Set the expected user object
                .status(201)
                .timestamp(Instant.now())
                .message("User created successfully")
                .build();

        when(authService.createUser(any(CreateUserRequest.class))).thenReturn(null); // Set the expected user object

        // Act
        ResponseEntity<UserResponse> response = authController.createUser(createUserRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse.getStatus(), response.getBody().getStatus());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());

        verify(authService, times(1)).createUser(createUserRequest);
    }

    @Test
    void updateUser_ValidUpdateUserAuthenticationInfoRequest_ReturnsUserResponseWith200Status() {
        // Arrange
        UpdateUserAuthenticationInfoRequest updateUserRequest = new UpdateUserAuthenticationInfoRequest();
        UserResponse expectedResponse = UserResponse.builder()
                .user(null) // Set the expected user object
                .status(200)
                .timestamp(Instant.now())
                .message("User authentication information updated successfully")
                .build();

        when(authService.updateUserAuthenticationInformation(any(UpdateUserAuthenticationInfoRequest.class)))
                .thenReturn(null); // Set the expected user object

        // Act
        ResponseEntity<UserResponse> response = authController.updateUser(updateUserRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getStatus(), response.getBody().getStatus());
        assertEquals(expectedResponse.getMessage(), response.getBody().getMessage());

        verify(authService, times(1)).updateUserAuthenticationInformation(updateUserRequest);
    }

}