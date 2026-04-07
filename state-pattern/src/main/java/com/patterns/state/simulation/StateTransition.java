package com.patterns.state.simulation;

import java.time.LocalDateTime;

/**
 * Record representing a state transition in order history.
 *
 * @param stateName name of the state
 * @param timestamp when the transition occurred
 */
public record StateTransition(String stateName, LocalDateTime timestamp) {
}
