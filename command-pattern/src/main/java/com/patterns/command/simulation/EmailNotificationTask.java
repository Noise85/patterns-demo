package com.patterns.command.simulation;

/**
 * Task for sending email notifications.
 * NOT idempotent - should not retry automatically (don't send duplicate emails).
 */
public class EmailNotificationTask extends BaseTask {
    
    private final String recipient;
    private final String subject;
    
    /**
     * Creates an email notification task.
     *
     * @param taskId unique task ID
     * @param recipient email recipient
     * @param subject email subject
     */
    public EmailNotificationTask(String taskId, String recipient, String subject) {
        super(taskId, TaskPriority.HIGH, false, 1);  // NOT idempotent
        this.recipient = recipient;
        this.subject = subject;
    }
    
    @Override
    protected String doExecute() throws Exception {
        // TODO: Implement email sending simulation
        // Simulate sending email
        // Return message like: "Sent email to [recipient] with subject '[subject]'"
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    public String getDescription() {
        return String.format("Send email to %s: %s", recipient, subject);
    }
    
    public String getRecipient() {
        return recipient;
    }
    
    public String getSubject() {
        return subject;
    }
}
