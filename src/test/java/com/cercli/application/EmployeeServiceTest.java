package com.cercli.application;

import com.cercli.domain.Employee;
import com.cercli.domain.EmployeeRepository;
import com.cercli.infrastructure.InMemoryEmployeeRepository;
import com.cercli.shared.exception.EmployeeCreationFailedException;
import com.cercli.shared.util.DateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        EmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    public void testAddEmployee() {
        Employee employee = new Employee(
                UUID.randomUUID(),
                "Yauri Attamimi",
                "AI Engineer",
                "yauritux@gmail.com",
                11000,
                DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                DateTimeUtils.getCurrentDateTimeInServerTimeZone()
        );

        employeeService.addEmployee(employee);
        Employee retrievedEmployee = employeeService.getEmployee(employee.id());

        assertNotNull(retrievedEmployee);
        assertEquals(employee.id(), retrievedEmployee.id());
    }

    @Test
    public void testAddEmployeeWithInvalidEmail() {
        Employee employee = new Employee(
                UUID.randomUUID(),
                "Yauri Attamimi",
                "AI Engineer",
                "yauritux",
                9000,
                DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                DateTimeUtils.getCurrentDateTimeInServerTimeZone()
        );

        var thrown = assertThrows(EmployeeCreationFailedException.class,
                () -> employeeService.addEmployee(employee));

        assertEquals(String.format(
                "Invalid email for employee: %s", employee.email()), thrown.getMessage());
    }
}
