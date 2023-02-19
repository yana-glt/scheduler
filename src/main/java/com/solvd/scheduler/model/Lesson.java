package com.solvd.scheduler.model;

import java.util.Objects;

public class Lesson implements Comparable<Lesson> {
    private final int lessonId;
    private final Subject subject;
    private final Group group;
    private Timeslot timeslot;

    public Lesson(int lessonId, Subject subject, Group group, Timeslot timeslot) {
        this.lessonId = lessonId;
        this.subject = subject;
        this.group = group;
        this.timeslot = timeslot;
    }

    public int getLessonId() {
        return lessonId;
    }

    public Subject getSubject() {
        return subject;
    }

    public Group getGroup() {
        return group;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson lesson)) return false;
        return lessonId == lesson.lessonId && subject.equals(lesson.subject) && group.equals(lesson.group)
                && Objects.equals(timeslot, lesson.timeslot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId, subject, group, timeslot);
    }

    @Override
    public String toString() {
        return String.format("lesson: id: %d, group: %s, %s, subject: %s",
                lessonId, group.getName(), timeslot, subject.getName());
    }

    @Override
    public int compareTo(Lesson o) {
        int i = group.getName().compareTo(o.getGroup().getName());
        if (i != 0) return i;

        i = timeslot.getDay().compareTo(o.getTimeslot().getDay());
        if (i != 0) return i;

        i = Integer.compare(timeslot.getSlot(), o.getTimeslot().getSlot());
        if (i != 0) return i;

        return subject.getName().compareTo(o.subject.getName());
    }
}