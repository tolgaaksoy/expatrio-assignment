package com.expatrio.usermanagement.controller;

import com.expatrio.usermanagement.model.payload.request.CreateUserRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateUserRequest;
import com.expatrio.usermanagement.model.payload.response.UserResponse;
import com.expatrio.usermanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        UserResponse response = UserResponse.builder()
                .user(userService.createUser(request))
                .status(201)
                .timestamp(Instant.now())
                .message("User created successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest request) {
        UserResponse response = UserResponse.builder()
                .user(userService.updateUser(request))
                .status(200)
                .timestamp(Instant.now())
                .message("User updated successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        UserResponse response = UserResponse.builder()
                .status(200)
                .timestamp(Instant.now())
                .message("User deleted successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = UserResponse.builder()
                .user(userService.getUserById(id))
                .status(200)
                .timestamp(Instant.now())
                .message("User retrieved successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserResponse> getAllUsers(@RequestParam(required = false) Integer page,
                                                    @RequestParam(required = false) Integer size) {
        UserResponse response = UserResponse.builder()
                .userList(userService.getAllUsers(page, size))
                .status(200)
                .timestamp(Instant.now())
                .message("Users retrieved successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
