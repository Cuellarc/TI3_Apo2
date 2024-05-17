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

    public Stage getStage(){
        return stage;
    }

    public void setScene(Scene scene){
        this.stage.setScene(scene);
    }

    public void setSceneParent(Parent parent){
        Scene scene = new Scene(parent);
        setScene(scene);
    }

    public void setScene(String file){
        FXMLLoader fxmlLoader = new FXMLLoader((HelloApplication.class.getResource(file)));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            setScene(scene);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}