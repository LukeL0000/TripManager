package ca.ubc.cs304.model;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

import javax.xml.crypto.Data;

/**
 * The intent for this class is to update/store information about a single branch
 */
public class People {
    private String name;
    private final String userID;

    public People(String name, String userID) {
        this.name = name;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public String getUserID() {
        return userID;
    }
}
