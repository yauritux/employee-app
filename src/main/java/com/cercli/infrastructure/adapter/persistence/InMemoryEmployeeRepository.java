package com.cercli.infrastructure.adapter.persistence;

import com.cercli.domain.core.Employee;
import com.cercli.port.EmployeeRepository;
import com.cercli.shared.exception.EmployeeNotFoundException;

import java.util.*;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * In-Memory implementation of the <code>EmployeeRepository</code> interface.
 * This represents what so-called as an `adapter` within the context of
 * our <strong>Hexagonal Architecture</strong>, that implement the
 * <code>EmployeeRepository</code> port interface.
 */
public class InMemoryEmployeeRepository implements EmployeeRepository {

    private final Map<UUID, Employee> employees = new HashMap<>();

    /**
     * Adds a new employee to the repository.
     *
     * @param employee the employee to add.
     */
    @Override
    public void addEmployee(Employee employee) {
        employees.put(employee.id(), employee);
    }

    /**
     * Updates an existing employee in the repository.
     *
     * @param employee the employee to update.
     * @throws EmployeeNotFoundException if the employee does not exist.
     */
    @Override
    public void updateEmployee(Employee employee) {
        if (!employees.containsKey(employee.id())) {
            throw new EmployeeNotFoundException(
                    String.format("Cannot find employee with ID %s", employee.id()));
        }

        employees.put(employee.id(), employee);
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param id the ID of the employee to retrieve.
     * @return the employee with the specified ID, or null if not found.
     */
    @Override
    public Employee getEmployee(UUID id) {
        if (!employees.containsKey(id)) {
            throw new EmployeeNotFoundException(
                    String.format("Cannot find employee with ID %s", id)
            );
        }
        return employees.get(id);
    }

    /**
     * Retrieves all employees in the repository.
     *
     * @return a list of all employees.
     */
    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }
}
