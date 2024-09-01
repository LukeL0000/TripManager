package ca.ubc.cs304.model;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
public class Activity {
    private final String activityName;

    public Activity(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityName() {
        return activityName;
    }
}
