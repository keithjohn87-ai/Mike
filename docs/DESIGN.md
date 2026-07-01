# Design notes

Small on purpose. The value is in getting a few rules exactly right, so the rules are
spelled out here and the tests are meant to lock them in.

## Model

- **Provider** and **Patient** are records identified by a stable id.
- **Appointment** is a `[start, end)` half-open window owned by one provider and one
  patient. Half-open matters: an appointment ending at 10:00 leaves 10:00 free.
- **TimeSlot** is an open `[start, end)` window a patient could book.

## Booking rules (AppointmentService)

1. `end` must be strictly after `start`.
2. A provider may hold at most one appointment at any instant.
3. Because windows are half-open, two appointments that only touch (one ends exactly when
   the next begins) do not conflict. Back-to-back booking is allowed and common.

## Slot generation (SlotCalculator)

Split `[windowStart, windowEnd)` into consecutive slots of a fixed length. Every slot must
lie fully inside the window. A window that does not divide evenly yields fewer slots; it
must never yield a slot that runs past `windowEnd`.

## Storage

`AppointmentRepository` is the seam; the in-memory default is fine for a single node. A
provider's schedule returned from a lookup is a read-only view: iterating it must be safe
while the clinic keeps booking, and callers must not be able to change stored state
through it.
