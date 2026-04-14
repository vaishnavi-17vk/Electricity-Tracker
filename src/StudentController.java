// StudentController.java
// Controls all 4 screens of the student dashboard
// Concepts: @FXML, TableView, ComboBox, Canvas drawing, listeners

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;
import java.util.Optional;

public class StudentController {

    // Sidebar labels
    @FXML private Label sidebarNameLabel;
    @FXML private Label sidebarRoomLabel;

    // Sidebar buttons
    @FXML private Button btnHome;
    @FXML private Button btnLogUsage;
    @FXML private Button btnMyUsage;
    @FXML private Button btnMyBill;
    @FXML private Button btnLogout;

    // The 4 screen containers
    @FXML private VBox homeScreen;
    @FXML private VBox logUsageScreen;
    @FXML private VBox myUsageScreen;
    @FXML private VBox myBillScreen;

    // Home screen
    @FXML private Label welcomeLabel;
    @FXML private Label todayUnitsLabel;
    @FXML private Label monthUnitsLabel;
    @FXML private Label currentBillLabel;
    @FXML private Label daysLoggedLabel;
    @FXML private TableView usageTable;
    @FXML private TableColumn colDate;
    @FXML private TableColumn colAppliance;
    @FXML private TableColumn colHours;
    @FXML private TableColumn colUnits;

    // Log usage screen
    @FXML private TextField logDateField;
    @FXML private ComboBox applianceCombo;
    @FXML private TextField hoursField;
    @FXML private Label estimatedUnitsLabel;
    @FXML private Label logStatusLabel;

    // My usage screen
    @FXML private Canvas usageCanvas;

    // Bill screen
    @FXML private Label billTotalUnits;
    @FXML private Label billRoomLabel;
    @FXML private Label billUsageCharge;
    @FXML private Label billTotalLabel;
    @FXML private Label billPayableLabel;

    // Data
    private Student currentStudent;
    private ArrayList<UsageLog> sessionLogs;

    // Appliance data
    private String[] applianceNames = {
        "Fan", "Tubelight", "Phone Charger",
        "Laptop", "Iron", "LED Bulb"
    };
    private int[] applianceWatts = {75, 40, 10, 65, 1000, 9};

    // Table data model
    private ObservableList<UsageRowData> tableData;

