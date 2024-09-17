package com.cercli.application;

import com.cercli.domain.Employee;
import com.cercli.domain.EmployeeRepository;
import com.cercli.shared.exception.EmployeeCreationFailedException;
import com.cercli.shared.util.EmailValidator;

import java.util.List;
import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(Employee employee) {
        if (!EmailValidator.isValidEmail(employee.email())) {
            throw new EmployeeCreationFailedException(
                    String.format("Invalid email for employee: %s", employee.email()));
        }

        employeeRepository.addEmployee(employee);
    }

    public void updateEmployee(Employee employee) {
        if (!EmailValidator.isValidEmail(employee.email())) {
            throw new EmployeeCreationFailedException(
                    String.format("Invalid email for employee: %s", employee.email()));
        }

        employeeRepository.updateEmployee(employee);
    }

    public Employee getEmployee(UUID id) {
        return employeeRepository.getEmployee(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }
}
