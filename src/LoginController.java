import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    private LoginManager loginManager = new LoginManager();
    private int wrongAttempts = 0;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password.");
            return;
        }

        String result = loginManager.login(username, password);

        if (result.equals("student")) {

            Student loggedInStudent = loginManager.getStudentByUsername(username);

            if (loggedInStudent != null) {
                openStudentDashboard(loggedInStudent);
            } else {
                showError("Student not found after login.");
            }

        } else if (result.equals("admin")) {

            Admin loggedInAdmin = loginManager.getAdminByUsername(username);

            if (loggedInAdmin != null) {
                openAdminDashboard(loggedInAdmin);
            } else {
                showError("Admin not found after login.");
            }

        } else {

            wrongAttempts++;

            if (wrongAttempts >= 3) {
                showError("Too many attempts. Closing app.");
                javafx.application.Platform.exit();
            } else {
                int remaining = 3 - wrongAttempts;
                showError("Wrong credentials. " + remaining + " attempt(s) left.");
                passwordField.clear();
            }
        }
    }

    private void showError(String msg) {
        errorLabel.setText(msg);
    }

    // ✅ FIXED: correct FXML name
    private void openStudentDashboard(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/student_dashboard.fxml")
            );

            if (loader.getLocation() == null) {
                System.out.println("student_dashboard.fxml NOT FOUND");
                return;
            }

            Parent root = loader.load();

            StudentController controller = loader.getController();
            controller.initData(student);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.setTitle("Student Dashboard");
            stage.show();

        } catch (Exception e) {
            System.out.println("Error loading student dashboard:");
            e.printStackTrace();
        }
    }

    // ✅ FIXED: correct FXML name
    private void openAdminDashboard(Admin admin) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/admin_dashboard.fxml")
            );

            if (loader.getLocation() == null) {
                System.out.println("admin_dashboard.fxml NOT FOUND");
                return;
            }

            Parent root = loader.load();

            AdminController controller = loader.getController();
            controller.initData(admin, loginManager);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(root, 800, 500);
            stage.setScene(scene);
            stage.setTitle("Admin Dashboard");
            stage.show();

        } catch (Exception e) {
            System.out.println("Error loading admin dashboard:");
            e.printStackTrace();
        }
    }

    @FXML
    private void openSignup() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/signup.fxml")
            );

            if (loader.getLocation() == null) {
                System.out.println("signup.fxml NOT FOUND");
                return;
            }

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign Up");
            stage.show();

        } catch (Exception e) {
            System.out.println("Error loading signup:");
            e.printStackTrace();
        }
    }
}