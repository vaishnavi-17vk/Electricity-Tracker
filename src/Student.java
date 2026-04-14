// Student.java
// This class EXTENDS User. It adds student-specific fields.
// Concepts: Inheritance, super keyword, Constructor chaining

public class Student extends User {

    // Extra fields that only a Student has
    private String studentName;
    private String roomNumber;
    private int rollNumber;

    // Constructor - notice we call super() to set username, password, role
    public Student(String username, String password,
                   String studentName, String roomNumber, int rollNumber) {

        // super() calls the parent class (User) constructor
        super(username, password, "student");

        // Now set student-specific fields
        this.studentName = studentName;
        this.roomNumber = roomNumber;
        this.rollNumber = rollNumber;
    }

    // Getter for studentName
    public String getStudentName() {
        return studentName;
    }

    // Setter for studentName
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Getter for roomNumber
    public String getRoomNumber() {
        return roomNumber;
    }

    // Setter for roomNumber
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    // Getter for rollNumber
    public int getRollNumber() {
        return rollNumber;
    }

    // Setter for rollNumber
    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    // Override showInfo to also print student details
    // Concepts: Method Overriding (Polymorphism)
    public void showInfo() {
        // Call parent's showInfo first
        super.showInfo();
        System.out.println("Student Name: " + studentName);
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Roll Number: " + rollNumber);
    }
}