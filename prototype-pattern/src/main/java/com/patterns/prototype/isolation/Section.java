package com.patterns.prototype.isolation;

import java.util.HashMap;
import java.util.Map;

/**
 * Section of a document that can be cloned.
 * Contains heading, content, and metadata.
 */
public class Section {
    private final String heading;
    private final String content;
    private final Map<String, String> metadata;

    public Section(String heading, String content, Map<String, String> metadata) {
        this.heading = heading;
        this.content = content;
        this.metadata = new HashMap<>(metadata); // Defensive copy
    }

    public String getHeading() {
        return heading;
    }

    public String getContent() {
        return content;
    }

    public Map<String, String> getMetadata() {
        return new HashMap<>(metadata); // Defensive copy on return
    }

    /**
     * Creates a deep copy of this section.
     *
     * @return Independent copy of this section
     */
    public Section clone() {
        // TODO: Implement deep copy of section
        // Remember to deep copy the metadata map
        throw new UnsupportedOperationException("TODO: Implement Section.clone()");
    }
}
