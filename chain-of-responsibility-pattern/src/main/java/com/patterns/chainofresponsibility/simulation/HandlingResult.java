package com.patterns.chainofresponsibility.simulation;

/**
 * Result of handling a ticket.
 */
public record HandlingResult(
    boolean handled,
    String handlerName,
    String resolution,
    long processingTimeMillis
) {
}
