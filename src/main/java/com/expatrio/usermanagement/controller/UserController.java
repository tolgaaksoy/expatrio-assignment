package com.expatrio.usermanagement.controller;

import com.expatrio.usermanagement.model.payload.request.CreateUserRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateUserRequest;
import com.expatrio.usermanagement.model.payload.response.UserResponse;
import com.expatrio.usermanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create user response entity.
     *
     * @param request the request
     * @return the response entity
     */
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

    /**
     * Update user response entity.
     *
     * @param request the request
     * @return the response entity
     */
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

    /**
     * Delete user response entity.
     *
     * @param id the id
     * @return the response entity
     */
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

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
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

    /**
     * Gets all users.
     *
     * @param page the page
     * @param size the size
     * @return the all users
     */
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
