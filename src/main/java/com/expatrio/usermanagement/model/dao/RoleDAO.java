package com.expatrio.usermanagement.model.dao;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDAO {

    private Long id;
    private String roleType;

    public RoleDAO(RoleType roleType) {
        this.roleType = roleType.toString();
    }

}
