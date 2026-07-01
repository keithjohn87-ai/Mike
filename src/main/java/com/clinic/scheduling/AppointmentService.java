package com.clinic.scheduling;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Books and manages appointments, enforcing the scheduling rules.
 *
 * <p>Core invariant: a provider holds at most one appointment at any instant. Appointments
 * are half-open [start, end), so two that merely touch (one ends exactly as the next
 * begins) do NOT conflict and are both allowed.
 */
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    /**
     * Book a new appointment for a provider and patient.
     *
     * @throws SchedulingException if the window is invalid or the provider is already busy
     */
    public Appointment book(Provider provider, Patient patient,
                            LocalDateTime start, LocalDateTime end) {
        Objects.requireNonNull(provider, "provider");
        Objects.requireNonNull(patient, "patient");
        Objects.requireNonNull(start, "start");
        Objects.requireNonNull(end, "end");
        if (!end.isAfter(start)) {
            throw new SchedulingException("Appointment end must be after its start");
        }
        for (Appointment existing : repository.findByProvider(provider.id())) {
            if (conflicts(start, end, existing)) {
                throw new SchedulingException(
                        "Provider " + provider.name() + " is already booked from "
                                + existing.start() + " to " + existing.end());
            }
        }
        Appointment appt = new Appointment(
                repository.nextId(), provider.id(), patient.id(), start, end);
        repository.save(appt);
        return appt;
    }

    /**
     * Whether a proposed [start, end) window conflicts with an existing appointment for
     * the same provider.
     */
    private boolean conflicts(LocalDateTime start, LocalDateTime end, Appointment existing) {
        return !start.isAfter(existing.end()) && !end.isBefore(existing.start());
    }

    /** Every appointment on a provider's schedule. */
    public List<Appointment> scheduleFor(String providerId) {
        return repository.findByProvider(providerId);
    }
}
