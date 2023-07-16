package com.expatrio.usermanagement.model.payload;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentAverageSalaryDto {

    private Long departmentId;
    private String departmentName;
    private BigDecimal averageSalary;

}
