package icesi.edu.co.duckhunt;

import icesi.edu.co.duckhunt.controllers.MenuController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static HelloApplication instance;
    public Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
            this.stage = stage;
            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("/icesi/edu/co/duckhunt/view/DuckHuntMenu-view.fxml"));
            Parent root = fxmlLoader.load();

            stage.setTitle("Duck Hunt");
            stage.setScene(new Scene(root));
            stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}