package com.cercli.domain;

import java.util.List;
import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public interface EmployeeRepository {

    void addEmployee(Employee employee);
    void updateEmployee(Employee employee);
    Employee getEmployee(UUID id);
    List<Employee> getAllEmployees();
}
