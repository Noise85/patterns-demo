package com.patterns.command.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Macro command that executes multiple commands as a single unit.
 * Supports undo of all commands in reverse order.
 */
public class MacroCommand implements Command {
    
    private final List<Command> commands;
    private final String description;
    
    /**
     * Creates a macro command.
     *
     * @param commands list of commands to execute
     * @param description description of the macro
     */
    public MacroCommand(List<Command> commands, String description) {
        this.commands = new ArrayList<>(commands);
        this.description = description;
    }
    
    @Override
    public void execute() {
        // TODO: Implement execute
        // Execute all commands in order
        // for each command: command.execute()
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public void undo() {
        // TODO: Implement undo
        // Undo all commands in REVERSE order
        // for i = commands.size() - 1 downto 0:
        //     commands.get(i).undo()
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        return description;
    }
}
