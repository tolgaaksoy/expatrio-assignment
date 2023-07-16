package com.expatrio.usermanagement.service.impl;

import com.expatrio.usermanagement.exception.DepartmentNotFoundException;
import com.expatrio.usermanagement.mapper.DepartmentMapper;
import com.expatrio.usermanagement.model.dao.DepartmentDAO;
import com.expatrio.usermanagement.model.payload.DepartmentAverageSalaryDto;
import com.expatrio.usermanagement.model.payload.request.CreateDepartmentRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateDepartmentRequest;
import com.expatrio.usermanagement.repository.DepartmentRepository;
import com.expatrio.usermanagement.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of the {@link DepartmentService} interface.
 * This class provides methods for creating, updating, deleting and retrieving departments.
 */
@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;


    /**
     * Constructs a new DepartmentServiceImpl with the given department repository and department mapper.
     *
     * @param departmentRepository the department repository
     * @param departmentMapper     the department mapper
     */
    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    /**
     * Creates a new department with the given department details and returns the created department.
     *
     * @param request the request containing the department details
     * @return the created department
     */
    @Override
    public DepartmentDAO createDepartment(CreateDepartmentRequest request) {
        DepartmentDAO department = departmentMapper.toEntity(request);
        return departmentRepository.save(department);
    }

    /**
     * Updates an existing department with the given department details and returns the updated department.
     *
     * @param request the request containing the department details
     * @return the updated department
     * @throws DepartmentNotFoundException if the department with the given ID is not found
     */
    @Override
    public DepartmentDAO updateDepartment(UpdateDepartmentRequest request) {
        DepartmentDAO departmentDAO = departmentRepository.update(departmentMapper.toEntity(request));
        if (departmentDAO == null) {
            throw new DepartmentNotFoundException(request.getId());
        }
        return departmentDAO;
    }

    /**
     * Deletes the department with the given ID.
     *
     * @param id the ID of the department to delete
     * @return true if the department was deleted successfully, false otherwise
     * @throws DepartmentNotFoundException if the department with the given ID is not found
     */
    @Override
    public boolean deleteDepartment(Long id) {
        if (!departmentRepository.deleteById(id)) {
            throw new DepartmentNotFoundException(id);
        }
        return true;
    }

    /**
     * Retrieves the department with the given ID.
     *
     * @param id the ID of the department to retrieve
     * @return the department with the given ID
     * @throws DepartmentNotFoundException if the department with the given ID is not found
     */
    @Override
    public DepartmentDAO getDepartmentById(Long id) {
        Optional<DepartmentDAO> departmentDAO = departmentRepository.findById(id);
        if (departmentDAO.isEmpty()) {
            throw new DepartmentNotFoundException(id);
        }
        return departmentDAO.get();
    }

    /**
     * Retrieves all departments with pagination.
     *
     * @param page the page number
     * @param size the page size
     * @return a list of departments with the given pagination
     */
    @Override
    public List<DepartmentDAO> getAllDepartments(Integer page, Integer size) {
        return departmentRepository.findAll(page, size);
    }

    @Override
    public List<DepartmentAverageSalaryDto> getAverageSalaryPerDepartment() {
        return departmentRepository.getAverageSalaryPerDepartment();
    }

}
