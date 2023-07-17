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
import com.expatrio.usermanagement.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The implementation of the {@link AuthService} interface.
 * This class provides methods for user authentication, creation and updating user authentication information.
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Instantiates a new Auth service.
     *
     * @param authenticationManager the authentication manager
     * @param userRepository        the user repository
     * @param encoder               the encoder
     * @param jwtUtils              the jwt utils
     * @param userMapper            the user mapper
     */
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           PasswordEncoder encoder,
                           JwtUtils jwtUtils,
                           UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.userMapper = userMapper;
    }

    /**
     * Authenticates a user with the given login credentials and returns a JWT token and user roles.
     *
     * @param loginRequest the login request containing the username and password
     * @return a {@link LoginResponse} object containing the JWT token and user roles
     */
    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        return LoginResponse.builder()
                .token(jwt)
                .roles(roles)
                .username(userDetails.getUsername())
                .build();
    }

    /**
     * Creates a new user with the given user details and returns the created user.
     *
     * @param request the request containing the user details
     * @return the created user
     */
    @Override
    public UserDAO createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistException(request.getUsername());
        }
        UserDAO userDAO = userMapper.toEntity(request);
        userDAO.setPassword(encoder.encode(request.getPassword()));
        return userRepository.save(userDAO);
    }

    /**
     * Updates the authentication information of an existing user with the given user details and returns the updated user.
     *
     * @param request the request containing the user details
     * @return the updated user
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    @Override
    public UserDAO updateUserAuthenticationInformation(UpdateUserAuthenticationInfoRequest request) {
        if (!userRepository.existsById(request.getId())) {
            throw new UserNotFoundException(request.getId());
        }
        if (request.getUsername() != null && userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistException(request.getUsername());
        }
        return userRepository.update(userMapper.toEntity(request));
    }

}