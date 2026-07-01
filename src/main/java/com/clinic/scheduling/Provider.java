package com.clinic.scheduling;

import java.util.Objects;

/**
 * A provider (clinician) who sees patients. Identified by a stable id.
 */
public record Provider(String id, String name) {

    public Provider {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(name, "name");
        if (id.isBlank()) {
            throw new IllegalArgumentException("provider id must not be blank");
        }
    }
}
