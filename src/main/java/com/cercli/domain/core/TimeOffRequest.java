package com.cercli.domain.core;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Represents a time-off request.
 */
public record TimeOffRequest(
        UUID id,
        UUID requestCategoryId,
        UUID employeeId,
        ZonedDateTime startDate,
        ZonedDateTime endDate
) {}
