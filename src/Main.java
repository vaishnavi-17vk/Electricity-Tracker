// Main.java
// This is the entry point of the JavaFX application
// It loads the login.fxml and shows the login window

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML file safely
            URL fxmlLocation = getClass().getResource("login.fxml");

            if (fxmlLocation == null) {
                System.out.println("Error: login.fxml not found!");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            Scene scene = new Scene(root, 420, 340);

            // Load CSS safely
            URL cssLocation = getClass().getResource("style.css");

            if (cssLocation != null) {
                scene.getStylesheets().add(cssLocation.toExternalForm());
            } else {
                System.out.println("Warning: style.css not found!");
            }

            primaryStage.setTitle("Hostel Electricity Tracker");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Error starting application:");
            e.printStackTrace(); // IMPORTANT for debugging
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}