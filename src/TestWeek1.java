// TestWeek1.java
// Run this file to test all 6 classes you created this week
// If no errors appear, Week 1 is complete!

import java.util.ArrayList;

public class TestWeek1 {

    public static void main(String[] args) {

        System.out.println("===== WEEK 1 TEST =====");
        System.out.println();

        // --- Test 1: Create a Student object ---
        System.out.println("-- Test 1: Student Object --");
        Student student1 = new Student("rahul123", "pass123",
                                       "Rahul Sharma", "Room 204", 101);
        student1.showInfo();
        System.out.println();

        // --- Test 2: Create an Admin object ---
        System.out.println("-- Test 2: Admin Object --");
        Admin admin1 = new Admin("warden01", "admin999", "Mr. Patil");
        admin1.showInfo();
        System.out.println();

        // --- Test 3: Create Appliances and store in ArrayList ---
        System.out.println("-- Test 3: Appliance ArrayList --");
        ArrayList<Appliance> applianceList = new ArrayList<Appliance>();
        applianceList.add(new Appliance("Fan", 75));
        applianceList.add(new Appliance("Tubelight", 40));
        applianceList.add(new Appliance("Laptop", 65));
        applianceList.add(new Appliance("Phone Charger", 10));
        applianceList.add(new Appliance("Iron", 1000));
        applianceList.add(new Appliance("LED Bulb", 9));

        // Print all appliances using a for loop
        for (int i = 0; i < applianceList.size(); i++) {
            applianceList.get(i).showInfo();
        }
        System.out.println();

        // --- Test 4: Calculate units using Appliance method ---
        System.out.println("-- Test 4: Units Calculation --");
        Appliance fan = new Appliance("Fan", 75);
        double units = fan.calculateUnits(8); // Fan used for 8 hours
        System.out.println("Fan for 8 hours = " + units + " units");
        // Expected output: 0.6 units

        Appliance laptop = new Appliance("Laptop", 65);
        double laptopUnits = laptop.calculateUnits(4); // Laptop for 4 hours
        System.out.println("Laptop for 4 hours = " + laptopUnits + " units");
        // Expected output: 0.26 units
        System.out.println();

        // --- Test 5: Create UsageLog objects and store in ArrayList ---
        System.out.println("-- Test 5: UsageLog ArrayList --");
        ArrayList<UsageLog> logList = new ArrayList<UsageLog>();
        logList.add(new UsageLog("rahul123", "Fan", 75, 8, "01-Apr-2025"));
        logList.add(new UsageLog("rahul123", "Laptop", 65, 4, "01-Apr-2025"));
        logList.add(new UsageLog("rahul123", "Phone Charger", 10, 2, "02-Apr-2025"));

        // Print all logs using for loop
        for (int i = 0; i < logList.size(); i++) {
            System.out.println(logList.get(i).toString());
        }
        System.out.println();

        // --- Test 6: Custom Exception ---
        System.out.println("-- Test 6: Custom Exception --");
        try {
            double totalUnits = 250.0; // Simulate student crossing 200 units

            // If units cross 200, throw our custom exception
            if (totalUnits > 200) {
                throw new UsageLimitException(
                    "Warning! Usage limit crossed!", totalUnits);
            }

        } catch (UsageLimitException e) {
            // This block runs when exception is caught
            System.out.println("Exception caught: " + e.getMessage());
            System.out.println("Units consumed: " + e.getUnitsConsumed());
        }

        System.out.println();
        System.out.println("===== ALL WEEK 1 TESTS PASSED =====");
    }
}