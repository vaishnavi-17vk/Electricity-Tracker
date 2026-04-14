// UsageLog.java
// Represents ONE log entry - when a student used an appliance
// Concepts: Class, Constructor, Encapsulation, toString method

public class UsageLog {

    // Which student logged this
    private String studentUsername;

    // Which appliance was used
    private String applianceName;

    // How many hours it was used
    private double hoursUsed;

    // How many units were consumed (calculated automatically)
    private double unitsConsumed;

    // Date of usage (stored as simple string like "01-Apr-2025")
    private String date;

    // Constructor - automatically calculates units from watts and hours
    public UsageLog(String studentUsername, String applianceName,
                    int watts, double hoursUsed, String date) {

        this.studentUsername = studentUsername;
        this.applianceName = applianceName;
        this.hoursUsed = hoursUsed;
        this.date = date;

        // Calculate units automatically when log is created
        // Formula: units = (watts x hours) / 1000
        this.unitsConsumed = (watts * hoursUsed) / 1000.0;
    }

    // Getters
    public String getStudentUsername() {
        return studentUsername;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public double getHoursUsed() {
        return hoursUsed;
    }

    public double getUnitsConsumed() {
        return unitsConsumed;
    }

    public String getDate() {
        return date;
    }

    // toString method - prints the log in a readable format
    // This is called automatically when you print a UsageLog object
    public String toString() {
        return date + " | " + applianceName +
               " | " + hoursUsed + " hrs" +
               " | " + unitsConsumed + " units";
    }
}
