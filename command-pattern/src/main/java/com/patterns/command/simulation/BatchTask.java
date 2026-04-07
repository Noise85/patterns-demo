package com.patterns.command.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Batch task that executes multiple tasks as a single unit.
 * Implements the Composite pattern combined with Command pattern.
 */
public class BatchTask extends BaseTask {
    
    private final List<Task> tasks;
    private final boolean stopOnFailure;
    
    /**
     * Creates a batch task.
     *
     * @param taskId unique batch task ID
     * @param tasks list of tasks to execute
     * @param stopOnFailure if true, stop batch on first task failure
     */
    public BatchTask(String taskId, List<Task> tasks, boolean stopOnFailure) {
        super(taskId, TaskPriority.NORMAL, false, 0);
        this.tasks = new ArrayList<>(tasks);
        this.stopOnFailure = stopOnFailure;
    }
    
    @Override
    protected String doExecute() throws Exception {
        // TODO: Implement batch execution
        // 1. Execute each task in order
        // 2. If stopOnFailure is true and any task fails: stop and throw exception
        // 3. Collect results from all executed tasks
        // 4. Return summary message like: "Batch: [x] of [y] tasks succeeded"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        return String.format("Batch of %d tasks", tasks.size());
    }
    
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
    
    public boolean isStopOnFailure() {
        return stopOnFailure;
    }
}
