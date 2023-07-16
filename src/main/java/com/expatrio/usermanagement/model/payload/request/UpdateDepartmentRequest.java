package com.expatrio.usermanagement.model.payload.request;

import lombok.*;

/**
 * The type Update department request.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDepartmentRequest {

    private Long id;
    private String name;

}
