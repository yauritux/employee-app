package com.cercli.domain.application.command;

import com.cercli.domain.core.RequestCategory;
import com.cercli.port.RequestCategoryRepository;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public class ListRequestCategoryCommand implements Command {

    private final RequestCategoryRepository requestCategoryRepository;

    public ListRequestCategoryCommand(RequestCategoryRepository requestCategoryRepository) {
        this.requestCategoryRepository = requestCategoryRepository;
    }

    @Override
    public void execute(String[] args) {
        for (RequestCategory requestCategory : requestCategoryRepository.getAllRequestCategories()) {
            System.out.println(requestCategory);
        }
    }
}
