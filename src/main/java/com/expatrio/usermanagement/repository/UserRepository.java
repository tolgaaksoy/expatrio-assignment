package com.expatrio.usermanagement.repository;

import com.expatrio.usermanagement.model.dao.DepartmentDAO;
import com.expatrio.usermanagement.model.dao.RoleDAO;
import com.expatrio.usermanagement.model.dao.UserDAO;
import com.expatrio.usermanagement.model.tables.AuthRole;
import com.expatrio.usermanagement.model.tables.AuthUser;
import com.expatrio.usermanagement.model.tables.AuthUserRole;
import com.expatrio.usermanagement.model.tables.Department;
import com.expatrio.usermanagement.model.tables.records.AuthRoleRecord;
import com.expatrio.usermanagement.model.tables.records.AuthUserRecord;
import com.expatrio.usermanagement.model.tables.records.AuthUserRoleRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectLimitPercentAfterOffsetStep;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * The type User repository.
 */
@Repository
public class UserRepository implements JOOQRepository<UserDAO> {

    private final DSLContext dsl;

    /**
     * Instantiates a new User repository.
     *
     * @param dslContext the DSL context
     */
    public UserRepository(DSLContext dslContext) {
        this.dsl = dslContext;
    }

    @Transactional
    @Override
    public UserDAO save(UserDAO dao) {
        if (dao == null) {
            return null;
        }

        UserDAO savedUserDAO = Objects.requireNonNull(dsl.insertInto(AuthUser.AUTH_USER)
                        .set(dsl.newRecord(AuthUser.AUTH_USER, dao))
                        .returning()
                        .fetchOne())
                .into(UserDAO.class);

        // Save user roles
        if (dao.getRoles() != null && !dao.getRoles().isEmpty()) {
            for (RoleDAO roleDAO : dao.getRoles()) {
                AuthRoleRecord roleRecord = dsl.selectFrom(AuthRole.AUTH_ROLE)
                        .where(AuthRole.AUTH_ROLE.ROLE_TYPE.eq(roleDAO.getRoleType()))
                        .fetchOne();

                if (roleRecord != null) {
                    dsl.insertInto(AuthUserRole.AUTH_USER_ROLE)
                            .set(dsl.newRecord(AuthUserRole.AUTH_USER_ROLE, new AuthUserRoleRecord(
                                    savedUserDAO.getId(),
                                    roleRecord.getId()
                            )))
                            .execute();
                }
            }
            savedUserDAO.setRoles(new HashSet<>(dao.getRoles()));
        }

        // Save user department
        if (dao.getDepartment() != null && dao.getDepartment().getId() != null) {
            dsl.update(AuthUser.AUTH_USER)
                    .set(AuthUser.AUTH_USER.DEPARTMENT_ID, dao.getDepartment().getId())
                    .where(AuthUser.AUTH_USER.ID.eq(savedUserDAO.getId()))
                    .execute();

            savedUserDAO.setDepartment(dao.getDepartment());
        }

        savedUserDAO.setPassword(null);
        return savedUserDAO;
    }

    @Transactional
    @Override
    public UserDAO update(UserDAO userDAO) {
        if (userDAO == null || userDAO.getId() == null) {
            return null;
        }

        // Update user information
        int updatedRows = dsl.update(AuthUser.AUTH_USER)
                .set(dsl.newRecord(AuthUser.AUTH_USER, userDAO))
                .where(AuthUser.AUTH_USER.ID.eq(userDAO.getId()))
                .execute();

        // Update user's roles
        if (userDAO.getRoles() != null && !userDAO.getRoles().isEmpty()) {
            // Delete user's existing roles
            dsl.deleteFrom(AuthUserRole.AUTH_USER_ROLE)
                    .where(AuthUserRole.AUTH_USER_ROLE.USER_ID.eq(userDAO.getId()))
                    .execute();

            for (RoleDAO roleDAO : userDAO.getRoles()) {
                AuthRoleRecord roleRecord = dsl.selectFrom(AuthRole.AUTH_ROLE)
                        .where(AuthRole.AUTH_ROLE.ROLE_TYPE.eq(roleDAO.getRoleType()))
                        .fetchOne();

                if (roleRecord != null) {
                    dsl.insertInto(AuthUserRole.AUTH_USER_ROLE)
                            .set(dsl.newRecord(AuthUserRole.AUTH_USER_ROLE, new AuthUserRoleRecord(
                                    userDAO.getId(),
                                    roleRecord.getId()
                            )))
                            .execute();
                }
            }
        }

        // Update user's department
        if (userDAO.getDepartment() != null && userDAO.getDepartment().getId() != null) {
            dsl.update(AuthUser.AUTH_USER)
                    .set(AuthUser.AUTH_USER.DEPARTMENT_ID, userDAO.getDepartment().getId())
                    .where(AuthUser.AUTH_USER.ID.eq(userDAO.getId()))
                    .execute();
        }

        return updatedRows > 0 ? userDAO : null;
    }

    @Transactional
    @Override
    public boolean deleteById(Long userId) {
        // Delete user roles
        dsl.deleteFrom(AuthUserRole.AUTH_USER_ROLE)
                .where(AuthUserRole.AUTH_USER_ROLE.USER_ID.eq(userId))
                .execute();

        // Remove user from department
        dsl.update(AuthUser.AUTH_USER)
                .setNull(AuthUser.AUTH_USER.DEPARTMENT_ID)
                .where(AuthUser.AUTH_USER.ID.eq(userId))
                .execute();

        // Delete the user
        int deletedRows = dsl.deleteFrom(AuthUser.AUTH_USER)
                .where(AuthUser.AUTH_USER.ID.eq(userId))
                .execute();

        return deletedRows > 0;
    }

