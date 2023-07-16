package com.expatrio.usermanagement.model.payload.request;

import com.expatrio.usermanagement.model.dao.RoleType;
import lombok.*;

import java.util.List;

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
