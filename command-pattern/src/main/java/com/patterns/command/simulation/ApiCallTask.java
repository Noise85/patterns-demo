package com.patterns.command.simulation;

/**
 * Task for making external API calls.
 */
public class ApiCallTask extends BaseTask {
    
    private final String endpoint;
    private final String method;
    
    /**
     * Creates an API call task.
     *
     * @param taskId unique task ID
     * @param endpoint API endpoint
     * @param method HTTP method (GET, POST, PUT, DELETE)
     */
    public ApiCallTask(String taskId, String endpoint, String method) {
        super(taskId, TaskPriority.NORMAL, isIdempotentMethod(method), 3);
        this.endpoint = endpoint;
        this.method = method;
    }
    
    /**
     * Determines if HTTP method is idempotent.
     * GET, PUT, DELETE are idempotent.
     * POST is not idempotent (creates new resources).
     *
     * @param method HTTP method
     * @return true if method is idempotent
     */
    private static boolean isIdempotentMethod(String method) {
        return "GET".equalsIgnoreCase(method) || 
               "PUT".equalsIgnoreCase(method) || 
               "DELETE".equalsIgnoreCase(method);
    }
    
    @Override
    protected String doExecute() throws Exception {
        // TODO: Implement API call simulation
        // Simulate making API call
        // Return message like: "[method] request to [endpoint] completed"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        return String.format("%s %s", method, endpoint);
    }
    
    public String getEndpoint() {
        return endpoint;
    }
    
    public String getMethod() {
        return method;
    }
}
