package com.patterns.proxy.simulation;

import java.time.LocalDateTime;

/**
 * Audit log entry for document operations.
 */
public record AuditEntry(
    LocalDateTime timestamp,
    String username,
    String operation,
    boolean successful,
    String details
) {
}
