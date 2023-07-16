package com.expatrio.usermanagement.mapper;

import com.expatrio.usermanagement.model.dao.DepartmentDAO;
import com.expatrio.usermanagement.model.dao.RoleDAO;
import com.expatrio.usermanagement.model.dao.UserDAO;
import com.expatrio.usermanagement.model.payload.request.CreateUserRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateUserRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {DepartmentDAO.class, RoleDAO.class, Collectors.class})
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", expression = "java(DepartmentDAO.builder().id(request.getDepartmentId()).build())")
    @Mapping(target = "roles", expression = "java(request.getRoles().stream().map(RoleDAO::new).collect(Collectors.toSet()))")
    UserDAO toEntity(CreateUserRequest request);

    @Mapping(target = "department", expression = "java(DepartmentDAO.builder().id(request.getDepartmentId()).build())")
    UserDAO toEntity(UpdateUserRequest request);


}
