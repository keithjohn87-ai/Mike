package com.clinic.scheduling;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppointmentServiceTest {

    private static final Provider PROVIDER = new Provider("prov-1", "Dr. Reyes");
    private static final Patient PATIENT = new Patient("pat-1", "A. Client");

    private AppointmentService newService() {
        return new AppointmentService(new InMemoryAppointmentRepository());
    }

    @Test
    void booksAValidAppointment() {
        AppointmentService svc = newService();
        LocalDateTime start = LocalDateTime.of(2026, 7, 1, 9, 0);
        Appointment a = svc.book(PROVIDER, PATIENT, start, start.plusHours(1));
        assertNotNull(a.id());
        assertEquals(1, svc.scheduleFor(PROVIDER.id()).size());
    }

    @Test
    void rejectsEndBeforeStart() {
        AppointmentService svc = newService();
        LocalDateTime start = LocalDateTime.of(2026, 7, 1, 9, 0);
        assertThrows(SchedulingException.class,
                () -> svc.book(PROVIDER, PATIENT, start, start.minusHours(1)));
    }

    @Test
    void rejectsAnOverlappingAppointmentForTheSameProvider() {
        AppointmentService svc = newService();
        LocalDateTime start = LocalDateTime.of(2026, 7, 1, 9, 0);
        svc.book(PROVIDER, PATIENT, start, start.plusHours(1));           // 9:00 - 10:00
        assertThrows(SchedulingException.class,
                () -> svc.book(PROVIDER, PATIENT, start.plusMinutes(30),  // 9:30 - 10:30
                        start.plusMinutes(90)));
    }

    @Test
    void allowsTwoProvidersAtTheSameTime() {
        AppointmentService svc = newService();
        Provider other = new Provider("prov-2", "Dr. Okafor");
        LocalDateTime start = LocalDateTime.of(2026, 7, 1, 9, 0);
        svc.book(PROVIDER, PATIENT, start, start.plusHours(1));
        Appointment a = svc.book(other, PATIENT, start, start.plusHours(1));
        assertNotNull(a.id());
    }
}
