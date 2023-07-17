package com.expatrio.usermanagement.controller;

import com.expatrio.usermanagement.model.payload.request.CreateDepartmentRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateDepartmentRequest;
import com.expatrio.usermanagement.model.payload.response.DepartmentResponse;
import com.expatrio.usermanagement.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

/**
 * The type Department controller.
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    /**
     * Instantiates a new Department controller.
     *
     * @param departmentService the department service
     */
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Create department response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody CreateDepartmentRequest request) {
        DepartmentResponse response = DepartmentResponse.builder()
                .department(departmentService.createDepartment(request))
                .status(HttpStatus.CREATED.value())
                .timestamp(Instant.now())
                .message("Department created successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update department response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<DepartmentResponse> updateDepartment(@Valid @RequestBody UpdateDepartmentRequest request) {
        DepartmentResponse response = DepartmentResponse.builder()
                .department(departmentService.updateDepartment(request))
                .status(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Department updated successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Delete department response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentResponse> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        DepartmentResponse response = DepartmentResponse.builder()
                .status(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Department deleted successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Gets department by id.
     *
     * @param id the id
     * @return the department by id
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id) {
        DepartmentResponse response = DepartmentResponse.builder()
                .department(departmentService.getDepartmentById(id))
                .status(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Department retrieved successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Gets all departments.
     *
     * @param page the page
     * @param size the size
     * @return the all departments
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<DepartmentResponse> getAllDepartments(@RequestParam(defaultValue = "0") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer size) {
        DepartmentResponse response = DepartmentResponse.builder()
                .departmentList(departmentService.getAllDepartments(page, size))
                .status(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Departments retrieved successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/averageSalary")
    public ResponseEntity<DepartmentResponse> getAverageSalaryByDepartment() {
        DepartmentResponse response = DepartmentResponse.builder()
                .departmentAverageSalaryList(departmentService.getAverageSalaryPerDepartment())
                .status(HttpStatus.OK.value())
                .timestamp(Instant.now())
                .message("Average salary by department retrieved successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
