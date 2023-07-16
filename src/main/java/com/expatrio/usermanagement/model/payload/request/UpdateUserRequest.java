package com.expatrio.usermanagement.model.payload.request;

import lombok.*;

import java.math.BigDecimal;

/**
 * The type Update user request.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private Long id;

    private String name;
    private BigDecimal salary;

    private Long departmentId;

}