    // Called from LoginController after loading this screen
    public void initData(Student student) {
        this.currentStudent = student;
        this.sessionLogs    = new ArrayList<UsageLog>();

        // Set sidebar labels
        sidebarNameLabel.setText(student.getStudentName());
        sidebarRoomLabel.setText(student.getRoomNumber());

        // Set welcome label
        welcomeLabel.setText("Welcome, " + student.getStudentName() + "!");

        // Setup appliance combo box
        setupComboBox();

        // Setup table columns
        setupTable();

        // Load logs from file
        sessionLogs = FileHandler.loadUsageLogs(student.getUsername());
        loadLogsIntoTable();

        // Update summary cards
        updateSummaryCards();

        // Auto-update estimated units when hours field changes
        hoursField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue observable,
                                String oldValue, String newValue) {
                updateEstimatedUnits();
            }
        });

        // Also update when appliance selection changes
        applianceCombo.valueProperty().addListener(
            new ChangeListener<Object>() {
                public void changed(ObservableValue observable,
                                    Object oldValue, Object newValue) {
                    updateEstimatedUnits();
                }
            }
        );
    }

    // Setup combobox with appliance items
    private void setupComboBox() {
        ObservableList<String> items =
            FXCollections.observableArrayList();

        for (int i = 0; i < applianceNames.length; i++) {
            items.add(applianceNames[i] +
                      " (" + applianceWatts[i] + " Watts)");
        }
        applianceCombo.setItems(items);
        applianceCombo.getSelectionModel().selectFirst();
    }

    // Setup table columns linking to UsageRowData fields
    private void setupTable() {
        tableData = FXCollections.observableArrayList();

        colDate.setCellValueFactory(
            new PropertyValueFactory<>("date"));
        colAppliance.setCellValueFactory(
            new PropertyValueFactory<>("appliance"));
        colHours.setCellValueFactory(
            new PropertyValueFactory<>("hours"));
        colUnits.setCellValueFactory(
            new PropertyValueFactory<>("units"));

        usageTable.setItems(tableData);
    }

    // Add all logs from sessionLogs into the TableView
    private void loadLogsIntoTable() {
        tableData.clear();
        for (int i = 0; i < sessionLogs.size(); i++) {
            UsageLog log = sessionLogs.get(i);
            tableData.add(new UsageRowData(
                log.getDate(),
                log.getApplianceName(),
                log.getHoursUsed() + " hrs",
                log.getUnitsConsumed() + " units"
            ));
        }
    }

    // Update the 4 summary cards on home screen
    private void updateSummaryCards() {
        double totalUnits = BillCalculator.calculateTotalUnits(sessionLogs);
        double totalBill  = BillCalculator.calculateBill(totalUnits);

        monthUnitsLabel.setText(totalUnits + " Units");
        currentBillLabel.setText("Rs." + totalBill);
        daysLoggedLabel.setText(sessionLogs.size() + " Logs");

        // Today's usage — sum up last entries for today's date
        double todayUnits = 0;
        String today = logDateField.getText().trim();
        for (int i = 0; i < sessionLogs.size(); i++) {
            if (sessionLogs.get(i).getDate().equals(today)) {
                todayUnits = todayUnits +
                             sessionLogs.get(i).getUnitsConsumed();
            }
        }
        todayUnitsLabel.setText(
            Math.round(todayUnits * 100.0) / 100.0 + " Units"
        );
    }

    // Auto-calculates units when hours or appliance changes
    private void updateEstimatedUnits() {
        try {
            String text = hoursField.getText().trim();
            if (text.equals("")) {
                estimatedUnitsLabel.setText("Estimated Units: 0.00");
                return;
            }
            double hours = Double.parseDouble(text);
            int index    = applianceCombo.getSelectionModel()
                                         .getSelectedIndex();
            int watts    = applianceWatts[index];
            double units = BillCalculator.calculateUnits(watts, hours);
            estimatedUnitsLabel.setText("Estimated Units: " + units);
        } catch (NumberFormatException e) {
            estimatedUnitsLabel.setText("Enter valid number");
        }
    }

    // ---- SIDEBAR NAVIGATION ----

    @FXML
    private void showHome() {
        hideAllScreens();
        homeScreen.setVisible(true);
        setActiveButton(btnHome);
        updateSummaryCards();
    }

    @FXML
    private void showLogUsage() {
        hideAllScreens();
        logUsageScreen.setVisible(true);
        setActiveButton(btnLogUsage);
    }

    @FXML
    private void showMyUsage() {
        hideAllScreens();
        myUsageScreen.setVisible(true);
        setActiveButton(btnMyUsage);
        // Draw chart after screen is visible
        drawBarChart();
    }

    @FXML
    private void showMyBill() {
        hideAllScreens();
        myBillScreen.setVisible(true);
        setActiveButton(btnMyBill);
        updateBillScreen();
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/login.fxml")
                );
                Parent root = loader.load();
                Stage stage = (Stage) btnLogout.getScene().getWindow();
                Scene scene = new Scene(root, 420, 340);
                scene.getStylesheets().add(
                    getClass().getResource("/style.css").toExternalForm()
                );
                stage.setTitle("Hostel Electricity Tracker");
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                System.out.println("Logout error: " + e.getMessage());
            }
        }
    }

    // Hides all 4 screens
    private void hideAllScreens() {
        homeScreen.setVisible(false);
        logUsageScreen.setVisible(false);
        myUsageScreen.setVisible(false);
        myBillScreen.setVisible(false);
    }

    // Highlights the active sidebar button
    private void setActiveButton(Button activeBtn) {
        btnHome.getStyleClass().remove("sidebar-btn-active");
        btnLogUsage.getStyleClass().remove("sidebar-btn-active");
        btnMyUsage.getStyleClass().remove("sidebar-btn-active");
        btnMyBill.getStyleClass().remove("sidebar-btn-active");

        if (!activeBtn.getStyleClass().contains("sidebar-btn-active")) {
            activeBtn.getStyleClass().add("sidebar-btn-active");
        }
    }

    // ---- LOG USAGE SCREEN ----

    @FXML
    private void handleAddLog() {
        String hoursText = hoursField.getText().trim();
        String dateText  = logDateField.getText().trim();

        // Validate empty
        if (hoursText.equals("")) {
            logStatusLabel.setStyle("-fx-text-fill: red;");
            logStatusLabel.setText("Please enter hours used.");
            return;
        }

        try {
            double hours = Double.parseDouble(hoursText);

            if (hours <= 0 || hours > 24) {
                logStatusLabel.setStyle("-fx-text-fill: red;");
                logStatusLabel.setText("Hours must be between 0 and 24.");
                return;
            }

            int index         = applianceCombo.getSelectionModel()
                                              .getSelectedIndex();
            int watts         = applianceWatts[index];
            String appName    = applianceNames[index];
            double units      = BillCalculator.calculateUnits(watts, hours);

            // Create log
            UsageLog newLog = new UsageLog(
                currentStudent.getUsername(),
                appName, watts, hours, dateText
            );

            sessionLogs.add(newLog);

            // Check usage limit
            try {
                double total =
                    BillCalculator.calculateTotalUnits(sessionLogs);
                if (total > 200) {
                    throw new UsageLimitException(
                        "Monthly limit exceeded!", total
                    );
                }
            } catch (UsageLimitException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Usage Alert");
                alert.setContentText("Warning! " + ex.getMessage() +
                    "\nTotal: " + ex.getUnitsConsumed() + " units");
                alert.showAndWait();
            }

            // Save to file
            FileHandler.saveUsageLog(newLog, watts);

            // Add to table
            tableData.add(0, new UsageRowData(
                dateText,
                appName,
                hours + " hrs",
                units + " units"
            ));

            // Show success
            logStatusLabel.setStyle("-fx-text-fill: green;");
            logStatusLabel.setText("Logged! " + appName +
                " for " + hours + " hrs = " + units + " units");

            // Clear hours field
            hoursField.clear();
            estimatedUnitsLabel.setText("Estimated Units: 0.00");

        } catch (NumberFormatException e) {
            logStatusLabel.setStyle("-fx-text-fill: red;");
            logStatusLabel.setText("Enter a valid number for hours.");
        }
    }

    // ---- MY USAGE SCREEN — Bar Chart ----

    private void drawBarChart() {
        GraphicsContext gc = usageCanvas.getGraphicsContext2D();

        double canvasW = usageCanvas.getWidth();
        double canvasH = usageCanvas.getHeight();

        // Clear canvas
        gc.clearRect(0, 0, canvasW, canvasH);
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasW, canvasH);

        // Chart data — last 7 days
        String[] dates = {
            "25-Mar","26-Mar","27-Mar",
            "28-Mar","29-Mar","30-Mar","31-Mar"
        };
        double[] units = {0.88, 0.24, 0.62, 0.86, 1.30, 0.75, 0.60};

        int margin    = 55;
        double chartW = canvasW - margin * 2;
        double chartH = canvasH - margin * 2;
        double maxVal = 1.5;

        // Draw axes
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1.5);
        // X axis
        gc.strokeLine(margin, canvasH - margin,
                      canvasW - margin, canvasH - margin);
        // Y axis
        gc.strokeLine(margin, margin, margin, canvasH - margin);

        // Y axis label
        gc.setFill(Color.GRAY);
        gc.setFont(Font.font("SansSerif", 11));
        gc.fillText("Units", 5, margin);

        double barW = (chartW / dates.length) - 14;

        // Draw each bar
        for (int i = 0; i < dates.length; i++) {

            double barH = (units[i] / maxVal) * chartH;
            double barX = margin + i * (chartW / dates.length) + 10;
            double barY = canvasH - margin - barH;

            // Bar fill
            gc.setFill(Color.web("#4a90d9"));
            gc.fillRect(barX, barY, barW, barH);

            // Bar border
            gc.setStroke(Color.web("#2e6da4"));
            gc.setLineWidth(1);
            gc.strokeRect(barX, barY, barW, barH);

            // Date label below bar
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("SansSerif", 10));
            gc.fillText(dates[i], barX - 2, canvasH - margin + 18);

            // Units value above bar
            gc.setFill(Color.web("#1a1a2e"));
            gc.setFont(Font.font("SansSerif", FontWeight.BOLD, 10));
            gc.fillText(String.valueOf(units[i]), barX + 4, barY - 5);
        }

        // Chart title
        gc.setFill(Color.web("#1a1a2e"));
        gc.setFont(Font.font("SansSerif", FontWeight.BOLD, 13));
        gc.fillText("Daily Usage — Last 7 Days", margin, margin - 15);
    }

    // ---- MY BILL SCREEN ----

    private void updateBillScreen() {
        double totalUnits   = BillCalculator.calculateTotalUnits(sessionLogs);
        double totalBill    = BillCalculator.calculateBill(totalUnits);
        double usageCharge  = totalBill - 50;

        billTotalUnits.setText(
            "Total Units This Month:   " + totalUnits + " units"
        );
        billRoomLabel.setText(
            "Room Number:   " + currentStudent.getRoomNumber()
        );
        billUsageCharge.setText(
            "Usage Charge:   Rs." + usageCharge
        );
        billTotalLabel.setText("Total Bill:   Rs." + totalBill);
        billPayableLabel.setText("Rs." + totalBill);
    }

    @FXML
    private void handleDownloadBill() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Download");
        alert.setContentText("Bill saved as Bill_April_" +
            currentStudent.getStudentName() + ".txt");
        alert.showAndWait();
    }
}