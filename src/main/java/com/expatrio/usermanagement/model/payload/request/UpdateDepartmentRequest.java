package com.expatrio.usermanagement.model.payload.request;

import jakarta.validation.constraints.Size;
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

    @Size(max = 255, message = "Department name must be less than 255 characters...")
    private String name;

}
