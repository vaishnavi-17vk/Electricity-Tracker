// User.java
// This is the PARENT class. Student and Admin will extend this class.
// Concepts: Class, Encapsulation, Constructor, Getters and Setters

public class User {

    // Private fields - only accessible through getters/setters
    private String username;
    private String password;
    private String role; // either "student" or "admin"

    // Constructor - called when we create a new User object
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for role
    public String getRole() {
        return role;
    }

    // Setter for role
    public void setRole(String role) {
        this.role = role;
    }

    // This method prints basic user info to console
    public void showInfo() {
        System.out.println("Username: " + username);
        System.out.println("Role: " + role);
    }
}