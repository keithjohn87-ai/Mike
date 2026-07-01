package com.clinic.scheduling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Splits a provider's working window into back-to-back bookable slots.
 *
 * <p>Contract: every returned slot lies fully inside [windowStart, windowEnd]. A window
 * that does not divide evenly by the slot length simply yields fewer slots; it never
 * yields a slot that runs past the end of the window.
 */
public final class SlotCalculator {

    private SlotCalculator() {
    }

    /**
     * Split [windowStart, windowEnd) into consecutive slots of {@code slotMinutes} each.
     *
     * @return the slots, in order; empty if the window is too short for even one
     */
    public static List<TimeSlot> slots(LocalDateTime windowStart, LocalDateTime windowEnd,
                                       int slotMinutes) {
        if (slotMinutes <= 0) {
            throw new IllegalArgumentException("slotMinutes must be positive");
        }
        List<TimeSlot> slots = new ArrayList<>();
        LocalDateTime cursor = windowStart;
        while (!cursor.isAfter(windowEnd)) {
            slots.add(new TimeSlot(cursor, cursor.plusMinutes(slotMinutes)));
            cursor = cursor.plusMinutes(slotMinutes);
        }
        return slots;
    }
}
