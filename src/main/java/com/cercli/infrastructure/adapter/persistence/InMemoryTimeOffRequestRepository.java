package com.cercli.infrastructure.adapter.persistence;

import com.cercli.domain.core.TimeOffRequest;
import com.cercli.port.TimeOffRequestRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * In-memory implementation of the <code>TimeOffRequestRepository</code> interface.
 * This represents what so-called as an `adapter` within the context of
 * our <strong>Hexagonal Architecture</strong>, that implement the
 * <code>TimeOffRequestRepository</code> port interface.
 */
public class InMemoryTimeOffRequestRepository implements TimeOffRequestRepository {

    private final Map<UUID, TimeOffRequest> timeOffRequests = new HashMap<>();

    /**
     * Adds a new time-off request to the repository.
     *
     * @param timeOffRequest the time off request to add.
     */
    @Override
    public void addTimeOffRequest(TimeOffRequest timeOffRequest) {
        timeOffRequests.put(timeOffRequest.id(), timeOffRequest);
    }

    /**
     * Retrieves all time-off requests for a specific employee.
     *
     * @param employeeId the ID of the employee.
     * @return a list of time-off requests for the specified employee.
     */
    @Override
    public List<TimeOffRequest> getTimeOffRequestsByEmployeeId(UUID employeeId) {
        return timeOffRequests.values().stream()
                .filter(request -> request.employeeId().equals(employeeId))
                .collect(Collectors.toList());
    }
}
