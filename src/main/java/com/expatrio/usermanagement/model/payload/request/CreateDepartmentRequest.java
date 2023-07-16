package com.expatrio.usermanagement.model.payload.request;

import lombok.*;

/**
 * The type Create department request.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepartmentRequest {

    private String name;

}
