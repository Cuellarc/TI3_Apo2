package icesi.edu.co.duckhunt.controllers;

import icesi.edu.co.duckhunt.view.GameView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button easy;
    @FXML
    private Button medium;
    @FXML
    private Button hard;
    private GameView gameView;



    @FXML
    public void onStartClick(ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("/icesi/edu/co/duckhunt/view/difficulty-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onExitClick(){
        System.exit(0);
    }

    @FXML
    public void onDifficultyClick(ActionEvent event){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Button pressedButton = (Button) event.getSource();

        if(pressedButton == easy){
            gameView = new GameView(1);
            stage.setTitle("Duck Hunt - Easy");
        }
        else if (pressedButton == medium) {
            gameView = new GameView(5);
            stage.setTitle("Duck Hunt - Medium");
        }
        else if (pressedButton == hard) {
            gameView = new GameView(10);
            stage.setTitle("Duck Hunt - Hard");
        }

        Scene scene = new Scene(gameView.getPane(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
