package com.patterns.proxy.isolation;

import java.util.List;

/**
 * Subject interface for database connections.
 * Implemented by both real connection and proxy.
 */
public interface DatabaseConnection {
    
    /**
     * Executes a SQL query.
     *
     * @param sql the SQL query
     * @return query result
     */
    String executeQuery(String sql);
    
    /**
     * Executes multiple SQL queries in batch.
     *
     * @param queries the list of SQL queries
     * @return list of results (one per query)
     */
    List<String> executeBatch(List<String> queries);
    
    /**
     * Closes the database connection.
     */
    void close();
    
    /**
     * Checks if the connection is established.
     *
     * @return true if connected
     */
    boolean isConnected();
}
