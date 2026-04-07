package com.patterns.singleton.simulation;

/**
 * Represents a database connection (simplified simulation).
 */
public class Connection {
    private final String id;
    private boolean valid;

    public Connection(String id) {
        this.id = id;
        this.valid = true;
    }

    public String getId() {
        return id;
    }

    public boolean isValid() {
        return valid;
    }

    /**
     * Closes/invalidates the connection.
     */
    public void close() {
        this.valid = false;
    }

    @Override
    public String toString() {
        return "Connection{id='" + id + "', valid=" + valid + '}';
    }
}
