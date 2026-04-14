// BillCalculator.java
// This class handles all bill calculation logic
// Concepts: Static methods, if-else, Math operations

public class BillCalculator {

    // Slab rates for electricity billing (like real MSEB rates)
    // These are fixed values so we make them static final (constants)
    static final double RATE_SLAB1 = 3.50;  // per unit for first 100 units
    static final double RATE_SLAB2 = 5.00;  // per unit for 101 to 300 units
    static final double RATE_SLAB3 = 7.50;  // per unit for above 300 units
    static final double FIXED_CHARGE = 50.0; // fixed meter charge per month

    // Calculate bill based on total units consumed
    // This is a static method so we can call it without creating an object
    // Usage: BillCalculator.calculateBill(48.6)
    public static double calculateBill(double totalUnits) {

        double billAmount = 0;

        // Slab 1: If units are 100 or less
        if (totalUnits <= 100) {
            billAmount = totalUnits * RATE_SLAB1;
        }
        // Slab 2: If units are between 101 and 300
        else if (totalUnits <= 300) {
            // First 100 units at slab 1 rate
            double slab1Cost = 100 * RATE_SLAB1;
            // Remaining units at slab 2 rate
            double slab2Cost = (totalUnits - 100) * RATE_SLAB2;
            billAmount = slab1Cost + slab2Cost;
        }
        // Slab 3: If units are above 300
        else {
            // First 100 units at slab 1 rate
            double slab1Cost = 100 * RATE_SLAB1;
            // Next 200 units (101 to 300) at slab 2 rate
            double slab2Cost = 200 * RATE_SLAB2;
            // Remaining units above 300 at slab 3 rate
            double slab3Cost = (totalUnits - 300) * RATE_SLAB3;
            billAmount = slab1Cost + slab2Cost + slab3Cost;
        }

        // Add fixed meter charge on top of usage charge
        billAmount = billAmount + FIXED_CHARGE;

        // Round to 2 decimal places
        billAmount = Math.round(billAmount * 100.0) / 100.0;

        return billAmount;
    }

    // Calculate units from watts and hours
    // Formula: units = (watts x hours) / 1000
    public static double calculateUnits(int watts, double hours) {
        double units = (watts * hours) / 1000.0;
        // Round to 2 decimal places
        units = Math.round(units * 100.0) / 100.0;
        return units;
    }

    // Calculate total units for one student from their log list
    // Takes an ArrayList of UsageLog and adds up all units
    public static double calculateTotalUnits(java.util.ArrayList<UsageLog> logList) {
        double total = 0;
        // Loop through each log and add the units
        for (int i = 0; i < logList.size(); i++) {
            total = total + logList.get(i).getUnitsConsumed();
        }
        // Round to 2 decimal places
        total = Math.round(total * 100.0) / 100.0;
        return total;
    }

    // Show a step by step slab breakdown as text
    // Useful for the bill screen in the UI
    public static void printBillBreakdown(double totalUnits) {

        System.out.println("===== BILL BREAKDOWN =====");
        System.out.println("Total Units Used: " + totalUnits);
        System.out.println();

        if (totalUnits <= 100) {
            System.out.println("Slab 1: " + totalUnits +
                               " units x Rs." + RATE_SLAB1 +
                               " = Rs." + (totalUnits * RATE_SLAB1));
        } else if (totalUnits <= 300) {
            System.out.println("Slab 1: 100 units x Rs." + RATE_SLAB1 +
                               " = Rs." + (100 * RATE_SLAB1));
            System.out.println("Slab 2: " + (totalUnits - 100) +
                               " units x Rs." + RATE_SLAB2 +
                               " = Rs." + ((totalUnits - 100) * RATE_SLAB2));
        } else {
            System.out.println("Slab 1: 100 units x Rs." + RATE_SLAB1 +
                               " = Rs." + (100 * RATE_SLAB1));
            System.out.println("Slab 2: 200 units x Rs." + RATE_SLAB2 +
                               " = Rs." + (200 * RATE_SLAB2));
            System.out.println("Slab 3: " + (totalUnits - 300) +
                               " units x Rs." + RATE_SLAB3 +
                               " = Rs." + ((totalUnits - 300) * RATE_SLAB3));
        }

        System.out.println("Fixed Meter Charge: Rs." + FIXED_CHARGE);
        System.out.println("--------------------------");
        System.out.println("TOTAL BILL: Rs." + calculateBill(totalUnits));
        System.out.println("==========================");
    }
}