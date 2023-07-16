package com.expatrio.usermanagement.model.payload.request;

import lombok.*;

/**
 * The type Login request.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String username;
    private String password;

}
