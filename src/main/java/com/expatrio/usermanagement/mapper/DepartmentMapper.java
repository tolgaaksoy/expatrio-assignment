package com.expatrio.usermanagement.mapper;

import com.expatrio.usermanagement.model.dao.DepartmentDAO;
import com.expatrio.usermanagement.model.payload.request.CreateDepartmentRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateDepartmentRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

/**
 * The interface Department mapper.
 */
@Component
@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepartmentMapper {

    /**
     * To entity department dao.
     *
     * @param request the request
     * @return the department dao
     */
    @Mapping(target = "id", ignore = true)
    DepartmentDAO toEntity(CreateDepartmentRequest request);

    /**
     * To entity department dao.
     *
     * @param request the request
     * @return the department dao
     */
    DepartmentDAO toEntity(UpdateDepartmentRequest request);

}
