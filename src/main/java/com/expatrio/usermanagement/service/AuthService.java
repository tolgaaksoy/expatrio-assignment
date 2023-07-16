package com.expatrio.usermanagement.service;

import com.expatrio.usermanagement.model.dao.UserDAO;
import com.expatrio.usermanagement.model.payload.request.CreateUserRequest;
import com.expatrio.usermanagement.model.payload.request.LoginRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateUserAuthenticationInfoRequest;
import com.expatrio.usermanagement.model.payload.response.LoginResponse;

/**
 * The interface Auth service.
 */
public interface AuthService {

    /**
     * Authenticate user login response.
     *
     * @param loginRequest the login request
     * @return the login response
     */
    LoginResponse authenticateUser(LoginRequest loginRequest);

    /**
     * Create user user dao.
     *
     * @param request the request
     * @return the user dao
     */
    UserDAO createUser(CreateUserRequest request);

    /**
     * Update user authentication information user dao.
     *
     * @param request the request
     * @return the user dao
     */
    UserDAO updateUserAuthenticationInformation(UpdateUserAuthenticationInfoRequest request);
}
