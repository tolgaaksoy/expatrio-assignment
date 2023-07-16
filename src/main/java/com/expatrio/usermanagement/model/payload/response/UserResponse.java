package com.expatrio.usermanagement.model.payload.response;

import com.expatrio.usermanagement.model.dao.UserDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type User response.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends BaseResponse {

    private UserDAO user;
    /**
     * The User list.
     */
    List<UserDAO> userList;

}
