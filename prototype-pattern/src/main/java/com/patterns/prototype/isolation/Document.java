package com.patterns.prototype.isolation;

import java.util.ArrayList;
import java.util.List;

/**
 * Document that can be cloned with all its sections.
 */
public class Document {
    private final String title;
    private final List<Section> sections;

    public Document(String title, List<Section> sections) {
        this.title = title;
        this.sections = new ArrayList<>(sections); // Defensive copy
    }

    public String getTitle() {
        return title;
    }

    public List<Section> getSections() {
        return new ArrayList<>(sections); // Defensive copy on return
    }

    /**
     * Creates a deep copy of this document.
     * All sections are cloned independently.
     *
     * @return Independent copy of this document
     */
    public Document clone() {
        // TODO: Implement deep copy of document
        // Remember to clone each section in the list, not just copy the list
        // Hint: Use Stream API or manual iteration to clone sections
        throw new UnsupportedOperationException("TODO: Implement Document.clone()");
    }
}
