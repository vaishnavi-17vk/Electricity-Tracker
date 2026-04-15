// AdminRowData.java
// Data class for one row in the admin TableView

public class AdminRowData {

    // Fields (must match TableColumn names)
    private String name;
    private String room;
    private String username;
    private String units;
    private String bill;
    private String status;

    // Constructor
    public AdminRowData(String name, String room, String username,
                        String units, String bill, String status) {
        this.name = name;
        this.room = room;
        this.username = username;
        this.units = units;
        this.bill = bill;
        this.status = status;
    }

    // Getters (VERY IMPORTANT for TableView)
    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    public String getUsername() {
        return username;
    }

    public String getUnits() {
        return units;
    }

    public String getBill() {
        return bill;
    }

    public String getStatus() {
        return status;
    }

    // Optional Setters (use if you want editable table)
    public void setName(String name) {
        this.name = name;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}