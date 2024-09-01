package ca.ubc.cs304.model;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

public class Location {
    private final String locationName;
    private final double latitude;
    private final double longitude;

    public Location(String locationName, double latitude, double longitude) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
