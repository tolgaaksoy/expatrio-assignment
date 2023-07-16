package com.expatrio.usermanagement.service.impl;

import com.expatrio.usermanagement.exception.DepartmentNotFoundException;
import com.expatrio.usermanagement.mapper.DepartmentMapper;
import com.expatrio.usermanagement.model.dao.DepartmentDAO;
import com.expatrio.usermanagement.model.payload.request.CreateDepartmentRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateDepartmentRequest;
import com.expatrio.usermanagement.repository.DepartmentRepository;
import com.expatrio.usermanagement.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Department service.
 */
@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;


    /**
     * Instantiates a new Department service.
     *
     * @param departmentRepository the department repository
     * @param departmentMapper     the department mapper
     */
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public DepartmentDAO createDepartment(CreateDepartmentRequest request) {
        DepartmentDAO department = departmentMapper.toEntity(request);
        return departmentRepository.save(department);
    }

    @Override
    public DepartmentDAO updateDepartment(UpdateDepartmentRequest request) {
        DepartmentDAO departmentDAO = departmentRepository.update(departmentMapper.toEntity(request));
        if (departmentDAO == null) {
            throw new DepartmentNotFoundException(request.getId());
        }
        return departmentDAO;
    }

    @Override
    public boolean deleteDepartment(Long id) {
        if (!departmentRepository.deleteById(id)) {
            throw new DepartmentNotFoundException(id);
        }
        return true;
    }

    @Override
    public DepartmentDAO getDepartmentById(Long id) {
        Optional<DepartmentDAO> departmentDAO = departmentRepository.findById(id);
        if (departmentDAO.isEmpty()) {
            throw new DepartmentNotFoundException(id);
        }
        return departmentDAO.get();
    }

    @Override
    public List<DepartmentDAO> getAllDepartments(Integer page, Integer size) {
        return departmentRepository.findAll(page, size);
    }

}
