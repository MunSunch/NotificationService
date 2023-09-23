package com.munsun.notifications.mapping;

import com.munsun.notifications.dto.in.EmployeeDtoIn;
import com.munsun.notifications.dto.out.EmployeeDtoOut;
import com.munsun.notifications.model.Employee;

public interface EmployeeMapper {
    Employee map(EmployeeDtoIn employeeDtoIn);
    EmployeeDtoOut map(Employee employee);
}
