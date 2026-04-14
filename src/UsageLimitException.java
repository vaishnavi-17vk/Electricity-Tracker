// UsageLimitException.java
// A custom exception that fires when student crosses 200 units
// Concepts: Custom Exception, extends Exception

public class UsageLimitException extends Exception {

    // The units that caused the exception
    private double unitsConsumed;

    // Constructor - takes a message and the units value
    public UsageLimitException(String message, double unitsConsumed) {
        // super() sends message to the parent Exception class
        super(message);
        this.unitsConsumed = unitsConsumed;
    }

    // Getter for unitsConsumed
    public double getUnitsConsumed() {
        return unitsConsumed;
    }
}