package com.expatrio.usermanagement.service;

import com.expatrio.usermanagement.model.dao.DepartmentDAO;
import com.expatrio.usermanagement.model.payload.request.CreateDepartmentRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateDepartmentRequest;

import java.util.List;

public interface DepartmentService {

    DepartmentDAO createDepartment(CreateDepartmentRequest request);

    DepartmentDAO updateDepartment(UpdateDepartmentRequest request);

    boolean deleteDepartment(Long id);

    DepartmentDAO getDepartmentById(Long id);

    List<DepartmentDAO> getAllDepartments(Integer page, Integer size);
}
