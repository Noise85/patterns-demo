package com.patterns.chainofresponsibility.isolation;

/**
 * Factory for building logger chains.
 */
public class LoggerChain {
    
    /**
     * Builds the default logging chain.
     * Chain: ConsoleLogger → FileLogger → EmailLogger → AlertLogger
     *
     * @return the head of the chain
     */
    public static LogHandler buildDefaultChain() {
        // TODO: Implement chain building
        // Create instances of each logger
        // Wire them together with setNext()
        // Return the first handler (ConsoleLogger)
        // Use fluent chaining:
        // return new ConsoleLogger()
        //     .setNext(new FileLogger("app.log"))
        //     .setNext(new EmailLogger("admin@company.com"))
        //     .setNext(new AlertLogger("PagerDuty"));
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
