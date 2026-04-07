package com.patterns.builder.simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Builder for constructing SQL SELECT queries with validation and parameterization.
 */
public class SqlQueryBuilder {
    private List<String> columns = new ArrayList<>();
    private String table;
    private List<String> whereClauses = new ArrayList<>();
    private List<Object> whereParams = new ArrayList<>();
    private List<String> joins = new ArrayList<>();
    private List<String> orderByColumns = new ArrayList<>();
    private Integer limitValue;

    public SqlQueryBuilder select(String... columns) {
        // TODO: Add columns to select (store them for later)
        // TODO: Return this for method chaining
        throw new UnsupportedOperationException("TODO: Implement select()");
    }

    public SqlQueryBuilder from(String table) {
        // TODO: Set the table name
        // TODO: Return this for method chaining
        throw new UnsupportedOperationException("TODO: Implement from()");
    }

    public SqlQueryBuilder where(String condition, Object... params) {
        // TODO: Add WHERE clause condition
        // TODO: Add parameters for this condition
        // TODO: Return this for method chaining
        // Hint: Multiple where() calls should be combined with AND
        throw new UnsupportedOperationException("TODO: Implement where()");
    }

    public SqlQueryBuilder join(String table, String condition) {
        // TODO: Add JOIN clause
        // TODO: Return this for method chaining
        throw new UnsupportedOperationException("TODO: Implement join()");
    }

    public SqlQueryBuilder orderBy(String... columns) {
        // TODO: Add columns for ORDER BY
        // TODO: Return this for method chaining
        throw new UnsupportedOperationException("TODO: Implement orderBy()");
    }

    public SqlQueryBuilder limit(int limit) {
        // TODO: Set the LIMIT value
        // TODO: Validate limit is positive
        // TODO: Return this for method chaining
        throw new UnsupportedOperationException("TODO: Implement limit()");
    }

    public SqlQuery build() {
        // TODO: Validate required parts (SELECT and FROM must be present)
        // TODO: Build SQL string with proper structure:
        //       SELECT columns FROM table [JOIN ...] [WHERE ...] [ORDER BY ...] [LIMIT ...]
        // TODO: Use ? placeholders for parameters
        // TODO: Return SqlQuery with assembled SQL and parameters
        
        // Hints:
        // - Use StringBuilder for SQL assembly
        // - Join columns with ", "
        // - Join WHERE clauses with " AND "
        // - Parameters should be in the same order as placeholders
        
        throw new UnsupportedOperationException("TODO: Implement build() with SQL assembly and validation");
    }
}
