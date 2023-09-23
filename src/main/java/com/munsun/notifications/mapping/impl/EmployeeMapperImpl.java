package com.munsun.notifications.mapping.impl;

import com.munsun.notifications.dto.in.EmployeeDtoIn;
import com.munsun.notifications.dto.out.EmployeeDtoOut;
import com.munsun.notifications.mapping.EmployeeMapper;
import com.munsun.notifications.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {
    @Override
    public Employee map(EmployeeDtoIn dto) {
        return new Employee(null, dto.getName(), dto.getSurname(), dto.getPatronymic());
    }

    @Override
    public EmployeeDtoOut map(Employee employee) {
        return new EmployeeDtoOut(employee.getName(), employee.getSurname(), employee.getPatronymic());
    }
}
