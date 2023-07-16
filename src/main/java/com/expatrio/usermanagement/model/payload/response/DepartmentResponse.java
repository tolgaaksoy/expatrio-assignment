package com.expatrio.usermanagement.model.payload.response;

import com.expatrio.usermanagement.model.dao.DepartmentDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Department response.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse extends BaseResponse {

    private DepartmentDAO department;
    private List<DepartmentDAO> departmentList;

}
