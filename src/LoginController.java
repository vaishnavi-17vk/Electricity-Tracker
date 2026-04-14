// LoginController.java
// Handles all logic for the login screen
// Concepts: @FXML annotation, event handling, loading new screens

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class LoginController {

    // @FXML links Java variable to the fx:id in the fxml file
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    // LoginManager from Week 2
    private LoginManager loginManager = new LoginManager();

    // Count wrong attempts
    private int wrongAttempts = 0;

    // This method is called when LOGIN button is clicked
    // It is linked via onAction="#handleLogin" in the fxml
    @FXML
    private void handleLogin() {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Empty field check
        if (username.equals("") || password.equals("")) {
            showError("Please enter both username and password.");
            return;
        }

        // Use LoginManager to check credentials
        String result = loginManager.login(username, password);

        if (result.equals("student")) {

            Student loggedInStudent =
                loginManager.getStudentByUsername(username);

            // Open student dashboard
            openStudentDashboard(loggedInStudent);

        } else if (result.equals("admin")) {

            Admin loggedInAdmin =
                loginManager.getAdminByUsername(username);

            // Open admin dashboard
            openAdminDashboard(loggedInAdmin);

        } else {
            wrongAttempts = wrongAttempts + 1;

            if (wrongAttempts >= 3) {
                showError("Too many attempts. Closing app.");
                javafx.application.Platform.exit();
            } else {
                int remaining = 3 - wrongAttempts;
                showError("Wrong credentials. " +
                          remaining + " attempt(s) left.");
                passwordField.clear();
            }
        }
    }

    // Shows error message below the form
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    // Opens student dashboard in the same window
    private void openStudentDashboard(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/student_dashboard.fxml")
            );
            Parent root = loader.load();

            // Pass student data to the dashboard controller
            StudentController controller = loader.getController();
            controller.initData(student);

            // Get current window and change the scene
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
            );
            stage.setTitle("Student Dashboard — " +
                           student.getStudentName());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            showError("Error loading dashboard: " + e.getMessage());
        }
    }

    // Opens admin dashboard in the same window
    private void openAdminDashboard(Admin admin) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/admin_dashboard.fxml")
            );
            Parent root = loader.load();

            AdminController controller = loader.getController();
            controller.initData(admin, loginManager);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(root, 860, 560);
            scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
            );
            stage.setTitle("Admin Dashboard — " + admin.getAdminName());
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            showError("Error loading admin panel: " + e.getMessage());
        }
    }
}