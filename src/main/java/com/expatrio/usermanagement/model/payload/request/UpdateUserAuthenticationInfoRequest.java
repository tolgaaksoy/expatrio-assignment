package com.expatrio.usermanagement.model.payload.request;

import com.expatrio.usermanagement.model.dao.RoleType;
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

    private String username;
    private String password;

    private List<RoleType> roles;

}
