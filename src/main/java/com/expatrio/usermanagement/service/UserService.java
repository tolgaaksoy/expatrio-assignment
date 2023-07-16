package com.expatrio.usermanagement.service;

import com.expatrio.usermanagement.model.payload.request.CreateUserRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateUserRequest;
import com.expatrio.usermanagement.model.dao.UserDAO;

import java.util.List;

public interface UserService {
    UserDAO createUser(CreateUserRequest request);

    UserDAO updateUser(UpdateUserRequest request);

    boolean deleteUserById(Long id);

    UserDAO getUserById(Long id);

    List<UserDAO> getAllUsers(Integer page, Integer size);
}
