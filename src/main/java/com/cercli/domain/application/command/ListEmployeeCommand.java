package com.cercli.domain.application.command;

import com.cercli.domain.application.service.EmployeeService;
import com.cercli.domain.core.Employee;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public class ListEmployeeCommand implements Command {

    private final EmployeeService employeeService;

    public ListEmployeeCommand(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("List of employees:");
        var records = employeeService.getAllEmployees();
        if (records.isEmpty()) {
            System.out.println("--- No records found ---");
            return;
        }
        for (Employee employee : records) {
            System.out.println(employee);
        }
    }
}
