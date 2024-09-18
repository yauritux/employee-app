package com.cercli.infrastructure.adapter.ui.cli;

import com.cercli.domain.application.EmployeeService;
import com.cercli.domain.core.Employee;
import com.cercli.infrastructure.adapter.persistence.InMemoryEmployeeRepository;
import com.cercli.port.EmployeeRepository;
import com.cercli.shared.exception.EmployeeNotFoundException;
import com.cercli.shared.util.DateTimeUtils;

import java.util.Scanner;
import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * The main CLI (`Command-Line-Interface`) application represents
 * the infrastructure (a.k.a. `adapter`) part in our
 * <strong>Hexagonal Architecture</strong>.
 */
public class EmployeeStarterApp {

    /**
     * Main entry point for running the CLI app.
     */
    public static void run() {
        Scanner in = new Scanner(System.in);

        EmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        while (true) {
            System.out.println("Type 'help' to see the list of commands:");
            System.out.print("$ ");
            String line = in.nextLine();

            if ("exit".equals(line)) {
                System.out.println("--- Thank You ---");
                break;
            }

            var commands = line.split(" ");

            if ("help".equalsIgnoreCase(commands[0])) {
                System.out.println("You can use one of the following commands:");
                showCommandOptions();
            } else if ("add-employee".equalsIgnoreCase(commands[0])) {
                System.out.println("Registering a new employee...");
                System.out.print("Enter the employee name: ");
                String name = in.nextLine();
                System.out.print("Enter the employee position: ");
                String position = in.nextLine();
                System.out.print("Enter the employee email: ");
                String email = in.nextLine();
                float salary = getSalaryEntered(in);
                while (salary == 0) {
                    salary = getSalaryEntered(in);
                }
                employeeService.addEmployee(new Employee(
                        UUID.randomUUID(),
                        name, position, email, salary,
                        DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                        DateTimeUtils.getCurrentDateTimeInServerTimeZone()
                ));
            } else if ("list-employees".equalsIgnoreCase(commands[0])) {
                System.out.println("List of employees:");
                var records = employeeService.getAllEmployees();
                if (records.isEmpty()) {
                    System.out.println("-- No records found ---");
                    continue;
                }
                for (Employee employee : records) {
                    System.out.println(employee);
                }
            } else if ("get-employee".equalsIgnoreCase(commands[0])) {
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
            } else {
                System.err.println("Unrecognized command! Please use one of the following commands:");
                showCommandOptions();
            }
        }
    }

    private static void showCommandOptions() {
        System.out.println();
        System.out.println("Employee Data");
        System.out.println("   add-employee: Add (register) a new employee");
        System.out.println("   list-employees: List all employees");
        System.out.println("   get-employee: Show employee details based on the selected ID");
        System.out.println();
        System.out.println("Others");
        System.out.println("   exit: End the application");
        System.out.println();
    }

    private static float getSalaryEntered(Scanner in) {
        System.out.print("Enter the employee salary in USD: ");
        try {
            return Float.parseFloat(in.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("Invalid salary amount. Please provide number!");
            System.err.println("E.g. : 7500");
        }
        return 0;
    }
}
