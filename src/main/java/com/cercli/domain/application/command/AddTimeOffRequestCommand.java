package com.cercli.domain.application.command;

import com.cercli.domain.application.service.TimeOffRequestService;
import com.cercli.domain.core.TimeOffRequest;
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
 */
public class AddTimeOffRequestCommand implements Command {

    private final TimeOffRequestService timeOffRequestService;
    private final Scanner in;

    public AddTimeOffRequestCommand(TimeOffRequestService timeOffRequestService, Scanner in) {
        this.timeOffRequestService = timeOffRequestService;
        this.in = in;
    }

    @Override
    public void execute(String[] args) {
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

    private static ZonedDateTime getZonedDateTime(String enteredDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
        return LocalDateTime.parse(enteredDate, dtf).atZone(DateTimeUtils.SERVER_TIME_ZONE);
    }
}
