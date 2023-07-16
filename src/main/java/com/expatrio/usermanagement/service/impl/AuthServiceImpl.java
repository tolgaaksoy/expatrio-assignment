package com.expatrio.usermanagement.service.impl;

import com.expatrio.usermanagement.exception.UserNotFoundException;
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
 * The type Auth service.
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

    @Override
    public UserDAO createUser(CreateUserRequest request) {
        UserDAO userDAO = userMapper.toEntity(request);
        userDAO.setPassword(encoder.encode(request.getPassword()));
        return userRepository.save(userDAO);
    }

    @Override
    public UserDAO updateUserAuthenticationInformation(UpdateUserAuthenticationInfoRequest request) {
        UserDAO userDAO = userRepository.update(userMapper.toEntity(request));
        if (userDAO == null) {
            throw new UserNotFoundException(request.getId());
        }
        return userDAO;
    }

}


