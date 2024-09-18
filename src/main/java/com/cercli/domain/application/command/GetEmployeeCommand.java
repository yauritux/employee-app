package com.cercli.domain.application.command;

import com.cercli.domain.application.service.EmployeeService;
import com.cercli.shared.exception.EmployeeNotFoundException;

import java.util.Scanner;
import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public class GetEmployeeCommand implements Command {

    private final EmployeeService employeeService;
    private final Scanner in;

    public GetEmployeeCommand(EmployeeService employeeService, Scanner in) {
        this.employeeService = employeeService;
        this.in = in;
    }

    @Override
    public void execute(String[] args) {
        System.out.print("Enter the employee ID: ");
        String id = in.nextLine();
        try {
            var emp = employeeService.getEmployee(UUID.fromString(id));
            System.out.print(emp);
        } catch (IllegalArgumentException e) {
            System.err.printf("Invalid employee ID: %s\n", id);
        } catch (EmployeeNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
