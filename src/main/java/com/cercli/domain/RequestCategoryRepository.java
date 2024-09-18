package com.cercli.domain;

import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Interface representing a repository for managing RequestCategory entities.
 * This interface represents `what so-called as `port` within the context of
 * <strong>Hexagonal Architecture</strong>.
 */
public interface RequestCategoryRepository {

    /**
     * Adds a new request category to the repository.
     *
     * @param requestCategory the request category to add.
     */
    void addRequestCategory(RequestCategory requestCategory);

    /**
     * Retrieves a request category by its ID.
     *
     * @param id the ID of the request category.
     * @return the request category with the specified ID, or null if not found.
     */
    RequestCategory getRequestCategoryById(UUID id);
}
