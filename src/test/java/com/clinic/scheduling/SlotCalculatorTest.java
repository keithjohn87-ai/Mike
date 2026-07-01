package com.clinic.scheduling;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SlotCalculatorTest {

    @Test
    void rejectsNonPositiveSlotLength() {
        LocalDateTime start = LocalDateTime.of(2026, 7, 1, 9, 0);
        assertThrows(IllegalArgumentException.class,
                () -> SlotCalculator.slots(start, start.plusHours(1), 0));
    }

    @Test
    void firstSlotStartsAtTheWindowStart() {
        LocalDateTime start = LocalDateTime.of(2026, 7, 1, 9, 0);
        List<TimeSlot> slots = SlotCalculator.slots(start, start.plusHours(1), 30);
        assertTrue(slots.size() >= 1);
        assertEquals(start, slots.get(0).start());
    }
}
