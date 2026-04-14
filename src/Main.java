// Main.java
// This is the entry point of the JavaFX application
// It loads the login.fxml and shows the login window

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // start() is called automatically by JavaFX when app launches
    public void start(Stage primaryStage) throws Exception {

        // Load the login screen fxml file
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/login.fxml")
        );

        // Parent is the root node of the fxml layout
        Parent root = loader.load();

        // Create a scene with the loaded layout
        // 420 = width, 340 = height
        Scene scene = new Scene(root, 420, 340);

        // Load the CSS stylesheet
        scene.getStylesheets().add(
            getClass().getResource("/style.css").toExternalForm()
        );

        // Set window title
        primaryStage.setTitle("Hostel Electricity Tracker");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // main() launches the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}