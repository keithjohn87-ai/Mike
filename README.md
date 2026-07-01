# Clinic Scheduling

A small, focused Java library for the part of a practice platform that is easy to get
wrong: booking appointments without double-booking a provider, and computing the open
slots in a working day.

It is deliberately small and dependency-light so the scheduling rules are easy to read,
test, and reason about.

## Layout

```
src/main/java/com/clinic/scheduling/
  Provider.java                     a provider who sees patients
  Patient.java                      a patient being seen
  Appointment.java                  a booked window for a provider + patient
  TimeSlot.java                     an open window a patient could book
  SchedulingException.java          thrown when a booking breaks a rule
  AppointmentRepository.java        storage seam (interface)
  InMemoryAppointmentRepository.java default in-memory store
  AppointmentService.java           booking + double-booking rules
  SlotCalculator.java               splits a working window into open slots
  Clinic.java                       small facade tying it together
```

## Build and test

```
mvn test
```

Java 17, JUnit 5, no other runtime dependencies.

## Design notes

See [docs/DESIGN.md](docs/DESIGN.md) for the booking rules and the invariants the tests
lock in. The short version: a provider can hold at most one appointment at any instant,
appointments that merely touch (one ends exactly as the next begins) are allowed, and slot
generation never produces a slot that runs past the end of the working window.
