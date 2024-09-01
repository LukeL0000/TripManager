package ca.ubc.cs304.model;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

/**
 * The intent for this class is to update/store information about a single Trip.
 */
public class Trip {
    private String tripName;
    private final String tripID;
    private final String organizerID;
    private String locationName;
    private double latitude;
    private double longitude;

    public Trip(String tripName, String tripID, String id, String locationName, double latitude, double longitude) {
        this.tripName = tripName;
        this.tripID = tripID;
        this.organizerID = id;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTripName() {
        return tripName;
    }

    public String getTripID() {
        return tripID;
    }

    public String getOrganizerID() {
        return organizerID;
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
