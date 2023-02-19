package com.solvd.scheduler.model;

import java.util.Objects;

public class Timeslot implements Comparable<Timeslot> {
    private final long id;
    private final Weekday day;
    private final int slot;

    public Timeslot(long id, Weekday day, int slot) {
        this.id = id;
        this.day = day;
        this.slot = slot;
    }

    public long getId() {
        return id;
    }

    public Weekday getDay() {
        return day;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timeslot timeslot)) return false;
        return slot == timeslot.slot && day == timeslot.day;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, day, slot);
    }

    @Override
    public String toString() {
        return String.format("timeslot: %s %dH", getDay(), slot);
    }

    @Override
    public int compareTo(Timeslot o) {
        int i = day.compareTo(o.getDay());
        if (i != 0) return i;
        return Integer.compare(slot, o.getSlot());
    }
}