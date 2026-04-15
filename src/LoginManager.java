// LoginManager.java
// Handles login validation for both Student and Admin
// Concepts: HashMap, ArrayList, if-else, for loop

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class LoginManager {

    private ArrayList<Student> studentList;
    private ArrayList<Admin> adminList;

    public LoginManager() {

        studentList = new ArrayList<>();
        adminList = new ArrayList<>();

        // default users
        studentList.add(new Student("rahul123", "pass123", "Rahul Sharma", "Room 204", 101));
        studentList.add(new Student("priya456", "pass456", "Priya Patel", "Room 101", 102));
        studentList.add(new Student("amit789", "pass789", "Amit Kumar", "Room 305", 103));

        adminList.add(new Admin("warden01", "admin999", "Mr. Patil"));
    }

    // -------------------------------------------------------
    // LOGIN (FILE BASED ONLY)
    // -------------------------------------------------------
    public String login(String username, String password) {

        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts[0].equals(username) && parts[1].equals(password)) {
                    br.close();
                    return parts[2]; // role
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "invalid";
    }

    // -------------------------------------------------------
    // GET STUDENT OBJECT
    // -------------------------------------------------------
    public Student getStudentByUsername(String username) {

        // FIRST check file-based users
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts[0].equals(username) && parts[2].equals("student")) {
                    br.close();
                    return new Student(username, parts[1], username, "Unknown", 0);
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // fallback (default students)
        for (Student s : studentList) {
            if (s.getUsername().equals(username)) {
                return s;
            }
        }

        return null;
    }

    // -------------------------------------------------------
    // GET ADMIN OBJECT
    // -------------------------------------------------------
    public Admin getAdminByUsername(String username) {

        for (Admin a : adminList) {
            if (a.getUsername().equals(username)) {
                return a;
            }
        }

        return null;
    }

    public ArrayList<Student> getAllStudents() {
        return studentList;
    }
}