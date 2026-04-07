package com.patterns.command.simulation;

/**
 * Task for importing data from external sources.
 */
public class DataImportTask extends BaseTask {
    
    private final String source;
    private final int recordCount;
    
    /**
     * Creates a data import task.
     *
     * @param taskId unique task ID
     * @param source data source
     * @param recordCount number of records to import
     */
    public DataImportTask(String taskId, String source, int recordCount) {
        super(taskId, TaskPriority.NORMAL, true, 3);
        this.source = source;
        this.recordCount = recordCount;
    }
    
    @Override
    protected String doExecute() throws Exception {
        // TODO: Implement data import simulation
        // Simulate importing data (e.g., sleep for a short time)
        // Return message like: "Imported [recordCount] records from [source]"
        // Optionally: throw exception randomly to simulate failures for testing
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        return String.format("Import %d records from %s", recordCount, source);
    }
    
    public String getSource() {
        return source;
    }
    
    public int getRecordCount() {
        return recordCount;
    }
}
