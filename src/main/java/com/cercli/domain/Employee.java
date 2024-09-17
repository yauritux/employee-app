package com.cercli.domain;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public record Employee(
        UUID id,
        String name,
        String position,
        String email,
        float salary,
        ZonedDateTime createdAt,
        ZonedDateTime modifiedAt
) {
    @Override
    public String toString() {
        return String.format(
                "Employee{id=%s, name=%s, position=%s, email=%s, salary=%.2f, createdAt=%s, modifiedAt=%s}",
                id, name, position, email, salary, createdAt, modifiedAt
        );
    }
}