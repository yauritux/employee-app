package com.cercli.port;

import com.cercli.domain.core.TimeOffRequest;

import java.util.List;
import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Interface representing a repository for managing <code>TimeOffRequest</code> entities.
 * This interface represents what so-called as `port` within the context of
 * <strong>Hexagonal Architecture</strong>.
 */
public interface TimeOffRequestRepository {

    /**
     * Adds a new time off request to the repository.
     *
     * @param timeOffRequest the time off request to add.
     */
    void addTimeOffRequest(TimeOffRequest timeOffRequest);

    /**
     * Retrieves all time-off requests for a specified employee.
     *
     * @param employeeId the ID of the employee.
     * @return a list of time-off requests for the specified employee.
     */
    List<TimeOffRequest> getTimeOffRequestsByEmployeeId(UUID employeeId);
}
