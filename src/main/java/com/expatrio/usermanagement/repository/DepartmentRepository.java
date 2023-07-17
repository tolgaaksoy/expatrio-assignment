package com.expatrio.usermanagement.repository;

import com.expatrio.usermanagement.model.dao.DepartmentDAO;
import com.expatrio.usermanagement.model.payload.DepartmentAverageSalaryDto;
import com.expatrio.usermanagement.model.tables.AuthUser;
import com.expatrio.usermanagement.model.tables.Department;
import com.expatrio.usermanagement.model.tables.records.DepartmentRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.jooq.impl.DSL.avg;

/**
 * The type Department repository.
 */
@Repository
public class DepartmentRepository implements JOOQRepository<DepartmentDAO> {

    private final DSLContext dsl;

    /**
     * Instantiates a new Department repository.
     *
     * @param dsl the dsl
     */
    public DepartmentRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Transactional
    @Override
    public DepartmentDAO save(DepartmentDAO entity) {
        DepartmentRecord departmentRecord = dsl.insertInto(Department.DEPARTMENT)
                .set(Department.DEPARTMENT.NAME, entity.getName())
                .returning(Department.DEPARTMENT.ID)
                .fetchOne();

        if (departmentRecord != null) {
            entity.setId(departmentRecord.getId());
            return entity;
        }
        return null;
    }

    @Transactional
    @Override
    public DepartmentDAO update(DepartmentDAO entity) {
        int updatedRows = dsl.update(Department.DEPARTMENT)
                .set(Department.DEPARTMENT.NAME, entity.getName())
                .where(Department.DEPARTMENT.ID.eq(entity.getId()))
                .execute();

        return updatedRows > 0 ? entity : null;
    }

    @Override
    public boolean deleteById(Long id) {
        int deletedRows = dsl.deleteFrom(Department.DEPARTMENT)
                .where(Department.DEPARTMENT.ID.eq(id))
                .execute();

        return deletedRows > 0;
    }

    @Override
    public Optional<DepartmentDAO> findById(Long id) {
        DepartmentDAO departmentDAO = dsl.selectFrom(Department.DEPARTMENT)
                .where(Department.DEPARTMENT.ID.eq(id))
                .fetchOneInto(DepartmentDAO.class);

        return Optional.ofNullable(departmentDAO);
    }

    @Override
    public List<DepartmentDAO> findAll(Integer requestedPage, Integer requestedPageSize) {

        int page = requestedPage == null ? JOOQRepository.DEFAULT_PAGE_NUMBER : requestedPage;
        int size = requestedPageSize == null ? JOOQRepository.DEFAULT_PAGE_SIZE : requestedPageSize;

        return dsl.select()
                .from(Department.DEPARTMENT)
                .orderBy(Department.DEPARTMENT.ID)
                .offset(page * size)
                .limit(size)
                .fetchInto(DepartmentDAO.class);
    }

    @Override
    public int count() {
        return dsl.fetchCount(Department.DEPARTMENT);
    }

    @Override
    public boolean existsById(Long id) {
        return dsl.fetchExists(Department.DEPARTMENT, Department.DEPARTMENT.ID.eq(id));
    }


    public List<DepartmentAverageSalaryDto> getAverageSalaryPerDepartment() {
        return dsl.select(Department.DEPARTMENT.ID, Department.DEPARTMENT.NAME, avg(AuthUser.AUTH_USER.SALARY))
                .from(Department.DEPARTMENT)
                .leftJoin(AuthUser.AUTH_USER)
                .on(AuthUser.AUTH_USER.DEPARTMENT_ID.eq(Department.DEPARTMENT.ID))
                .groupBy(Department.DEPARTMENT.ID, Department.DEPARTMENT.NAME)
                .fetchInto(DepartmentAverageSalaryDto.class);
    }

}
