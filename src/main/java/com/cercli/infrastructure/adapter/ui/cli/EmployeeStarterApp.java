package com.cercli.infrastructure.adapter.ui.cli;

import com.cercli.domain.application.EmployeeService;
import com.cercli.domain.application.TimeOffRequestService;
import com.cercli.domain.core.Employee;
import com.cercli.domain.core.RequestCategory;
import com.cercli.domain.core.TimeOffRequest;
import com.cercli.infrastructure.adapter.persistence.InMemoryEmployeeRepository;
import com.cercli.infrastructure.adapter.persistence.InMemoryRequestCategoryRepository;
import com.cercli.infrastructure.adapter.persistence.InMemoryTimeOffRequestRepository;
import com.cercli.port.EmployeeRepository;
import com.cercli.port.RequestCategoryRepository;
import com.cercli.port.TimeOffRequestRepository;
import com.cercli.shared.exception.EmployeeNotFoundException;
import com.cercli.shared.exception.TimeOffRequestException;
import com.cercli.shared.util.DateTimeUtils;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

        TimeOffRequestRepository timeOffRequestRepository = new InMemoryTimeOffRequestRepository();
        RequestCategoryRepository requestCategoryRepository = new InMemoryRequestCategoryRepository();

        TimeOffRequestService timeOffRequestService = new TimeOffRequestService(
                timeOffRequestRepository, requestCategoryRepository, employeeRepository
        );

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
            } else if ("add-request-category".equalsIgnoreCase(commands[0])) {
                System.out.println("Enter the request category name: ");
                String name = in.nextLine();
                requestCategoryRepository.addRequestCategory(new RequestCategory(UUID.randomUUID(), name));
            } else if ("list-request-categories".equalsIgnoreCase(commands[0])) {
                for (RequestCategory requestCategory : requestCategoryRepository.getAllRequestCategories()) {
                    System.out.println(requestCategory);
                }
            } else if ("add-time-off-request".equalsIgnoreCase(commands[0])) {
                var requestCategoryId = getRequestCategoryId(in);
                while (requestCategoryId == null) {
                    requestCategoryId = getRequestCategoryId(in);
                }
                var selectedEmployeeId = getEmployeeId(in);
                while (selectedEmployeeId == null) {
                    selectedEmployeeId = getEmployeeId(in);
                }
                System.out.print("Enter the time-off start date (yyyy-mm-dd HH:mm, e.g.: 2024-09-18 07:00): ");
                var startDate = getZonedDateTime(in.nextLine());
                System.out.print("Enter the time-off end date (yyyy-mm-dd HH:mm, e.g.: 2024-09-25 07:00): ");
                var endDate = getZonedDateTime(in.nextLine());
                try {
                    timeOffRequestService.addTimeOffRequest(new TimeOffRequest(
                            UUID.randomUUID(), requestCategoryId, selectedEmployeeId, startDate, endDate
                    ));
                } catch (TimeOffRequestException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.err.println("Unrecognized command! Please use one of the following commands:");
                showCommandOptions();
            }
        }
    }

    private static ZonedDateTime getZonedDateTime(String enteredDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
        return LocalDateTime.parse(enteredDate, dtf).atZone(DateTimeUtils.SERVER_TIME_ZONE);
    }

    private static void showCommandOptions() {
        System.out.println();
        System.out.println("Employee Data");
        System.out.println("   add-employee: Add (register) a new employee");
        System.out.println("   list-employees: List all employees");
        System.out.println("   get-employee: Show employee details based on the selected ID");
        System.out.println();
        System.out.println("Time-Off Requests");
        System.out.println("   add-request-category: Add (register) a new time-off request category");
        System.out.println("   list-request-categories: List all time-off request categories");
        System.out.println("   add-time-off-request: Add (register) a new time off request");
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

    private static UUID getRequestCategoryId(Scanner in) {
        System.out.print("Enter the request category ID: ");
        try {
            return UUID.fromString(in.nextLine());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid request category ID!");
        }
        return null;
    }

    private static UUID getEmployeeId(Scanner in) {
        System.out.print("Enter the employee ID: ");
        try {
            return UUID.fromString(in.nextLine());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid employee ID!");
        }
        return null;
    }
}
