package com.clinic.scheduling;

import java.time.LocalDateTime;

/**
 * Small facade over the scheduling pieces: hold a repository, book through one door.
 */
public final class Clinic {

    private final AppointmentService appointments;

    public Clinic(AppointmentRepository repository) {
        this.appointments = new AppointmentService(repository);
    }

    /** A ready-to-use clinic backed by the in-memory store. */
    public static Clinic inMemory() {
        return new Clinic(new InMemoryAppointmentRepository());
    }

    public Appointment book(Provider provider, Patient patient,
                            LocalDateTime start, LocalDateTime end) {
        return appointments.book(provider, patient, start, end);
    }

    public AppointmentService appointments() {
        return appointments;
    }
}
