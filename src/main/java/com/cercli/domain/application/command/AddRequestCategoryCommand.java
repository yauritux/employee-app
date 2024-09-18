package com.cercli.domain.application.command;

import com.cercli.domain.core.RequestCategory;
import com.cercli.port.RequestCategoryRepository;

import java.util.Scanner;
import java.util.UUID;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public class AddRequestCategoryCommand implements Command {

    private final RequestCategoryRepository requestCategoryRepository;
    private final Scanner in;

    public AddRequestCategoryCommand(RequestCategoryRepository requestCategoryRepository, Scanner in) {
        this.requestCategoryRepository = requestCategoryRepository;
        this.in = in;
    }
    @Override
    public void execute(String[] args) {
        System.out.println("Enter the request category name: ");
        String name = in.nextLine();
        requestCategoryRepository.addRequestCategory(new RequestCategory(UUID.randomUUID(), name));
    }
}
