package ca.ubc.cs304.model;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

public class Conditions {
    private final String conditionID;
    private final String weather;
    private final String terrain;
    private final String rules;
    private final String hazards;

    public Conditions(String conditionID, String weather, String terrain, String rules, String hazards) {
        this.conditionID = conditionID;
        this.weather = weather;
        this.terrain = terrain;
        this.rules = rules;
        this.hazards = hazards;
    }

    public String getConditionID() {
        return conditionID;
    }

    public String getWeather() {
        return weather;
    }

    public String getTerrain() {
        return terrain;
    }

    public String getRules() {
        return rules;
    }

    public String getHazards() {
        return hazards;
    }
}
