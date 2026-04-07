package com.patterns.builder.simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable SQL query with parameterized values.
 */
public final class SqlQuery {
    private final String sql;
    private final List<Object> parameters;

    public SqlQuery(String sql, List<Object> parameters) {
        this.sql = sql;
        this.parameters = List.copyOf(parameters);
    }

    public String getSql() {
        return sql;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "SqlQuery{sql='" + sql + "', parameters=" + parameters + '}';
    }
}
