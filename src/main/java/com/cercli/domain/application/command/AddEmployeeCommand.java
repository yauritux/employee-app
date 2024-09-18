package com.cercli.domain.application.command;

import com.cercli.domain.application.service.EmployeeService;
import com.cercli.domain.core.Employee;
import com.cercli.shared.util.DateTimeUtils;

import java.util.Scanner;
import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 */
public class AddEmployeeCommand implements Command {

    private final EmployeeService employeeService;
    private final Scanner in;

    public AddEmployeeCommand(EmployeeService employeeService, Scanner in) {
        this.employeeService = employeeService;
        this.in = in;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Registering a new employee...");
        System.out.print("Enter the employee name: ");
        String name = in.nextLine();
        System.out.print("Enter the employee position: ");
        String position = in.nextLine();
        System.out.print("Enter the employee email: ");
        String email = in.nextLine();
        float salary = getSalaryEntered();
        while (salary == 0) {
            salary = getSalaryEntered();
        }

        employeeService.addEmployee(new Employee(
                UUID.randomUUID(),
                name, position, email, salary,
                DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                DateTimeUtils.getCurrentDateTimeInServerTimeZone()
        ));
    }

    private float getSalaryEntered() {
        System.out.print("Enter the employee salary in USD: ");
        try {
            return Float.parseFloat(in.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Invalid salary amount. Please provide only numbers!");
            System.err.println("E.g., 7500");
        }
        return 0;
    }
}
