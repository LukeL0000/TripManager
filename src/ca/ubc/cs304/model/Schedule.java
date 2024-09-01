package ca.ubc.cs304.model;

import java.sql.Timestamp;

public class Schedule {

    private final Timestamp startTime;
    private final Timestamp endTime;

    public Schedule(Timestamp startTime, Timestamp endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }
}