    @Override
    public Optional<UserDAO> findById(Long userId) {

        AuthUserRecord authUserRecord = dsl.selectFrom(AuthUser.AUTH_USER)
                .where(AuthUser.AUTH_USER.ID.eq(userId))
                .fetchOne();

        if (authUserRecord == null) {
            return Optional.empty();
        }

        UserDAO userDAO = authUserRecord.into(UserDAO.class);

        // Fetch user roles
        List<RoleDAO> roles = dsl.select(AuthRole.AUTH_ROLE.ID, AuthRole.AUTH_ROLE.ROLE_TYPE)
                .from(AuthRole.AUTH_ROLE)
                .join(AuthUserRole.AUTH_USER_ROLE)
                .on(AuthUserRole.AUTH_USER_ROLE.ROLE_ID.eq(AuthRole.AUTH_ROLE.ID))
                .where(AuthUserRole.AUTH_USER_ROLE.USER_ID.eq(userDAO.getId()))
                .fetchInto(RoleDAO.class);

        userDAO.setRoles(new HashSet<>(roles));

        // Fetch department name
        if (userDAO.getDepartment() != null && userDAO.getDepartment().getId() != null) {
            DepartmentDAO departmentDAO = dsl.select(Department.DEPARTMENT.ID, Department.DEPARTMENT.NAME)
                    .from(Department.DEPARTMENT)
                    .where(Department.DEPARTMENT.ID.eq(userDAO.getDepartment().getId()))
                    .fetchOneInto(DepartmentDAO.class);

            if (departmentDAO != null) {
                userDAO.getDepartment().setName(departmentDAO.getName());
            }
        }

        return Optional.of(userDAO);
    }

    @Override
    public List<UserDAO> findAll(Integer requestedPage, Integer requestedPageSize) {
        int page = requestedPage == null ? JOOQRepository.DEFAULT_PAGE_NUMBER : requestedPage;
        int size = requestedPageSize == null ? JOOQRepository.DEFAULT_PAGE_SIZE : requestedPageSize;

        SelectLimitPercentAfterOffsetStep<Record> select = dsl.select()
                .from(AuthUser.AUTH_USER)
                .leftJoin(AuthUserRole.AUTH_USER_ROLE)
                .on(AuthUser.AUTH_USER.ID.eq(AuthUserRole.AUTH_USER_ROLE.USER_ID))
                .leftJoin(AuthRole.AUTH_ROLE)
                .on(AuthUserRole.AUTH_USER_ROLE.ROLE_ID.eq(AuthRole.AUTH_ROLE.ID))
                .leftJoin(Department.DEPARTMENT)
                .on(AuthUser.AUTH_USER.DEPARTMENT_ID.eq(Department.DEPARTMENT.ID))
                .orderBy(AuthUser.AUTH_USER.ID.asc())
                .offset(page * size)
                .limit(size);

        Result<Record> result = select.fetch();

        Map<AuthUserRecord, List<AuthRoleRecord>> userRoleMap = result.stream()
                .collect(groupingBy(
                        r -> r.into(AuthUser.AUTH_USER),
                        mapping(r -> r.into(AuthRole.AUTH_ROLE), toList())
                ));

        return userRoleMap.entrySet().stream()
                .map(entry -> {
                    AuthUserRecord authUserRecord = entry.getKey();
                    UserDAO userDAO = authUserRecord.into(UserDAO.class);
                    userDAO.setPassword(null);
                    List<AuthRoleRecord> roleRecords = entry.getValue();
                    Set<RoleDAO> roles = roleRecords.stream()
                            .map(record1 -> record1.into(AuthRole.AUTH_ROLE).into(RoleDAO.class))
                            .collect(toSet());
                    userDAO.setRoles(roles);
                    return userDAO;
                })
                .toList();
    }


    /**
     * Find by username optional.
     *
     * @param username the username
     * @return the optional
     */
    public Optional<UserDAO> findByUsername(String username) {
        AuthUserRecord authUserRecord = dsl.selectFrom(AuthUser.AUTH_USER)
                .where(AuthUser.AUTH_USER.USERNAME.eq(username))
                .fetchOne();

        if (authUserRecord == null) {
            return Optional.empty();
        }

        UserDAO userDAO = authUserRecord.into(UserDAO.class);

        // Fetch user roles
        List<RoleDAO> roles = dsl.select(AuthRole.AUTH_ROLE.ID, AuthRole.AUTH_ROLE.ROLE_TYPE)
                .from(AuthRole.AUTH_ROLE)
                .join(AuthUserRole.AUTH_USER_ROLE)
                .on(AuthUserRole.AUTH_USER_ROLE.ROLE_ID.eq(AuthRole.AUTH_ROLE.ID))
                .where(AuthUserRole.AUTH_USER_ROLE.USER_ID.eq(userDAO.getId()))
                .fetchInto(RoleDAO.class);

        userDAO.setRoles(new HashSet<>(roles));

        return Optional.of(userDAO);
    }

    @Override
    public int count() {
        return dsl.fetchCount(AuthUser.AUTH_USER);
    }

}