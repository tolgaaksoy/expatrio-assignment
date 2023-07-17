package com.expatrio.usermanagement.controller;

import com.expatrio.usermanagement.model.dao.DepartmentDAO;
import com.expatrio.usermanagement.model.payload.request.CreateDepartmentRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateDepartmentRequest;
import com.expatrio.usermanagement.model.payload.response.DepartmentResponse;
import com.expatrio.usermanagement.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DepartmentControllerTest {

    @Test
    void createDepartment() {
        // Arrange
        CreateDepartmentRequest request = new CreateDepartmentRequest();
        DepartmentService departmentService = mock(DepartmentService.class);
        DepartmentController departmentController = new DepartmentController(departmentService);
        when(departmentService.createDepartment(request)).thenReturn(mock(DepartmentDAO.class));

        // Act
        ResponseEntity<DepartmentResponse> response = departmentController.createDepartment(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Department created successfully", response.getBody().getMessage());
        verify(departmentService, times(1)).createDepartment(request);
    }

    @Test
    void updateDepartment() {
        // Arrange
        UpdateDepartmentRequest request = new UpdateDepartmentRequest();
        DepartmentService departmentService = mock(DepartmentService.class);
        DepartmentController departmentController = new DepartmentController(departmentService);
        when(departmentService.updateDepartment(request)).thenReturn(mock(DepartmentDAO.class));

        // Act
        ResponseEntity<DepartmentResponse> response = departmentController.updateDepartment(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Department updated successfully", response.getBody().getMessage());
        verify(departmentService, times(1)).updateDepartment(request);
    }

    @Test
    void deleteDepartment() {
        // Arrange
        Long id = 1L;
        DepartmentService departmentService = mock(DepartmentService.class);
        DepartmentController departmentController = new DepartmentController(departmentService);

        // Act
        ResponseEntity<DepartmentResponse> response = departmentController.deleteDepartment(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Department deleted successfully", response.getBody().getMessage());
        verify(departmentService, times(1)).deleteDepartment(id);
    }

    @Test
    void getDepartmentById() {
        // Arrange
        Long id = 1L;
        DepartmentService departmentService = mock(DepartmentService.class);
        DepartmentController departmentController = new DepartmentController(departmentService);
        when(departmentService.getDepartmentById(id)).thenReturn(mock(DepartmentDAO.class));

        // Act
        ResponseEntity<DepartmentResponse> response = departmentController.getDepartmentById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Department retrieved successfully", response.getBody().getMessage());
        verify(departmentService, times(1)).getDepartmentById(id);
    }

    @Test
    void getAllDepartments() {
        // Arrange
        Integer page = 0;
        Integer size = 10;
        DepartmentService departmentService = mock(DepartmentService.class);
        DepartmentController departmentController = new DepartmentController(departmentService);
        when(departmentService.getAllDepartments(page, size)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<DepartmentResponse> response = departmentController.getAllDepartments(page, size);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Departments retrieved successfully", response.getBody().getMessage());
        verify(departmentService, times(1)).getAllDepartments(page, size);
    }
}
