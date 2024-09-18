package com.cercli.application;

import com.cercli.domain.core.Employee;
import com.cercli.port.EmployeeRepository;
import com.cercli.domain.application.EmployeeService;
import com.cercli.infrastructure.adapter.persistence.InMemoryEmployeeRepository;
import com.cercli.shared.exception.EmployeeCreationFailedException;
import com.cercli.shared.util.DateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
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

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee(
                UUID.randomUUID(),
                "Yauri Attamimi",
                "AI Engineer",
                "yauritux@gmail.com",
                7500,
                DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                DateTimeUtils.getCurrentDateTimeInServerTimeZone()
        );

        employeeService.addEmployee(employee);

        Employee updatedEmployee = new Employee(
                employee.id(),
                "Yauri Attamimi",
                "Software Architect",
                "yauritux@gmail.com",
                9000,
                DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                DateTimeUtils.getCurrentDateTimeInServerTimeZone()
        );

        employeeService.updateEmployee(updatedEmployee);

        Employee retrievedEmployee = employeeService.getEmployee(employee.id());
        assertNotNull(retrievedEmployee);
        assertEquals(updatedEmployee.position(), retrievedEmployee.position());
        assertEquals(updatedEmployee.salary(), retrievedEmployee.salary());
    }

    @Test
    public void testGetAllEmployees() {
        Employee emp1 = new Employee(
                UUID.randomUUID(),
                "Yauri Attamimi",
                "AI Engineer",
                "yauritux@gmail.com",
                9000,
                DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                DateTimeUtils.getCurrentDateTimeInServerTimeZone()
        );

        Employee emp2 = new Employee(
                UUID.randomUUID(),
                "Joe Yabuki",
                "Software Engineer",
                "joey@gmail.com",
                5000,
                DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                DateTimeUtils.getCurrentDateTimeInServerTimeZone()
        );

        employeeService.addEmployee(emp1);
        employeeService.addEmployee(emp2);

        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(2, employees.size());
        assertTrue(employees.contains(emp1));
        assertTrue(employees.contains(emp2));
    }
}
