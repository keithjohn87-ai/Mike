package com.clinic.scheduling;

import java.util.List;

/**
 * Storage seam for appointments. Swap the in-memory default for a JDBC or JPA backed
 * implementation without touching the scheduling rules.
 */
public interface AppointmentRepository {

    /** A fresh, unique appointment id. */
    String nextId();

    /** Persist an appointment. */
    void save(Appointment appointment);

    /** Every appointment for a provider, in no guaranteed order. Never null. */
    List<Appointment> findByProvider(String providerId);

    /** Total appointments stored. */
    int count();
}
