// Appliance.java
// Represents one electrical appliance like Fan, Laptop, etc.
// Concepts: Class, Constructor, Encapsulation

public class Appliance {

    // Name of the appliance (e.g. "Fan")
    private String applianceName;

    // Power consumption in Watts (e.g. 75 for a fan)
    private int watts;

    // Constructor
    public Appliance(String applianceName, int watts) {
        this.applianceName = applianceName;
        this.watts = watts;
    }

    // Getter for applianceName
    public String getApplianceName() {
        return applianceName;
    }

    // Setter for applianceName
    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    // Getter for watts
    public int getWatts() {
        return watts;
    }

    // Setter for watts
    public void setWatts(int watts) {
        this.watts = watts;
    }

    // Calculate units consumed by this appliance
    // Formula: units = (watts x hours) / 1000
    public double calculateUnits(double hoursUsed) {
        double units = (watts * hoursUsed) / 1000.0;
        return units;
    }

    // Print appliance info
    public void showInfo() {
        System.out.println("Appliance: " + applianceName + " | Watts: " + watts);
    }
}