package ca.ubc.cs304.model;

public class Equipment {
    public final String supplyEquipmentID;
    public final String transportEquipmentID;
    public final String type;
    public final String model;
    public double cost;

    public Equipment(String supplyEquipmentID, String transportEquipmentID, String type, String model, double cost) {
        this.supplyEquipmentID = supplyEquipmentID;
        this.transportEquipmentID = transportEquipmentID;
        this.type = type;
        this.model = model;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public String getEquipmentID() {
        if (supplyEquipmentID.isEmpty()) {
            return transportEquipmentID;
        } else {
            return supplyEquipmentID;
        }
    }
}
