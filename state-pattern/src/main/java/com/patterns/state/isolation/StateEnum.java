package com.patterns.state.isolation;

public enum StateEnum {
    DRAFT("Draft"),
    REVIEW("Review"),
    PUBLISHED("Published"),
    ARCHIVED("Archived");

    private final String displayName;

    StateEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
