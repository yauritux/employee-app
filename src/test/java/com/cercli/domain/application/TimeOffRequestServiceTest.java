package com.cercli.domain.application;

import com.cercli.domain.core.Employee;
import com.cercli.domain.core.RequestCategory;
import com.cercli.domain.core.TimeOffRequest;
import com.cercli.infrastructure.adapter.persistence.InMemoryEmployeeRepository;
import com.cercli.infrastructure.adapter.persistence.InMemoryRequestCategoryRepository;
import com.cercli.infrastructure.adapter.persistence.InMemoryTimeOffRequestRepository;
import com.cercli.port.EmployeeRepository;
import com.cercli.port.RequestCategoryRepository;
import com.cercli.port.TimeOffRequestRepository;
import com.cercli.shared.exception.TimeOffRequestException;
import com.cercli.shared.util.DateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public class TimeOffRequestServiceTest {

    private TimeOffRequestRepository timeOffRequestRepository;
    private EmployeeRepository employeeRepository;
    private TimeOffRequestService timeOffRequestService;

    private final RequestCategory workRemotelyCategory =
            new RequestCategory(UUID.randomUUID(), "Work Remotely");
    private final RequestCategory annualLeaveCategory =
            new RequestCategory(UUID.randomUUID(), "Annual Leave");

    @BeforeEach
    public void setUp() {
        timeOffRequestRepository = new InMemoryTimeOffRequestRepository();
        RequestCategoryRepository requestCategoryRepository = new InMemoryRequestCategoryRepository();
        employeeRepository = new InMemoryEmployeeRepository();
        timeOffRequestService = new TimeOffRequestService(
                timeOffRequestRepository, requestCategoryRepository, employeeRepository);

        // Add request categories
        requestCategoryRepository.addRequestCategory(workRemotelyCategory);
        requestCategoryRepository.addRequestCategory(annualLeaveCategory);

        // Add employee
        employeeRepository.addEmployee(
                new Employee(UUID.randomUUID(), "Yauri Attamimi", "AI Engineer",
                        "yauritux@gmail.com", 9000,
                        DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                        DateTimeUtils.getCurrentDateTimeInServerTimeZone())
        );
    }

    @Test
    public void testAddTimeOffRequest() {
        var employeeId = employeeRepository.getAllEmployees().get(0).id();
        TimeOffRequest request1 = new TimeOffRequest(
                UUID.randomUUID(),
                workRemotelyCategory.id(),
                employeeId,
                DateTimeUtils.convertToLocalDateTime(DateTimeUtils.getCurrentDateTimeInServerTimeZone()),
                DateTimeUtils.convertToLocalDateTime(DateTimeUtils.getCurrentDateTimeInServerTimeZone())
        );

        timeOffRequestService.addTimeOffRequest(request1);
        TimeOffRequest retrievedRequest = timeOffRequestRepository.getTimeOffRequestsByEmployeeId(employeeId).get(0);

        assertNotNull(retrievedRequest);
        assertEquals(request1.id(), retrievedRequest.id());
    }

    @Test
    public void testAddTimeOffRequestCategoryNotFound() {
        var employeeId = employeeRepository.getAllEmployees().get(0).id();
        var categoryId = UUID.randomUUID();
        TimeOffRequest request = new TimeOffRequest(
                UUID.randomUUID(),
                categoryId,
                employeeId,
                DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                DateTimeUtils.getCurrentDateTimeInServerTimeZone()
        );

        var thrown = assertThrows(TimeOffRequestException.class, () -> timeOffRequestService.addTimeOffRequest(request));
        assertEquals(String.format(
                "Failed to find request category with id %s", categoryId
        ), thrown.getMessage());
    }

    @Test
    public void testAddTimeOffRequestEmployeeNotFound() {
        var employeeId = UUID.randomUUID();
        TimeOffRequest request = new TimeOffRequest(
                UUID.randomUUID(),
                annualLeaveCategory.id(),
                employeeId,
                DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                DateTimeUtils.getCurrentDateTimeInServerTimeZone()
        );

        var thrown = assertThrows(TimeOffRequestException.class, () -> timeOffRequestService.addTimeOffRequest(request));
        assertEquals(String.format(
                "Cannot find employee with ID %s", employeeId), thrown.getMessage());
    }

    @Test
    public void testAddOverlappingTimeOffRequest() {
        var employeeId = employeeRepository.getAllEmployees().get(0).id();
        TimeOffRequest request1 = new TimeOffRequest(
                UUID.randomUUID(),
                annualLeaveCategory.id(),
                employeeId,
                DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                DateTimeUtils.getCurrentDateTimeInServerTimeZone()
        );

        TimeOffRequest request2 = new TimeOffRequest(
                UUID.randomUUID(),
                annualLeaveCategory.id(),
                employeeId,
                DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                DateTimeUtils.getCurrentDateTimeInServerTimeZone()
        );

        timeOffRequestService.addTimeOffRequest(request1);

        assertThrows(TimeOffRequestException.class, () -> timeOffRequestService.addTimeOffRequest(request2));
    }

    @Test
    public void testAddOverlappingTimeOffRequestWithWorkRemotely() {
        var employeeId = employeeRepository.getAllEmployees().get(0).id();
        TimeOffRequest request1 = new TimeOffRequest(
                UUID.randomUUID(),
                workRemotelyCategory.id(),
                employeeId,
                DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                DateTimeUtils.getCurrentDateTimeInServerTimeZone()
        );

        TimeOffRequest request2 = new TimeOffRequest(
                UUID.randomUUID(),
                annualLeaveCategory.id(),
                employeeId,
                DateTimeUtils.getCurrentDateTimeInServerTimeZone(),
                DateTimeUtils.getCurrentDateTimeInServerTimeZone()
        );

        timeOffRequestService.addTimeOffRequest(request1);
        timeOffRequestService.addTimeOffRequest(request2);

        assertEquals(2, timeOffRequestRepository.getTimeOffRequestsByEmployeeId(employeeId).size());
    }
}
