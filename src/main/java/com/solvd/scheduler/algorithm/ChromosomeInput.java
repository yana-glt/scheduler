package com.solvd.scheduler.algorithm;

import com.solvd.scheduler.model.Group;
import com.solvd.scheduler.model.Timeslot;
import com.solvd.scheduler.model.Weekday;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChromosomeInput {
    private final List<Group> groups;
    private final List<Timeslot> timeslots;
    public static final int SCHOOL_WORKING_HOURS = 8;
    public static final int MAX_SCHOOL_WORKING_DAYS = 5;
    public static int numSchoolDays;
    public static int minLessons;
    public static int maxLessons;
    private final static Logger LOGGER = LogManager.getLogger(ChromosomeInput.class);

    public ChromosomeInput(List<Group> groupList, int numSchoolDays, int minLessons, int maxLessons) {
        this.groups = groupList;
        this.timeslots = generateTimeSlots(numSchoolDays);
        ChromosomeInput.numSchoolDays = numSchoolDays;
        ChromosomeInput.minLessons = minLessons;
        ChromosomeInput.maxLessons = maxLessons;
        LOGGER.info(String.format("Collected chromosome info for %d groups with %d random timeslots [%d working days," +
                        " min lessons per day: %d, max lessons per day: %d]",
                groupList.size(), timeslots.size(), numSchoolDays, minLessons, maxLessons));
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Timeslot getRandomTimeslot() {
        return timeslots.get((int) (timeslots.size() * Math.random()));
    }

    private List<Timeslot> generateTimeSlots(int numberOfDays) {
        Weekday[] days = Arrays.copyOfRange(Weekday.values(), 0, numberOfDays);
        int id = 1;
        List<Timeslot> slots = new ArrayList<>();
        for (Weekday day : days) {
            for (int k = 1; k < SCHOOL_WORKING_HOURS + 1; k++) {
                slots.add(new Timeslot(id, day, k));
                id++;
            }
        }
        LOGGER.debug(String.format("Generated list of %d timeslots [for %d working days, number of school working hours: %d]",
                slots.size(), numberOfDays, SCHOOL_WORKING_HOURS));
        return slots;
    }
}
