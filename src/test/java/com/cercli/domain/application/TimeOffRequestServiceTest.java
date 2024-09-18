package com.cercli.domain.application;

import com.cercli.domain.core.RequestCategory;
import com.cercli.domain.core.TimeOffRequest;
import com.cercli.infrastructure.adapter.persistence.InMemoryRequestCategoryRepository;
import com.cercli.infrastructure.adapter.persistence.InMemoryTimeOffRequestRepository;
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
    private RequestCategoryRepository requestCategoryRepository;
    private TimeOffRequestService timeOffRequestService;

    private final RequestCategory workRemotelyCategory =
            new RequestCategory(UUID.randomUUID(), "Work Remotely");
    private final RequestCategory annualLeaveCategory =
            new RequestCategory(UUID.randomUUID(), "Annual Leave");

    @BeforeEach
    public void setUp() {
        timeOffRequestRepository = new InMemoryTimeOffRequestRepository();
        requestCategoryRepository = new InMemoryRequestCategoryRepository();
        timeOffRequestService = new TimeOffRequestService(timeOffRequestRepository, requestCategoryRepository);

        // Add request categories
        requestCategoryRepository.addRequestCategory(workRemotelyCategory);
        requestCategoryRepository.addRequestCategory(annualLeaveCategory);
    }

    @Test
    public void testAddTimeOffRequest() {
        UUID employeeId = UUID.randomUUID();
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
    public void testAddOverlappingTimeOffRequest() {
        UUID employeeId = UUID.randomUUID();
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
}
