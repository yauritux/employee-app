package com.cercli.domain.application.service;

import com.cercli.domain.core.RequestCategory;
import com.cercli.domain.core.TimeOffRequest;
import com.cercli.port.EmployeeRepository;
import com.cercli.port.RequestCategoryRepository;
import com.cercli.port.TimeOffRequestRepository;
import com.cercli.shared.exception.EmployeeNotFoundException;
import com.cercli.shared.exception.TimeOffRequestException;

import java.util.List;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Service class for managing time-off requests.
 * This service class represents what so-called as `application-domain` (a.k.a. `use-cases`)
 * within the context of <strong>Hexagonal Architecture</strong>.
 */
public class TimeOffRequestService {

    private final TimeOffRequestRepository timeOffRequestRepository;
    private final RequestCategoryRepository requestCategoryRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * Constructs a TimeOffRequestService with the specified repositories.
     *
     * @param timeoffRequestRepository the repository to use for managing time-off requests.
     * @param requestCategoryRepository the repository to use for managing request categories.
     */
    public TimeOffRequestService(
            TimeOffRequestRepository timeoffRequestRepository,
            RequestCategoryRepository requestCategoryRepository,
            EmployeeRepository employeeRepository) {
        this.timeOffRequestRepository = timeoffRequestRepository;
        this.requestCategoryRepository = requestCategoryRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Adds a new time-off request.
     *
     * @param timeOffRequest the time-off request to add.
     */
    public void addTimeOffRequest(TimeOffRequest timeOffRequest) {
        var selectedRequestCategory = requestCategoryRepository.getRequestCategoryById(timeOffRequest.requestCategoryId());
        if (selectedRequestCategory == null) {
            throw new TimeOffRequestException(String.format("Failed to find request category with id %s",
                            timeOffRequest.requestCategoryId()));
        }
        try {
            employeeRepository.getEmployee(timeOffRequest.employeeId());
        } catch (EmployeeNotFoundException e) {
            throw new TimeOffRequestException(e.getMessage());
        }
        List<TimeOffRequest> existingRequests =
                timeOffRequestRepository.getTimeOffRequestsByEmployeeId(timeOffRequest.employeeId());
        for (TimeOffRequest existingRequest : existingRequests) {
            if (isOverlapping(existingRequest, timeOffRequest)) {
                RequestCategory existingCategory = requestCategoryRepository.getRequestCategoryById(
                        existingRequest.requestCategoryId());
                RequestCategory newCategory = requestCategoryRepository.getRequestCategoryById(
                        timeOffRequest.requestCategoryId());
                if (isNotWorkRemotelyCategory(existingCategory) && isNotWorkRemotelyCategory(newCategory)) {
                    throw new TimeOffRequestException("Time off request overlaps with an existing request.");
                }
            }
        }

        timeOffRequestRepository.addTimeOffRequest(timeOffRequest);
    }

    /**
     * Checks if two time-off requests overlap.
     *
     * @param request1 the first time-off request.
     * @param request2 the second time-off request.
     * @return true if the requests overlap, false otherwise.
     */
    private boolean isOverlapping(TimeOffRequest request1, TimeOffRequest request2) {
        return request1.startDate().isBefore(request2.endDate().plusDays(1)) &&
                request1.endDate().isAfter(request2.startDate().minusDays(1));
    }

    /**
     * Checks if a request category is the "Work Remotely" category.
     *
     * @param category the request category to check.
     * @return true if the category is "Work Remotely", false otherwise.
     */
    private boolean isNotWorkRemotelyCategory(RequestCategory category) {
        return !("Work Remotely".equalsIgnoreCase(category.name()));
    }
}
