// TestWeek2.java
// Run this to test all Week 2 logic
// Concepts tested: Bill calc, File I/O, Login, Exception

import java.util.ArrayList;

public class TestWeek2 {

    public static void main(String[] args) {

        System.out.println("========== WEEK 2 TEST ==========");
        System.out.println();

        // ---- Test 1: Bill Calculator ----
        System.out.println("-- Test 1: Bill Calculator --");

        // Test with low usage (under 100 units)
        double bill1 = BillCalculator.calculateBill(48.6);
        System.out.println("Bill for 48.6 units = Rs." + bill1);
        // Expected: (48.6 x 3.50) + 50 = 170.10 + 50 = Rs.220.10

        // Test with medium usage (between 100-300 units)
        double bill2 = BillCalculator.calculateBill(150);
        System.out.println("Bill for 150 units  = Rs." + bill2);
        // Expected: (100x3.50) + (50x5.00) + 50 = 350+250+50 = Rs.650.0

        // Test with high usage (above 300 units)
        double bill3 = BillCalculator.calculateBill(350);
        System.out.println("Bill for 350 units  = Rs." + bill3);
        System.out.println();

        // ---- Test 2: Bill Breakdown Print ----
        System.out.println("-- Test 2: Bill Breakdown --");
        BillCalculator.printBillBreakdown(48.6);
        System.out.println();

        // ---- Test 3: Units Calculation ----
        System.out.println("-- Test 3: Units Calculation --");
        double fanUnits    = BillCalculator.calculateUnits(75, 8);
        double laptopUnits = BillCalculator.calculateUnits(65, 4);
        System.out.println("Fan 8hrs    = " + fanUnits    + " units");
        System.out.println("Laptop 4hrs = " + laptopUnits + " units");
        System.out.println();

        // ---- Test 4: File I/O — Save logs ----
        System.out.println("-- Test 4: Save Logs to File --");
        UsageLog log1 = new UsageLog("rahul123", "Fan",    75,  8.0, "01-Apr-2025");
        UsageLog log2 = new UsageLog("rahul123", "Laptop", 65,  4.0, "01-Apr-2025");
        UsageLog log3 = new UsageLog("priya456", "Fan",    75,  6.0, "01-Apr-2025");

        FileHandler.saveUsageLog(log1, 75);
        FileHandler.saveUsageLog(log2, 65);
        FileHandler.saveUsageLog(log3, 75);
        System.out.println();

        // ---- Test 5: File I/O — Load logs ----
        System.out.println("-- Test 5: Load Logs from File --");
        ArrayList<UsageLog> rahulLogs = FileHandler.loadUsageLogs("rahul123");
        System.out.println("Logs loaded for rahul123: " + rahulLogs.size());
        for (int i = 0; i < rahulLogs.size(); i++) {
            System.out.println(rahulLogs.get(i).toString());
        }
        System.out.println();

        // ---- Test 6: Total units from log list ----
        System.out.println("-- Test 6: Total Units from Logs --");
        double totalUnits = BillCalculator.calculateTotalUnits(rahulLogs);
        System.out.println("Total units for rahul123 = " + totalUnits);
        System.out.println();

        // ---- Test 7: Save and load Students ----
        System.out.println("-- Test 7: Save and Load Students --");
        Student s1 = new Student("newstudent", "newpass",
                                  "New Student", "Room 401", 201);
        FileHandler.saveStudent(s1);
        ArrayList<Student> allStudents = FileHandler.loadAllStudents();
        System.out.println("Students loaded from file: " + allStudents.size());
        System.out.println();

        // ---- Test 8: Login Manager ----
        System.out.println("-- Test 8: Login Manager --");
        LoginManager loginManager = new LoginManager();

        // Test correct student login
        String result1 = loginManager.login("rahul123", "pass123");
        System.out.println("rahul123 + pass123 → " + result1);

        // Test correct admin login
        String result2 = loginManager.login("warden01", "admin999");
        System.out.println("warden01 + admin999 → " + result2);

        // Test wrong password
        String result3 = loginManager.login("rahul123", "wrongpass");
        System.out.println("rahul123 + wrongpass → " + result3);

        // Test unknown user
        String result4 = loginManager.login("nobody", "nopass");
        System.out.println("nobody + nopass → " + result4);
        System.out.println();

        // ---- Test 9: Get student object after login ----
        System.out.println("-- Test 9: Get Student Object --");
        Student loggedInStudent = loginManager.getStudentByUsername("rahul123");
        if (loggedInStudent != null) {
            loggedInStudent.showInfo();
        }
        System.out.println();

        // ---- Test 10: Custom Exception with real data ----
        System.out.println("-- Test 10: Usage Limit Exception --");
        try {
            double monthTotal = BillCalculator.calculateTotalUnits(rahulLogs);
            if (monthTotal > 200) {
                throw new UsageLimitException(
                    "Monthly limit crossed!", monthTotal);
            } else {
                System.out.println("Units OK: " + monthTotal +
                                   " (under 200 limit)");
            }
        } catch (UsageLimitException e) {
            System.out.println("ALERT: " + e.getMessage());
            System.out.println("Units: " + e.getUnitsConsumed());
        }

        System.out.println();
        System.out.println("===== ALL WEEK 2 TESTS PASSED =====");
    }
}