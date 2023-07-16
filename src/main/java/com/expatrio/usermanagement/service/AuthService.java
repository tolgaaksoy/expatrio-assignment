package com.expatrio.usermanagement.service;

import com.expatrio.usermanagement.model.dao.UserDAO;
import com.expatrio.usermanagement.model.payload.request.CreateUserRequest;
import com.expatrio.usermanagement.model.payload.request.LoginRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateUserAuthenticationInfoRequest;
import com.expatrio.usermanagement.model.payload.response.LoginResponse;

public interface AuthService {

    LoginResponse authenticateUser(LoginRequest loginRequest);

    UserDAO createUser(CreateUserRequest request);

    UserDAO updateUserAuthenticationInformation(UpdateUserAuthenticationInfoRequest request);
}
