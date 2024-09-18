package com.cercli.domain.application.command;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public class HelpCommand implements Command {

    @Override
    public void execute(String[] args) {
        System.out.println("You can use one of the following commands:");
        showCommandOptions();
    }

    public static void showCommandOptions() {
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
}
