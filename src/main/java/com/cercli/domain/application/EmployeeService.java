package com.cercli.domain.application;

import com.cercli.domain.core.Employee;
import com.cercli.port.EmployeeRepository;
import com.cercli.shared.exception.EmployeeCreationFailedException;
import com.cercli.shared.util.EmailValidator;

import java.util.List;
import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Service class for managing Employee business logics.
 * This service class represents what so-called as `application-domain` (a.k.a. `use-cases`)
 * within the context of <strong>Hexagonal Architecture</strong>.
 */
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * Constructs an EmployeeService with the specified repository.
     *
     * @param employeeRepository the repository to use for managing employees.
     */
    public EmployeeService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Adds a new employee to the repository.
     *
     * @param employee the employee to add.
     * @throws EmployeeCreationFailedException if the employee's email is invalid.
     */
    public void addEmployee(Employee employee) {
        if (!EmailValidator.isValidEmail(employee.email())) {
            throw new EmployeeCreationFailedException(
                    String.format("Invalid email for employee: %s", employee.email()));
        }

        employeeRepository.addEmployee(employee);
    }

    /**
     * Updates an existing employee in the repository.
     *
     * @param employee the employee to update.
     * @throws EmployeeCreationFailedException if the employee's email is invalid.
     */
    public void updateEmployee(Employee employee) {
        if (!EmailValidator.isValidEmail(employee.email())) {
            throw new EmployeeCreationFailedException(
                    String.format("Invalid email for employee: %s", employee.email()));
        }

        employeeRepository.updateEmployee(employee);
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param id the ID of the employee to retrieve.
     * @return the employee with the specified ID, or null if not found.
     */
    public Employee getEmployee(UUID id) {
        return employeeRepository.getEmployee(id);
    }

    /**
     * Retrieves all employees in the repository.
     *
     * @return a list of all employees.
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }
}
