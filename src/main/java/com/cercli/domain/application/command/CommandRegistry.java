package com.cercli.domain.application.command;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public class CommandRegistry {

    private Map<String, Command> commands = new HashMap<>();

    public void registerCommand(String commandName, Command command) {
        commands.put(commandName.toLowerCase(), command);
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName.toLowerCase());
    }
}
