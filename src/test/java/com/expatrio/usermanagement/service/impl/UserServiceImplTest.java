package com.expatrio.usermanagement.service.impl;

import com.expatrio.usermanagement.exception.UserNotFoundException;
import com.expatrio.usermanagement.mapper.UserMapper;
import com.expatrio.usermanagement.model.dao.UserDAO;
import com.expatrio.usermanagement.model.payload.request.UpdateUserRequest;
import com.expatrio.usermanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateUser_ExistingUser_ReturnsUpdatedUser() {
        // Arrange
        Long userId = 1L;
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(userId);
        request.setName("updatedUser");
        request.setSalary(BigDecimal.valueOf(2000));

        UserDAO existingUser = new UserDAO();
        existingUser.setId(userId);
        existingUser.setName("existingUser");
        existingUser.setSalary(BigDecimal.valueOf(1000));

        UserDAO updatedUser = new UserDAO();
        updatedUser.setId(userId);
        updatedUser.setName("updatedUser");
        updatedUser.setSalary(BigDecimal.valueOf(2000));

        when(userRepository.existsById(userId)).thenReturn(true);
        when(userMapper.toEntity(request)).thenReturn(updatedUser);
        when(userRepository.update(any(UserDAO.class))).thenReturn(updatedUser);

        // Act
        UserDAO result = userService.updateUser(request);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertNull(result.getUsername());
        assertNull(result.getPassword());
        assertNull(result.getRoles());
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).update(any(UserDAO.class));
    }

    @Test
    void testUpdateUser_NonExistingUser_ThrowsUserNotFoundException() {
        // Arrange
        UpdateUserRequest request = new UpdateUserRequest();
        when(userRepository.existsById(anyLong())).thenReturn(false);
        when(userMapper.toEntity(request)).thenReturn(new UserDAO());
        when(userRepository.update(any(UserDAO.class))).thenReturn(null);

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(request));
        verify(userRepository, times(0)).update(any(UserDAO.class));
    }

    @Test
    void testDeleteUserById_ExistingUser_ReturnsTrue() {
        // Arrange
        Long userId = 1L;
        when(userRepository.deleteById(userId)).thenReturn(true);

        // Act
        boolean result = userService.deleteUserById(userId);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserById_NonExistingUser_ThrowsUserNotFoundException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.deleteById(userId)).thenReturn(false);

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(userId));
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testGetUserById_ExistingUser_ReturnsUser() {
        // Arrange
        Long userId = 1L;
        UserDAO user = new UserDAO();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        UserDAO result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserById_NonExistingUser_ThrowsUserNotFoundException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetAllUsers_ReturnsListOfUsers() {
        // Arrange
        List<UserDAO> users = new ArrayList<>();
        users.add(new UserDAO());
        users.add(new UserDAO());

        when(userRepository.findAll(anyInt(), anyInt())).thenReturn(users);

        // Act
        List<UserDAO> result = userService.getAllUsers(1, 10);

        // Assert
        assertNotNull(result);
        assertEquals(users.size(), result.size());
        verify(userRepository, times(1)).findAll(anyInt(), anyInt());
    }
}