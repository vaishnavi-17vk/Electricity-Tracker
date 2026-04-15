// AdminController.java
// Controls the admin dashboard
// Concepts: TableView with ObservableList, File export

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Optional;

public class AdminController {

    @FXML private Label adminTitleLabel;
    @FXML private TableView<AdminRowData> adminTable;
    @FXML private TableColumn<AdminRowData, String> colName;
    @FXML private TableColumn<AdminRowData, String> colRoom;
    @FXML private TableColumn<AdminRowData, String> colUser;
    @FXML private TableColumn<AdminRowData, String> colUnits;
    @FXML private TableColumn<AdminRowData, String> colBill;
    @FXML private TableColumn<AdminRowData, String> colStatus;

    private Admin currentAdmin;
    private LoginManager loginManager;
    private ObservableList<AdminRowData> tableData;

    // Called from LoginController
    public void initData(Admin admin, LoginManager lm) {
        this.currentAdmin = admin;
        this.loginManager = lm;

        adminTitleLabel.setText("Admin Panel — " + admin.getAdminName());

        setupTable();
        loadStudentData();
    }

    private void setupTable() {
        tableData = FXCollections.observableArrayList();

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        colUnits.setCellValueFactory(new PropertyValueFactory<>("units"));
        colBill.setCellValueFactory(new PropertyValueFactory<>("bill"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        adminTable.setItems(tableData);
    }

    private void loadStudentData() {
        tableData.clear();
        ArrayList<Student> allStudents = loginManager.getAllStudents();

        for (int i = 0; i < allStudents.size(); i++) {
            Student s = allStudents.get(i);
            ArrayList<UsageLog> logs = FileHandler.loadUsageLogs(s.getUsername());
            double totalUnits = BillCalculator.calculateTotalUnits(logs);
            double totalBill = BillCalculator.calculateBill(totalUnits);

            String status = totalUnits > 200 ? "HIGH USAGE" : "Normal";

            tableData.add(new AdminRowData(
                s.getStudentName(),
                s.getRoomNumber(),
                s.getUsername(),
                totalUnits + " units",
                "Rs." + totalBill,
                status
            ));
        }
    }

    @FXML
    private void handleRefresh() {
        loadStudentData();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Data refreshed.");
        alert.showAndWait();
    }

    @FXML
    private void handleExport() {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("admin_report.txt", false);
            java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);

            bw.write("===== HOSTEL ELECTRICITY REPORT =====");
            bw.newLine();

            ArrayList<Student> all = loginManager.getAllStudents();
            for (int i = 0; i < all.size(); i++) {
                Student s = all.get(i);
                ArrayList<UsageLog> logs = FileHandler.loadUsageLogs(s.getUsername());
                double units = BillCalculator.calculateTotalUnits(logs);
                double bill = BillCalculator.calculateBill(units);

                bw.write("Name: " + s.getStudentName() +
                        " | Room: " + s.getRoomNumber() +
                        " | Units: " + units +
                        " | Bill: Rs." + bill);
                bw.newLine();
            }
            bw.close();
            fw.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Report saved as admin_report.txt");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Export failed: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleLogout() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/login.fxml")
                );
                Parent root = loader.load();
                Stage stage = (Stage) adminTable.getScene().getWindow();
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
}