public class UsageRowData {

    private String date;
    private String appliance;
    private String hours;
    private String units;

    public UsageRowData(String date, String appliance, String hours, String units) {
        this.date = date;
        this.appliance = appliance;
        this.hours = hours;
        this.units = units;
    }

    public String getDate() { return date; }
    public String getAppliance() { return appliance; }
    public String getHours() { return hours; }
    public String getUnits() { return units; }
}