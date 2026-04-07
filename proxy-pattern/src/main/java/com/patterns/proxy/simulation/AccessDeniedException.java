package com.patterns.proxy.simulation;

/**
 * Exception thrown when a user attempts an unauthorized operation.
 */
public class AccessDeniedException extends RuntimeException {
    
    private final String username;
    private final String operation;
    private final Role userRole;
    private final Permission required;
    
    /**
     * Creates an access denied exception.
     *
     * @param username the username
     * @param operation the operation attempted
     * @param userRole the user's role
     * @param required the required permission
     */
    public AccessDeniedException(String username, String operation, Role userRole, Permission required) {
        super(formatMessage(username, operation, userRole, required));
        this.username = username;
        this.operation = operation;
        this.userRole = userRole;
        this.required = required;
    }
    
    private static String formatMessage(String username, String operation, Role userRole, Permission required) {
        // TODO: Implement message formatting
        // Return: "User '{username}' with role {userRole} cannot perform {operation} (requires {required})"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public Role getUserRole() {
        return userRole;
    }
    
    public Permission getRequired() {
        return required;
    }
}
