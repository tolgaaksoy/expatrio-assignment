package com.expatrio.usermanagement.controller;

import com.expatrio.usermanagement.model.payload.request.CreateDepartmentRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateDepartmentRequest;
import com.expatrio.usermanagement.model.payload.response.DepartmentResponse;
import com.expatrio.usermanagement.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(@RequestBody CreateDepartmentRequest request) {
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
    @PutMapping
    public ResponseEntity<DepartmentResponse> updateDepartment(@RequestBody UpdateDepartmentRequest request) {
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
