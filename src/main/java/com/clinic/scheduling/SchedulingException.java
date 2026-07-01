package com.clinic.scheduling;

/**
 * Thrown when a booking would break a scheduling rule (invalid window, or a provider
 * who is already busy for the requested time).
 */
public class SchedulingException extends RuntimeException {

    public SchedulingException(String message) {
        super(message);
    }
}
