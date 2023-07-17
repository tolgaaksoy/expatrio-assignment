package com.expatrio.usermanagement.model.payload.request;

import com.expatrio.usermanagement.model.dao.RoleType;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

/**
 * The type Update user authentication info request.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserAuthenticationInfoRequest {

    private Long id;

    @Size(max = 255, message = "Username must be less than 255 characters...")
    private String username;

    @Size(max = 255, message = "Password must be less than 255 characters...")
    private String password;

    private List<RoleType> roles;

}
