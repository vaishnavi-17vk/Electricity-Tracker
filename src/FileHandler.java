// FileHandler.java
// This class handles saving and loading data from .txt files
// Concepts: File I/O, FileWriter, BufferedReader, Exception Handling

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

public class FileHandler {

    // File names where data is stored
    static final String USAGE_LOG_FILE = "usage_logs.txt";
    static final String STUDENTS_FILE  = "students.txt";

    // -------------------------------------------------------
    // SAVE one UsageLog entry to the file
    // Each log is saved as one line:
    // username,applianceName,watts,hoursUsed,date
    // -------------------------------------------------------
    public static void saveUsageLog(UsageLog log, int watts) {

        try {
            // true = append mode (don't overwrite, add to end of file)
            FileWriter fw = new FileWriter(USAGE_LOG_FILE, true);
            BufferedWriter bw = new BufferedWriter(fw);

            // Write one log as a comma-separated line
            String line = log.getStudentUsername() + "," +
                          log.getApplianceName()   + "," +
                          watts                    + "," +
                          log.getHoursUsed()       + "," +
                          log.getDate();

            bw.write(line);      // write the line
            bw.newLine();        // move to next line
            bw.close();          // always close the writer
            fw.close();

            System.out.println("Log saved to file successfully.");

        } catch (Exception e) {
            // If anything goes wrong with file saving
            System.out.println("Error saving log: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // LOAD all UsageLog entries from file for one student
    // Reads the file line by line, splits by comma, rebuilds objects
    // -------------------------------------------------------
    public static ArrayList<UsageLog> loadUsageLogs(String studentUsername) {

        // This list will hold all logs for the given student
        ArrayList<UsageLog> logList = new ArrayList<UsageLog>();

        try {
            // Check if file exists first — if not, return empty list
            File file = new File(USAGE_LOG_FILE);
            if (!file.exists()) {
                System.out.println("No log file found. Starting fresh.");
                return logList;
            }

            FileReader fr = new FileReader(USAGE_LOG_FILE);
            BufferedReader br = new BufferedReader(fr);

            String line = "";

            // Read line by line until end of file (null means end)
            while ((line = br.readLine()) != null) {

                // Skip empty lines
                if (line.trim().equals("")) {
                    continue;
                }

                // Split the line by comma into parts
                // parts[0] = username
                // parts[1] = applianceName
                // parts[2] = watts
                // parts[3] = hoursUsed
                // parts[4] = date
                String[] parts = line.split(",");

                // Only load logs that belong to this student
                if (parts[0].equals(studentUsername)) {

                    String username     = parts[0];
                    String applianceName = parts[1];
                    int    watts        = Integer.parseInt(parts[2]);
                    double hours        = Double.parseDouble(parts[3]);
                    String date         = parts[4];

                    // Create a UsageLog object from the data
                    UsageLog log = new UsageLog(username, applianceName,
                                               watts, hours, date);

                    // Add to our list
                    logList.add(log);
                }
            }

            br.close();
            fr.close();

        } catch (Exception e) {
            System.out.println("Error loading logs: " + e.getMessage());
        }

        return logList;
    }

    // -------------------------------------------------------
    // SAVE a Student to the students file
    // Format: username,password,studentName,roomNumber,rollNumber
    // -------------------------------------------------------
    public static void saveStudent(Student student) {

        try {
            FileWriter fw = new FileWriter(STUDENTS_FILE, true);
            BufferedWriter bw = new BufferedWriter(fw);

            String line = student.getUsername()    + "," +
                          student.getPassword()    + "," +
                          student.getStudentName() + "," +
                          student.getRoomNumber()  + "," +
                          student.getRollNumber();

            bw.write(line);
            bw.newLine();
            bw.close();
            fw.close();

            System.out.println("Student saved to file.");

        } catch (Exception e) {
            System.out.println("Error saving student: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // LOAD all Students from file into an ArrayList
    // -------------------------------------------------------
    public static ArrayList<Student> loadAllStudents() {

        ArrayList<Student> studentList = new ArrayList<Student>();

        try {
            File file = new File(STUDENTS_FILE);
            if (!file.exists()) {
                System.out.println("No students file found.");
                return studentList;
            }

            FileReader fr = new FileReader(STUDENTS_FILE);
            BufferedReader br = new BufferedReader(fr);

            String line = "";

            while ((line = br.readLine()) != null) {

                if (line.trim().equals("")) {
                    continue;
                }

                // parts[0]=username, parts[1]=password,
                // parts[2]=studentName, parts[3]=roomNumber, parts[4]=rollNumber
                String[] parts = line.split(",");

                String username    = parts[0];
                String password    = parts[1];
                String studentName = parts[2];
                String roomNumber  = parts[3];
                int    rollNumber  = Integer.parseInt(parts[4]);

                Student student = new Student(username, password,
                                              studentName, roomNumber,
                                              rollNumber);
                studentList.add(student);
            }

            br.close();
            fr.close();

        } catch (Exception e) {
            System.out.println("Error loading students: " + e.getMessage());
        }

        return studentList;
    }

    // -------------------------------------------------------
    // DELETE all logs for a specific student
    // (used when resetting a student's monthly data)
    // Reads all lines, skips the student's lines, rewrites file
    // -------------------------------------------------------
    public static void deleteLogsForStudent(String studentUsername) {

        try {
            File file = new File(USAGE_LOG_FILE);
            if (!file.exists()) {
                return;
            }

            // Read all lines
            FileReader fr = new FileReader(USAGE_LOG_FILE);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> remainingLines = new ArrayList<String>();

            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("")) {
                    continue;
                }
                // Keep lines that do NOT belong to this student
                String[] parts = line.split(",");
                if (!parts[0].equals(studentUsername)) {
                    remainingLines.add(line);
                }
            }
            br.close();
            fr.close();

            // Rewrite file with only remaining lines
            // false = overwrite mode (not append)
            FileWriter fw = new FileWriter(USAGE_LOG_FILE, false);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < remainingLines.size(); i++) {
                bw.write(remainingLines.get(i));
                bw.newLine();
            }

            bw.close();
            fw.close();

            System.out.println("Logs deleted for: " + studentUsername);

        } catch (Exception e) {
            System.out.println("Error deleting logs: " + e.getMessage());
        }
    }
}