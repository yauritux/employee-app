package com.cercli.infrastructure;

import com.cercli.domain.Employee;
import com.cercli.domain.EmployeeRepository;
import com.cercli.shared.exception.EmployeeNotFoundException;

import java.util.*;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public class InMemoryEmployeeRepository implements EmployeeRepository {

    private final Map<UUID, Employee> employees = new HashMap<>();

    @Override
    public void addEmployee(Employee employee) {
        employees.put(employee.id(), employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        if (!employees.containsKey(employee.id())) {
            throw new EmployeeNotFoundException(
                    String.format("Cannot find employee with ID %s", employee.id()));
        }

        employees.put(employee.id(), employee);
    }

    @Override
    public Employee getEmployee(UUID id) {
        if (!employees.containsKey(id)) {
            throw new EmployeeNotFoundException(
                    String.format("Cannot find employee with ID %s", id)
            );
        }
        return employees.get(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees.values());
    }
}
