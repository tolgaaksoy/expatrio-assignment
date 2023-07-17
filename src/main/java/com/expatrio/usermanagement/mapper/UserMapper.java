package com.expatrio.usermanagement.mapper;

import com.expatrio.usermanagement.model.dao.DepartmentDAO;
import com.expatrio.usermanagement.model.dao.RoleDAO;
import com.expatrio.usermanagement.model.dao.UserDAO;
import com.expatrio.usermanagement.model.payload.request.CreateUserRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateUserAuthenticationInfoRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateUserRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * The interface User mapper.
 */
@Component
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {DepartmentDAO.class, RoleDAO.class, Collectors.class})
public interface UserMapper {

    /**
     * To entity user dao.
     *
     * @param request the request
     * @return the user dao
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", expression = "java(DepartmentDAO.builder().id(request.getDepartmentId()).build())")
    @Mapping(target = "roles", expression = "java(request.getRoles().stream().map(RoleDAO::new).collect(Collectors.toSet()))")
    UserDAO toEntity(CreateUserRequest request);

    /**
     * To entity user dao.
     *
     * @param request the request
     * @return the user dao
     */
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "department", expression = "java(DepartmentDAO.builder().id(request.getDepartmentId()).build())")
    UserDAO toEntity(UpdateUserRequest request);

    /**
     * To entity user dao.
     *
     * @param request the request
     * @return the user dao
     */
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "salary", ignore = true)
    @Mapping(target = "department", ignore = true)
    UserDAO toEntity(UpdateUserAuthenticationInfoRequest request);

}
