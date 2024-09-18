package com.cercli.port;

import com.cercli.domain.core.Employee;

import java.util.List;
import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Interface representing a repository for managing employee entities.
 * This interface represents `what so-called as `port` within the context of
 * <strong>Hexagonal Architecture</strong>.
 */
public interface EmployeeRepository {

    /**
     * Adds a new employee to the repository.
     *
     * @param employee the employee to add.
     */
    void addEmployee(Employee employee);

    /**
     * Updates an existing employee in the repository.
     *
     * @param employee the employee to update.
     */
    void updateEmployee(Employee employee);

    /**
     * Retrieves an employee by their ID.
     *
     * @param id the ID of the employee to retrieve.
     * @return the employee with the specified ID, or null if not found.
     */
    Employee getEmployee(UUID id);

    /**
     * Retrieves all employees in the repository.
     *
     * @return a list of all employees.
     */
    List<Employee> getAllEmployees();
}
