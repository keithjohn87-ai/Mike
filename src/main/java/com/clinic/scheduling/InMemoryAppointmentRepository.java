package com.clinic.scheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Default in-memory store. Fine for tests, demos, and single-node use. Keyed by provider
 * so the common lookup (a provider's day) is cheap.
 */
public class InMemoryAppointmentRepository implements AppointmentRepository {

    private final Map<String, List<Appointment>> byProvider = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public String nextId() {
        return "appt-" + sequence.incrementAndGet();
    }

    @Override
    public void save(Appointment appointment) {
        byProvider
                .computeIfAbsent(appointment.providerId(), k -> new ArrayList<>())
                .add(appointment);
    }

    @Override
    public List<Appointment> findByProvider(String providerId) {
        List<Appointment> found = byProvider.get(providerId);
        if (found == null) {
            return new ArrayList<>();
        }
        // Hands back the repository's own list so callers see the provider's schedule.
        return found;
    }

    @Override
    public int count() {
        int total = 0;
        for (List<Appointment> list : byProvider.values()) {
            total += list.size();
        }
        return total;
    }
}
