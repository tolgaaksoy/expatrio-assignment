package com.expatrio.usermanagement.model.dao;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

/**
 * The type User dao.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDAO {

    private Long id;
    private String username;
    private String password;
    private Set<RoleDAO> roles;

    private String name;
    private BigDecimal salary;
    private DepartmentDAO department;


}
