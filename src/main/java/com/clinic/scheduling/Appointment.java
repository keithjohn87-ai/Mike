package com.clinic.scheduling;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A booked window for a provider and a patient. Half-open in spirit: it occupies
 * [start, end), so an appointment ending at 10:00 does not conflict with one starting
 * at 10:00.
 */
public record Appointment(String id, String providerId, String patientId,
                          LocalDateTime start, LocalDateTime end) {

    public Appointment {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(providerId, "providerId");
        Objects.requireNonNull(patientId, "patientId");
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("end must be after start");
        }
    }

    /** Minutes this appointment occupies. */
    public long durationMinutes() {
        return java.time.Duration.between(start, end).toMinutes();
    }
}
