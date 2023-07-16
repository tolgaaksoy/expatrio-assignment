package com.expatrio.usermanagement.service;

import com.expatrio.usermanagement.model.dao.DepartmentDAO;
import com.expatrio.usermanagement.model.payload.DepartmentAverageSalaryDto;
import com.expatrio.usermanagement.model.payload.request.CreateDepartmentRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateDepartmentRequest;

import java.util.List;

/**
 * The interface Department service.
 */
public interface DepartmentService {

    /**
     * Create department department dao.
     *
     * @param request the request
     * @return the department dao
     */
    DepartmentDAO createDepartment(CreateDepartmentRequest request);

    /**
     * Update department department dao.
     *
     * @param request the request
     * @return the department dao
     */
    DepartmentDAO updateDepartment(UpdateDepartmentRequest request);

    /**
     * Delete department boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteDepartment(Long id);

    /**
     * Gets department by id.
     *
     * @param id the id
     * @return the department by id
     */
    DepartmentDAO getDepartmentById(Long id);

    /**
     * Gets all departments.
     *
     * @param page the page
     * @param size the size
     * @return the all departments
     */
    List<DepartmentDAO> getAllDepartments(Integer page, Integer size);

    List<DepartmentAverageSalaryDto> getAverageSalaryPerDepartment();
}
