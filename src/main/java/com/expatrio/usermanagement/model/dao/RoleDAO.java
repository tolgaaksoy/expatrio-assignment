package com.expatrio.usermanagement.model.dao;

import lombok.*;

/**
 * The type Role dao.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDAO {

    private Long id;
    private String roleType;

    /**
     * Instantiates a new Role dao.
     *
     * @param roleType the role type
     */
    public RoleDAO(RoleType roleType) {
        this.roleType = roleType.toString();
    }

}
