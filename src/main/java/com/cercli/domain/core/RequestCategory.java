package com.cercli.domain.core;

import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Represents a request category.
 */
public record RequestCategory(UUID id, String name) {
    @Override
    public String toString() {
        return String.format("RequestCategory{id=%s, name='%s'}", id, name);
    }
}
