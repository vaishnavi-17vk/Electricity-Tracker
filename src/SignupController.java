import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;

import java.io.FileWriter;
import java.io.PrintWriter;

public class SignupController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleRegister() {

        String user = usernameField.getText().trim();
        String pass = passwordField.getText().trim();

        // VALIDATION
        if (user.isEmpty() || pass.isEmpty()) {
            showAlert("Error", "Username and Password cannot be empty!");
            return;
        }

        try {
            PrintWriter pw = new PrintWriter(new FileWriter("users.txt", true));
            pw.println(user + "," + pass + ",student");
            pw.close();

            showAlert("Success", "User Registered Successfully!");

            // clear fields after signup
            usernameField.clear();
            passwordField.clear();

        } catch (Exception e) {
            showAlert("Error", "Failed to save user!");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}