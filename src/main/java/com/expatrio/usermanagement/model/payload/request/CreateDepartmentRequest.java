package com.expatrio.usermanagement.model.payload.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepartmentRequest {

    private String name;

}
