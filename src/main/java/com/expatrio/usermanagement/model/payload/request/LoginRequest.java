package com.expatrio.usermanagement.model.payload.request;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Username is required...")
    private String username;
    @NotBlank(message = "Password is required...")
    private String password;

}
