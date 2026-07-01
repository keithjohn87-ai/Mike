package com.clinic.scheduling;

import java.util.Objects;

/**
 * A patient being seen. Identified by a stable id.
 */
public record Patient(String id, String name) {

    public Patient {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(name, "name");
        if (id.isBlank()) {
            throw new IllegalArgumentException("patient id must not be blank");
        }
    }
}
