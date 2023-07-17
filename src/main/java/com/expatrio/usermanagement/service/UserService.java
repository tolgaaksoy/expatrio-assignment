package com.expatrio.usermanagement.service;

import com.expatrio.usermanagement.model.dao.UserDAO;
import com.expatrio.usermanagement.model.payload.request.UpdateUserRequest;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Update user user dao.
     *
     * @param request the request
     * @return the user dao
     */
    UserDAO updateUser(UpdateUserRequest request);

    /**
     * Delete user by id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteUserById(Long id);

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    UserDAO getUserById(Long id);

    /**
     * Gets all users.
     *
     * @param page the page
     * @param size the size
     * @return the all users
     */
    List<UserDAO> getAllUsers(Integer page, Integer size);
}
