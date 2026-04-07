package com.patterns.builder.simulation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for Exercise 2: SQL Query Builder
 */
class SimulationExerciseTest {

    @Test
    void builder_createsSimpleSelectQuery() {
        SqlQuery query = new SqlQueryBuilder()
                .select("id", "name", "email")
                .from("users")
                .build();

        assertThat(query.getSql()).isEqualTo("SELECT id, name, email FROM users");
        assertThat(query.getParameters()).isEmpty();
    }

    @Test
    void builder_createsQueryWithSingleWhereClause() {
        SqlQuery query = new SqlQueryBuilder()
                .select("id", "name")
                .from("users")
                .where("age > ?", 18)
                .build();

        assertThat(query.getSql()).isEqualTo("SELECT id, name FROM users WHERE age > ?");
        assertThat(query.getParameters()).containsExactly(18);
    }

    @Test
    void builder_createsQueryWithMultipleWhereClauses() {
        SqlQuery query = new SqlQueryBuilder()
                .select("id", "name")
                .from("users")
                .where("age > ?", 18)
                .where("country = ?", "USA")
                .build();

        assertThat(query.getSql())
                .isEqualTo("SELECT id, name FROM users WHERE age > ? AND country = ?");
        assertThat(query.getParameters()).containsExactly(18, "USA");
    }

    @Test
    void builder_createsQueryWithJoin() {
        SqlQuery query = new SqlQueryBuilder()
                .select("u.id", "u.name", "o.order_id")
                .from("users u")
                .join("orders o", "u.id = o.user_id")
                .build();

        assertThat(query.getSql())
                .contains("SELECT u.id, u.name, o.order_id FROM users u")
                .contains("JOIN orders o ON u.id = o.user_id");
    }

    @Test
    void builder_createsQueryWithOrderBy() {
        SqlQuery query = new SqlQueryBuilder()
                .select("id", "name", "created_at")
                .from("users")
                .orderBy("created_at", "name")
                .build();

        assertThat(query.getSql())
                .isEqualTo("SELECT id, name, created_at FROM users ORDER BY created_at, name");
    }

    @Test
    void builder_createsQueryWithLimit() {
        SqlQuery query = new SqlQueryBuilder()
                .select("id", "name")
                .from("users")
                .limit(10)
                .build();

        assertThat(query.getSql()).isEqualTo("SELECT id, name FROM users LIMIT 10");
    }

    @Test
    void builder_createsComplexQuery() {
        SqlQuery query = new SqlQueryBuilder()
                .select("u.id", "u.name", "COUNT(o.id) as order_count")
                .from("users u")
                .join("orders o", "u.id = o.user_id")
                .where("u.status = ?", "active")
                .where("o.total > ?", 100.0)
                .orderBy("order_count")
                .limit(20)
                .build();

        assertThat(query.getSql())
                .contains("SELECT u.id, u.name, COUNT(o.id) as order_count")
                .contains("FROM users u")
                .contains("JOIN orders o ON u.id = o.user_id")
                .contains("WHERE u.status = ? AND o.total > ?")
                .contains("ORDER BY order_count")
                .contains("LIMIT 20");
        
        assertThat(query.getParameters()).containsExactly("active", 100.0);
    }

    @Test
    void builder_handlesWhereWithMultipleParameters() {
        SqlQuery query = new SqlQueryBuilder()
                .select("id")
                .from("events")
                .where("created_at BETWEEN ? AND ?", "2026-01-01", "2026-12-31")
                .build();

        assertThat(query.getSql())
                .contains("WHERE created_at BETWEEN ? AND ?");
        assertThat(query.getParameters()).containsExactly("2026-01-01", "2026-12-31");
    }

    @Test
    void builder_supportsMethodChaining() {
        SqlQueryBuilder builder = new SqlQueryBuilder();
        
        SqlQuery query = builder
                .select("id", "name")
                .from("products")
                .where("price < ?", 50)
                .orderBy("price")
                .limit(5)
                .build();

        assertThat(query).isNotNull();
        assertThat(query.getSql()).contains("SELECT").contains("FROM").contains("WHERE");
    }

    @Test
    void build_throwsException_whenSelectIsMissing() {
        SqlQueryBuilder builder = new SqlQueryBuilder()
                .from("users");

        assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("SELECT");
    }

    @Test
    void build_throwsException_whenFromIsMissing() {
        SqlQueryBuilder builder = new SqlQueryBuilder()
                .select("id", "name");

        assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("FROM");
    }

    @Test
    void build_throwsException_whenLimitIsNegative() {
        SqlQueryBuilder builder = new SqlQueryBuilder()
                .select("id")
                .from("users")
                .limit(-1);

        assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalStateException.class)
                .satisfies(e -> assertThat(e.getMessage().toLowerCase())
                        .containsAnyOf("limit", "positive"));
    }

    @Test
    void build_throwsException_whenLimitIsZero() {
        SqlQueryBuilder builder = new SqlQueryBuilder()
                .select("id")
                .from("users")
                .limit(0);

        assertThatThrownBy(builder::build)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void builder_createsQueryWithMultipleJoins() {
        SqlQuery query = new SqlQueryBuilder()
                .select("u.name", "o.order_id", "p.product_name")
                .from("users u")
                .join("orders o", "u.id = o.user_id")
                .join("order_items oi", "o.id = oi.order_id")
                .join("products p", "oi.product_id = p.id")
                .build();

        String sql = query.getSql();
        assertThat(sql).contains("JOIN orders o ON u.id = o.user_id");
        assertThat(sql).contains("JOIN order_items oi ON o.id = oi.order_id");
        assertThat(sql).contains("JOIN products p ON oi.product_id = p.id");
    }

    @Test
    void sqlQuery_isImmutable() {
        SqlQuery query = new SqlQueryBuilder()
                .select("id")
                .from("users")
                .where("status = ?", "active")
                .build();

        // Attempting to modify parameters should not affect the query
        var params = query.getParameters();
        
        assertThatThrownBy(() -> params.add("hacker"))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
