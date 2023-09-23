package com.munsun.notifications.repository;

import com.munsun.notifications.Container;
import static org.junit.jupiter.api.Assertions.*;

import com.munsun.notifications.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryIntegrationTests extends Container {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void findByName_NotFound() {
        var actual = employeeRepository.findEmployeeByNameAndSurnameAndPatronymic("testName", "testSurname", "testPatronymic");
        assertTrue(actual.isEmpty());
    }

    @Test
    public void findByName() {
        var employee = new Employee();
            employee.setId(0);
            employee.setName("testName");
            employee.setSurname("testSurname");
            employee.setPatronymic("testPatronymic");
        employeeRepository.save(employee);

        var actual = employeeRepository.findEmployeeByNameAndSurnameAndPatronymic(employee.getName(), employee.getSurname(), employee.getPatronymic());

        assertTrue(actual.isPresent());
        assertAll(() -> {
            assertNotEquals(0, actual.get().getId());
            assertEquals(employee.getName(), actual.get().getName());
            assertEquals(employee.getSurname(), actual.get().getSurname());
            assertEquals(employee.getPatronymic(), actual.get().getPatronymic());
        });
    }
}
