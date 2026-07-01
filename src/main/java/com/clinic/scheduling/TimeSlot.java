package com.clinic.scheduling;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * An open window a patient could book. Half-open: [start, end).
 */
public record TimeSlot(LocalDateTime start, LocalDateTime end) {

    public TimeSlot {
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("slot end must be after start");
        }
    }

    public boolean contains(LocalDateTime instant) {
        return !instant.isBefore(start) && instant.isBefore(end);
    }
}
