package com.cercli.infrastructure.adapter.ui.cli;

import com.cercli.domain.application.command.*;
import com.cercli.domain.application.service.EmployeeService;
import com.cercli.domain.application.service.TimeOffRequestService;
import com.cercli.infrastructure.adapter.persistence.InMemoryEmployeeRepository;
import com.cercli.infrastructure.adapter.persistence.InMemoryRequestCategoryRepository;
import com.cercli.infrastructure.adapter.persistence.InMemoryTimeOffRequestRepository;
import com.cercli.port.EmployeeRepository;
import com.cercli.port.RequestCategoryRepository;
import com.cercli.port.TimeOffRequestRepository;

import java.util.Scanner;

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
        RequestCategoryRepository requestCategoryRepository = new InMemoryRequestCategoryRepository();
        TimeOffRequestRepository timeOffRequestRepository = new InMemoryTimeOffRequestRepository();
        TimeOffRequestService timeOffRequestService = new TimeOffRequestService(
                timeOffRequestRepository, requestCategoryRepository, employeeRepository
        );

        CommandRegistry commandRegistry = new CommandRegistry();
        commandRegistry.registerCommand("add-employee", new AddEmployeeCommand(employeeService, in));
        commandRegistry.registerCommand("list-employees", new ListEmployeeCommand(employeeService));
        commandRegistry.registerCommand("get-employee", new GetEmployeeCommand(employeeService, in));
        commandRegistry.registerCommand("add-request-category", new AddRequestCategoryCommand(requestCategoryRepository, in));
        commandRegistry.registerCommand("list-request-categories", new ListRequestCategoryCommand(requestCategoryRepository));
        commandRegistry.registerCommand("add-timeoff-request", new AddTimeOffRequestCommand(timeOffRequestService, in));
        commandRegistry.registerCommand("help", new HelpCommand());

        while (true) {
            System.out.println("Type 'help' to see the list of commands:");
            System.out.print("$ ");
            String line = in.nextLine();

            if ("exit".equals(line)) {
                System.out.println("--- Thank You ---");
                break;
            }

            var commands = line.split(" ");

            Command command = commandRegistry.getCommand(commands[0]);

            if (command != null) {
                command.execute(commands);
            } else {
                System.err.println("Unrecognized command! Please use one of the following commands:");
                HelpCommand.showCommandOptions();
            }
        }
    }
}
