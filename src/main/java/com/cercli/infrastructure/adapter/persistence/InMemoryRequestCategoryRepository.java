package com.cercli.infrastructure.adapter.persistence;

import com.cercli.domain.core.RequestCategory;
import com.cercli.port.RequestCategoryRepository;

import java.util.*;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * In-Memory implementation of the <code>RequestCategoryRepository</code> interface.
 * This represents what so-called as an `adapter` within the context of
 * our <strong>Hexagonal Architecture</strong>, that implement the
 * <code>RequestCategoryRepository</code> port interface.
 */
public class InMemoryRequestCategoryRepository implements RequestCategoryRepository {

    private final Map<UUID, RequestCategory> requestCategories = new HashMap<>();

    public InMemoryRequestCategoryRepository() {
        requestCategories.put(UUID.randomUUID(),
                new RequestCategory(UUID.randomUUID(), "Work Remotely"));
    }

    /**
     * Adds a new request category to the repository.
     *
     * @param requestCategory the request category to add.
     */
    @Override
    public void addRequestCategory(RequestCategory requestCategory) {
        requestCategories.put(requestCategory.id(), requestCategory);
    }

    /**
     * Retrieves a request category by its ID.
     *
     * @param id the ID of the request category.
     * @return the request category with the specified ID, or null if not found.
     */
    @Override
    public RequestCategory getRequestCategoryById(UUID id) {
        return requestCategories.get(id);
    }

    /**
     * Fetch all request category records.
     *
     * @return list of request categories.
     */
    @Override
    public List<RequestCategory> getAllRequestCategories() {
        return new ArrayList<>(requestCategories.values());
    }
}
