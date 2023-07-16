package com.expatrio.usermanagement.service.impl;

import com.expatrio.usermanagement.exception.UserNotFoundException;
import com.expatrio.usermanagement.mapper.UserMapper;
import com.expatrio.usermanagement.model.dao.UserDAO;
import com.expatrio.usermanagement.model.payload.request.CreateUserRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateUserRequest;
import com.expatrio.usermanagement.repository.UserRepository;
import com.expatrio.usermanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository the user repository
     * @param userMapper     the user mapper
     */
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDAO createUser(CreateUserRequest request) {
        UserDAO userDAO = userMapper.toEntity(request);
        return userRepository.save(userDAO);
    }

    @Override
    public UserDAO updateUser(UpdateUserRequest request) {
        UserDAO userDAO = userRepository.update(userMapper.toEntity(request));
        if (userDAO == null) {
            throw new UserNotFoundException(request.getId());
        }
        return userDAO;
    }

    @Override
    public boolean deleteUserById(Long id) {
        if (!userRepository.deleteById(id)) {
            throw new UserNotFoundException(id);
        }
        return true;
    }

    @Override
    public UserDAO getUserById(Long id) {
        Optional<UserDAO> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return user.get();
    }

    @Override
    public List<UserDAO> getAllUsers(Integer page, Integer size) {
        return userRepository.findAll(page, size);
    }

}
