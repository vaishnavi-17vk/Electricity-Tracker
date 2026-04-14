 // Admin.java
// This class EXTENDS User. It adds admin-specific fields.
// Concepts: Inheritance, super keyword

public class Admin extends User {

    // Extra field only Admin has
    private String adminName;

    // Constructor
    public Admin(String username, String password, String adminName) {

        // super() calls the User constructor with role = "admin"
        super(username, password, "admin");

        this.adminName = adminName;
    }

    // Getter for adminName
    public String getAdminName() {
        return adminName;
    }

    // Setter for adminName
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    // Override showInfo to print admin details
    public void showInfo() {
        super.showInfo();
        System.out.println("Admin Name: " + adminName);
    }
}
