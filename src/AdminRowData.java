// AdminRowData.java
// Data class for one row in the admin TableView

public class AdminRowData {

    private String name;
    private String room;
    private String username;
    private String units;
    private String bill;
    private String status;

    public AdminRowData(String name, String room, String username,
                        String units, String bill, String status) {
        this.name     = name;
        this.room     = room;
        this.username = username;
        this.units    = units;
        this.bill     = bill;
        this.status   = status;
    }

    public String getName()     { return name; }
    public String getRoom()     { return room; }
    public String getUsername() { return username; }
    public String getUnits()    { return units; }
    public String getBill()     { return bill; }
    public String getStatus()   { return status; }
}