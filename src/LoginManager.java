// LoginManager.java
// Handles login validation for both Student and Admin
// Concepts: HashMap, ArrayList, if-else, for loop

import java.util.HashMap;
import java.util.ArrayList;

public class LoginManager {

    // HashMap stores username as KEY and password as VALUE
    // Key   → "rahul123"
    // Value → "pass123"
    private HashMap<String, String> studentCredentials;
    private HashMap<String, String> adminCredentials;

    // We also keep the full Student objects so we can return them after login
    private ArrayList<Student> studentList;
    private ArrayList<Admin>   adminList;

    // Constructor — sets up default accounts
    public LoginManager() {

        // Initialize all four collections
        studentCredentials = new HashMap<String, String>();
        adminCredentials   = new HashMap<String, String>();
        studentList        = new ArrayList<Student>();
        adminList          = new ArrayList<Admin>();

        // Add some default students
        addStudent(new Student("rahul123", "pass123",
                               "Rahul Sharma", "Room 204", 101));
        addStudent(new Student("priya456", "pass456",
                               "Priya Patel", "Room 101", 102));
        addStudent(new Student("amit789", "pass789",
                               "Amit Kumar", "Room 305", 103));

        // Add default admin
        addAdmin(new Admin("warden01", "admin999", "Mr. Patil"));
    }

    // Add a new student — stores in both HashMap and ArrayList
    public void addStudent(Student student) {
        // Store credentials in HashMap for fast lookup
        studentCredentials.put(student.getUsername(), student.getPassword());
        // Store full student object in ArrayList
        studentList.add(student);
    }

    // Add a new admin
    public void addAdmin(Admin admin) {
        adminCredentials.put(admin.getUsername(), admin.getPassword());
        adminList.add(admin);
    }

    // -------------------------------------------------------
    // LOGIN METHOD
    // Returns "student", "admin", or "invalid"
    // -------------------------------------------------------
    public String login(String username, String password) {

        // Check if username exists in student credentials
        // HashMap.containsKey() checks if the key exists
        if (studentCredentials.containsKey(username)) {

            // Get the stored password for this username
            String storedPassword = studentCredentials.get(username);

            // Check if passwords match
            if (storedPassword.equals(password)) {
                return "student"; // login successful as student
            } else {
                return "invalid"; // wrong password
            }
        }

        // Check if username exists in admin credentials
        if (adminCredentials.containsKey(username)) {

            String storedPassword = adminCredentials.get(username);

            if (storedPassword.equals(password)) {
                return "admin"; // login successful as admin
            } else {
                return "invalid"; // wrong password
            }
        }

        // Username not found anywhere
        return "invalid";
    }

    // Get the full Student object by username (after login)
    public Student getStudentByUsername(String username) {

        // Loop through student list and find matching username
        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);
            if (s.getUsername().equals(username)) {
                return s; // found it — return this student
            }
        }
        return null; // not found
    }

    // Get the full Admin object by username (after login)
    public Admin getAdminByUsername(String username) {

        for (int i = 0; i < adminList.size(); i++) {
            Admin a = adminList.get(i);
            if (a.getUsername().equals(username)) {
                return a;
            }
        }
        return null;
    }

    // Get all students (used by admin to see everyone)
    public ArrayList<Student> getAllStudents() {
        return studentList;
    }
}