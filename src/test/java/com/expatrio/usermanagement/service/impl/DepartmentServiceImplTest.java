package com.expatrio.usermanagement.service.impl;

import com.expatrio.usermanagement.exception.DepartmentNotFoundException;
import com.expatrio.usermanagement.mapper.DepartmentMapper;
import com.expatrio.usermanagement.model.dao.DepartmentDAO;
import com.expatrio.usermanagement.model.payload.request.CreateDepartmentRequest;
import com.expatrio.usermanagement.model.payload.request.UpdateDepartmentRequest;
import com.expatrio.usermanagement.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceImplTest {
    @Mock
    DepartmentRepository departmentRepository;
    @Mock
    DepartmentMapper departmentMapper;
    @InjectMocks
    DepartmentServiceImpl departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDepartment_ValidRequest_ReturnsCreatedDepartment() {
        // Arrange
        CreateDepartmentRequest request = new CreateDepartmentRequest();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        when(departmentMapper.toEntity(request)).thenReturn(departmentDAO);
        when(departmentRepository.save(departmentDAO)).thenReturn(departmentDAO);

        // Act
        DepartmentDAO result = departmentService.createDepartment(request);

        // Assert
        assertNotNull(result);
        assertSame(departmentDAO, result);
        verify(departmentMapper, times(1)).toEntity(request);
        verify(departmentRepository, times(1)).save(departmentDAO);
    }

    @Test
    void updateDepartment_ValidRequest_ReturnsUpdatedDepartment() {
        // Arrange
        UpdateDepartmentRequest request = new UpdateDepartmentRequest();
        DepartmentDAO departmentDAO = new DepartmentDAO();

        when(departmentRepository.existsById(departmentDAO.getId())).thenReturn(true);
        when(departmentMapper.toEntity(request)).thenReturn(departmentDAO);
        when(departmentRepository.update(departmentDAO)).thenReturn(departmentDAO);

        // Act
        DepartmentDAO result = departmentService.updateDepartment(request);

        // Assert
        assertNotNull(result);
        assertSame(departmentDAO, result);
        verify(departmentMapper, times(1)).toEntity(request);
        verify(departmentRepository, times(1)).update(departmentDAO);
    }

    @Test
    void updateDepartment_DepartmentNotFound_ThrowsDepartmentNotFoundException() {
        // Arrange
        UpdateDepartmentRequest request = new UpdateDepartmentRequest();
        DepartmentDAO departmentDAO = new DepartmentDAO();

        when(departmentRepository.existsById(departmentDAO.getId())).thenReturn(false);
        when(departmentMapper.toEntity(request)).thenReturn(departmentDAO);
        when(departmentRepository.update(departmentDAO)).thenReturn(null);

        // Act & Assert
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.updateDepartment(request));
        verify(departmentMapper, times(0)).toEntity(request);
        verify(departmentRepository, times(0)).update(departmentDAO);
    }

    @Test
    void deleteDepartment_ValidId_DeletesDepartmentAndReturnsTrue() {
        // Arrange
        Long departmentId = 1L;
        when(departmentRepository.deleteById(departmentId)).thenReturn(true);

        // Act
        boolean result = departmentService.deleteDepartment(departmentId);

        // Assert
        assertTrue(result);
        verify(departmentRepository, times(1)).deleteById(departmentId);
    }

    @Test
    void deleteDepartment_DepartmentNotFound_ThrowsDepartmentNotFoundException() {
        // Arrange
        Long departmentId = 1L;
        when(departmentRepository.deleteById(departmentId)).thenReturn(false);

        // Act & Assert
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.deleteDepartment(departmentId));
        verify(departmentRepository, times(1)).deleteById(departmentId);
    }

    @Test
    void getDepartmentById_ValidId_ReturnsDepartment() {
        // Arrange
        Long departmentId = 1L;
        DepartmentDAO departmentDAO = new DepartmentDAO();
        Optional<DepartmentDAO> optionalDepartmentDAO = Optional.of(departmentDAO);
        when(departmentRepository.findById(departmentId)).thenReturn(optionalDepartmentDAO);

        // Act
        DepartmentDAO result = departmentService.getDepartmentById(departmentId);

        // Assert
        assertNotNull(result);
        assertSame(departmentDAO, result);
        verify(departmentRepository, times(1)).findById(departmentId);
    }

    @Test
    void getDepartmentById_DepartmentNotFound_ThrowsDepartmentNotFoundException() {
        // Arrange
        Long departmentId = 1L;
        Optional<DepartmentDAO> optionalDepartmentDAO = Optional.empty();
        when(departmentRepository.findById(departmentId)).thenReturn(optionalDepartmentDAO);

        // Act & Assert
        assertThrows(DepartmentNotFoundException.class, () -> departmentService.getDepartmentById(departmentId));
        verify(departmentRepository, times(1)).findById(departmentId);
    }

    @Test
    void getAllDepartments_NoPagination_ReturnsAllDepartments() {
        // Arrange
        when(departmentRepository.findAll(null, null)).thenReturn(Collections.singletonList(new DepartmentDAO()));

        // Act
        List<DepartmentDAO> result = departmentService.getAllDepartments(null, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(departmentRepository, times(1)).findAll(null, null);
    }

    @Test
    void getAllDepartments_WithPagination_ReturnsPaginatedDepartments() {
        // Arrange
        int page = 1;
        int size = 10;
        when(departmentRepository.findAll(page, size)).thenReturn(Collections.singletonList(new DepartmentDAO()));

        // Act
        List<DepartmentDAO> result = departmentService.getAllDepartments(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(departmentRepository, times(1)).findAll(page, size);
    }

}
