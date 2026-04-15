import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;

public class StudentController {

    // Sidebar labels
    @FXML private Label sidebarNameLabel;
    @FXML private Label sidebarRoomLabel;

    // Home labels
    @FXML private Label welcomeLabel;
    @FXML private Label todayUnitsLabel;
    @FXML private Label monthUnitsLabel;
    @FXML private Label currentBillLabel;
    @FXML private Label daysLoggedLabel;

    // Log usage fields
    @FXML private TextField logDateField;
    @FXML private ComboBox<String> applianceCombo;
    @FXML private TextField hoursField;
    @FXML private Label estimatedUnitsLabel;
    @FXML private Label logStatusLabel;

    // Bill labels
    @FXML private Label billTotalUnits;
    @FXML private Label billRoomLabel;
    @FXML private Label billUsageCharge;
    @FXML private Label billTotalLabel;
    @FXML private Label billPayableLabel;

    // Screens
    @FXML private VBox homeScreen;
    @FXML private VBox logUsageScreen;
    @FXML private VBox myUsageScreen;
    @FXML private VBox myBillScreen;

    private Student student;

    // ---------------- INIT ----------------
    public void initData(Student student) {
        this.student = student;

        sidebarNameLabel.setText(student.getStudentName());
        sidebarRoomLabel.setText(student.getRoomNumber());
        welcomeLabel.setText("Welcome, " + student.getStudentName());

        applianceCombo.getItems().addAll("Fan", "Light", "AC", "Laptop", "Heater");
    }

    // ---------------- SCREEN SWITCH ----------------
    @FXML
    private void showHome() {
        homeScreen.setVisible(true);
        logUsageScreen.setVisible(false);
        myUsageScreen.setVisible(false);
        myBillScreen.setVisible(false);
    }

    @FXML
    private void showLogUsage() {
        homeScreen.setVisible(false);
        logUsageScreen.setVisible(true);
        myUsageScreen.setVisible(false);
        myBillScreen.setVisible(false);
    }

    @FXML
    private void showMyUsage() {
        homeScreen.setVisible(false);
        logUsageScreen.setVisible(false);
        myUsageScreen.setVisible(true);
        myBillScreen.setVisible(false);
    }

    @FXML
    private void showMyBill() {
        homeScreen.setVisible(false);
        logUsageScreen.setVisible(false);
        myUsageScreen.setVisible(false);
        myBillScreen.setVisible(true);
    }

    // ---------------- LOG USAGE ----------------
    @FXML
    private void handleAddLog() {
        try {
            String appliance = applianceCombo.getValue();
            double hours = Double.parseDouble(hoursField.getText());

            double units = hours * 0.5; // simple calculation

            estimatedUnitsLabel.setText("Estimated Units: " + units);
            logStatusLabel.setText("Log Added Successfully!");

        } catch (Exception e) {
            logStatusLabel.setText("Invalid input!");
        }
    }

    // ---------------- BILL ----------------
    @FXML
    private void handleDownloadBill() {
        System.out.println("Download Bill Clicked");
    }

    // ---------------- LOGOUT ----------------
    @FXML
    private void handleLogout() {
        System.out.println("Logout clicked");
        javafx.application.Platform.exit();
    }
}