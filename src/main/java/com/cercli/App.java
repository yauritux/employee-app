package com.cercli;

import com.cercli.application.EmployeeService;
import com.cercli.domain.Employee;
import com.cercli.domain.EmployeeRepository;
import com.cercli.infrastructure.InMemoryEmployeeRepository;
import com.cercli.shared.exception.EmployeeNotFoundException;
import com.cercli.shared.util.DateTimeUtils;

import java.util.Scanner;
import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 */
public class App {

    public static void main( String[] args ) {
        run();
    }

    private static void run() {
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
            } else if ("add".equalsIgnoreCase(commands[0])) {
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
            } else if ("list".equalsIgnoreCase(commands[0])) {
                System.out.println("List of employees:");
                var records = employeeService.getAllEmployees();
                if (records.isEmpty()) {
                    System.out.println("-- No records found ---");
                    continue;
                }
                for (Employee employee : records) {
                    System.out.println(employee);
                }
            } else if ("get".equalsIgnoreCase(commands[0])) {
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
        System.out.println("add - Register a new employee");
        System.out.println("list - List all employees");
        System.out.println("get - Show employee details based on the selected ID");
        System.out.println("exit - Exit the application");
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
