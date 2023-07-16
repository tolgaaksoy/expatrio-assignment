package com.expatrio.usermanagement.model.payload.request;

import com.expatrio.usermanagement.model.dao.RoleType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * The type Create user request.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    private String username;
    private String password;
    private List<RoleType> roles;

    private String name;
    private BigDecimal salary;

    private Long departmentId;

}
