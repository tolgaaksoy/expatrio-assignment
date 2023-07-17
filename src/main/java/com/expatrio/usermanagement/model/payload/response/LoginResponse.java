package com.expatrio.usermanagement.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Login response.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends BaseResponse {

    private String token;

    private String type = "Bearer";

    private String username;

    private List<String> roles;

}
