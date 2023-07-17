package com.expatrio.usermanagement.service.impl;

import com.expatrio.usermanagement.exception.UserNotFoundException;
import com.expatrio.usermanagement.exception.UsernameAlreadyExistException;
import com.expatrio.usermanagement.mapper.UserMapper;
import com.expatrio.usermanagement.model.dao.UserDAO;
import com.expatrio.usermanagement.model.payload.request.CreateUserRequest;
import com.expatrio.usermanagement.model.payload.request.LoginRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateUserAuthenticationInfoRequest;
import com.expatrio.usermanagement.model.payload.response.LoginResponse;
import com.expatrio.usermanagement.repository.UserRepository;
import com.expatrio.usermanagement.security.jwt.JwtUtils;
import com.expatrio.usermanagement.security.service.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    PasswordEncoder encoder;
    @Mock
    JwtUtils jwtUtils;
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticateUser_ValidCredentials_ReturnsLoginResponse() {
        // Arrange
        String jwtToken = "jwtToken";
        List<String> roles = List.of("ROLE_USER");
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        LoginRequest loginRequest = new LoginRequest("testuser", "password");
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "testuser", "password", authorities);

        LoginResponse expectedResponse = LoginResponse.builder()
                .token(jwtToken)
                .roles(roles)
                .username("testuser")
                .build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwtToken);

        // Act
        LoginResponse response = authService.authenticateUser(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getToken(), response.getToken());
        assertEquals(expectedResponse.getRoles(), response.getRoles());
        assertEquals(expectedResponse.getUsername(), response.getUsername());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils, times(1)).generateJwtToken(authentication);
    }

    @Test
    void createUser_NonExistingUsername_ReturnsCreatedUser() {
        // Arrange
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username("testuser")
                .password("password")
                .build();
        UserDAO userDAO = UserDAO.builder()
                .username("testuser")
                .password("encodedPassword")
                .build();
        UserDAO expectedUserDAO = UserDAO.builder()
                .id(1L)
                .username("testuser")
                .password("encodedPassword")
                .build();

        when(userRepository.existsByUsername(createUserRequest.getUsername())).thenReturn(false);
        when(userMapper.toEntity(createUserRequest)).thenReturn(userDAO);
        when(encoder.encode(createUserRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(userDAO)).thenReturn(expectedUserDAO);

        // Act
        UserDAO createdUser = authService.createUser(createUserRequest);

        // Assert
        assertNotNull(createdUser);
        assertEquals(expectedUserDAO.getId(), createdUser.getId());
        assertEquals(expectedUserDAO.getUsername(), createdUser.getUsername());
        assertEquals(expectedUserDAO.getPassword(), createdUser.getPassword());

        verify(userRepository, times(1)).existsByUsername(createUserRequest.getUsername());
        verify(userMapper, times(1)).toEntity(createUserRequest);
        verify(encoder, times(1)).encode(createUserRequest.getPassword());
        verify(userRepository, times(1)).save(userDAO);
    }

    @Test
    void createUser_ExistingUsername_ThrowsUsernameAlreadyExistException() {
        // Arrange
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .username("testuser")
                .password("password")
                .build();

        when(userRepository.existsByUsername(createUserRequest.getUsername())).thenReturn(true);

        // Act & Assert
        assertThrows(UsernameAlreadyExistException.class, () -> authService.createUser(createUserRequest));

        verify(userRepository, times(1)).existsByUsername(createUserRequest.getUsername());
        verify(userRepository, never()).save(any(UserDAO.class));
    }

    @Test
    void updateUserAuthenticationInformation_ExistingUser_ReturnsUpdatedUser() {
        // Arrange
        UpdateUserAuthenticationInfoRequest updateRequest = new UpdateUserAuthenticationInfoRequest(1L, "newUsername", "newPassword", null);
        UserDAO existingUserDAO = UserDAO.builder()
                .id(1L)
                .username("testuser")
                .password("encodedPassword")
                .build();
        UserDAO updatedUserDAO = UserDAO.builder()
                .id(1L)
                .username("newUsername")
                .password("encodedPassword")
                .build();

        when(userRepository.existsById(updateRequest.getId())).thenReturn(true);
        when(userRepository.existsByUsername(updateRequest.getUsername())).thenReturn(false);
        when(userMapper.toEntity(updateRequest)).thenReturn(existingUserDAO);
        when(encoder.encode(updateRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.update(any(UserDAO.class))).thenReturn(updatedUserDAO);

        // Act
        UserDAO updatedUser = authService.updateUserAuthenticationInformation(updateRequest);

        // Assert
        assertNotNull(updatedUser);
        assertEquals(updatedUserDAO.getId(), updatedUser.getId());
        assertEquals(updatedUserDAO.getUsername(), updatedUser.getUsername());
        assertEquals(updatedUserDAO.getPassword(), updatedUser.getPassword());

        verify(userRepository, times(1)).existsById(updateRequest.getId());
        verify(userRepository, times(1)).existsByUsername(updateRequest.getUsername());
        verify(userRepository, times(1)).update(any(UserDAO.class));
    }

    @Test
    void updateUserAuthenticationInformation_NonExistingUser_ThrowsUserNotFoundException() {
        // Arrange
        UpdateUserAuthenticationInfoRequest updateRequest = new UpdateUserAuthenticationInfoRequest(1L, "newUsername", "newPassword", null);

        when(userRepository.existsById(updateRequest.getId())).thenReturn(false);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> authService.updateUserAuthenticationInformation(updateRequest));

        verify(userRepository, times(1)).existsById(updateRequest.getId());
        verify(userRepository, never()).update(any(UserDAO.class));
    }
}