package com.expatrio.usermanagement.controller;

import com.expatrio.usermanagement.model.dao.UserDAO;
import com.expatrio.usermanagement.model.payload.request.UpdateUserRequest;
import com.expatrio.usermanagement.model.payload.response.UserResponse;
import com.expatrio.usermanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateUser_ValidRequest_ReturnsUpdatedUserResponse() {
        // Arrange
        UpdateUserRequest request = new UpdateUserRequest();
        when(userService.updateUser(request)).thenReturn(new UserDAO());

        // Act
        ResponseEntity<UserResponse> responseEntity = userController.updateUser(request);
        UserResponse response = responseEntity.getBody();

        // Assert
        assertNotNull(responseEntity);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(response.getUser());
        assertNull(response.getUserList());
        assertEquals(200, response.getStatus());
        assertNotNull(response.getTimestamp());
        assertEquals("User updated successfully", response.getMessage());
        verify(userService, times(1)).updateUser(request);
    }

    @Test
    void deleteUser_ValidId_ReturnsDeletedUserResponse() {
        // Arrange
        Long userId = 1L;

        // Act
        ResponseEntity<UserResponse> responseEntity = userController.deleteUser(userId);
        UserResponse response = responseEntity.getBody();

        // Assert
        assertNotNull(responseEntity);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(response.getUser());
        assertNull(response.getUserList());
        assertEquals(200, response.getStatus());
        assertNotNull(response.getTimestamp());
        assertEquals("User deleted successfully", response.getMessage());
        verify(userService, times(1)).deleteUserById(userId);
    }

    @Test
    void getUserById_ValidId_ReturnsUserResponse() {
        // Arrange
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(new UserDAO());

        // Act
        ResponseEntity<UserResponse> responseEntity = userController.getUserById(userId);
        UserResponse response = responseEntity.getBody();

        // Assert
        assertNotNull(responseEntity);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(response.getUser());
        assertNull(response.getUserList());
        assertEquals(200, response.getStatus());
        assertNotNull(response.getTimestamp());
        assertEquals("User retrieved successfully", response.getMessage());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getAllUsers_NoPagination_ReturnsAllUsersResponse() {
        // Arrange
        when(userService.getAllUsers(null, null)).thenReturn(Collections.singletonList(new UserDAO()));

        // Act
        ResponseEntity<UserResponse> responseEntity = userController.getAllUsers(null, null);
        UserResponse response = responseEntity.getBody();

        // Assert
        assertNotNull(responseEntity);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(response.getUser());
        assertNotNull(response.getUserList());
        assertEquals(200, response.getStatus());
        assertNotNull(response.getTimestamp());
        assertEquals("Users retrieved successfully", response.getMessage());
        verify(userService, times(1)).getAllUsers(null, null);
    }

    @Test
    void getAllUsers_WithPagination_ReturnsPaginatedUsersResponse() {
        // Arrange
        int page = 1;
        int size = 10;
        when(userService.getAllUsers(page, size)).thenReturn(Collections.singletonList(new UserDAO()));

        // Act
        ResponseEntity<UserResponse> responseEntity = userController.getAllUsers(page, size);
        UserResponse response = responseEntity.getBody();

        // Assert
        assertNotNull(responseEntity);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(response.getUser());
        assertNotNull(response.getUserList());
        assertEquals(200, response.getStatus());
        assertNotNull(response.getTimestamp());
        assertEquals("Users retrieved successfully", response.getMessage());
        verify(userService, times(1)).getAllUsers(page, size);
    }
}
