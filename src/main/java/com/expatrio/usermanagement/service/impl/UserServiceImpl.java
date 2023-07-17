package com.expatrio.usermanagement.service.impl;

import com.expatrio.usermanagement.exception.UserNotFoundException;
import com.expatrio.usermanagement.mapper.UserMapper;
import com.expatrio.usermanagement.model.dao.UserDAO;
import com.expatrio.usermanagement.model.payload.request.UpdateUserRequest;
import com.expatrio.usermanagement.repository.UserRepository;
import com.expatrio.usermanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of the {@link UserService} interface.
 * This class provides methods for creating, updating, deleting and retrieving users.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Constructs a new UserServiceImpl with the given user repository and user mapper.
     *
     * @param userRepository the user repository
     * @param userMapper     the user mapper
     */
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Updates an existing user with the given user details and returns the updated user.
     *
     * @param request the request containing the user details
     * @return the updated user
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    @Override
    public UserDAO updateUser(UpdateUserRequest request) {
        if (!userRepository.existsById(request.getId())) {
            throw new UserNotFoundException(request.getId());
        }
        UserDAO userDAO = userRepository.update(userMapper.toEntity(request));
        if (userDAO != null) {
            userDAO.setPassword(null);
            userDAO.setUsername(null);
            userDAO.setRoles(null);
        }
        return userDAO;
    }


    /**
     * Deletes the user with the given ID.
     *
     * @param id the ID of the user to delete
     * @return true if the user was deleted successfully, false otherwise
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    @Override
    public boolean deleteUserById(Long id) {
        if (!userRepository.deleteById(id)) {
            throw new UserNotFoundException(id);
        }
        return true;
    }

    /**
     * Retrieves the user with the given ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the given ID
     * @throws UserNotFoundException if the user with the given ID is not found
     */
    @Override
    public UserDAO getUserById(Long id) {
        Optional<UserDAO> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return user.get();
    }

    /**
     * Retrieves all users with pagination.
     *
     * @param page the page number
     * @param size the page size
     * @return a list of users with the given pagination
     */
    @Override
    public List<UserDAO> getAllUsers(Integer page, Integer size) {
        return userRepository.findAll(page, size);
    }

}
