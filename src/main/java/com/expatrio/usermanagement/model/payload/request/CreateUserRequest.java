package com.expatrio.usermanagement.model.payload.request;

import com.expatrio.usermanagement.model.dao.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Username is required...")
    @Size(max = 255, message = "Username must be less than 255 characters...")
    private String username;

    @NotBlank(message = "Password is required...")
    @Size(max = 255, message = "Password must be less than 255 characters...")
    private String password;

    private List<RoleType> roles;

    @Size(max = 255, message = "Name must be less than 255 characters...")
    private String name;
    private BigDecimal salary;

    private Long departmentId;

}
