package com.expatrio.usermanagement.model.payload.request;

import com.expatrio.usermanagement.model.dao.RoleType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private Long id;

    private String username;
    private String password;
    private List<RoleType> roles;

    private String name;
    private BigDecimal salary;

    private Long departmentId;

}
