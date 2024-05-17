package icesi.edu.co.duckhunt.view;

import icesi.edu.co.duckhunt.controllers.GameController;
import icesi.edu.co.duckhunt.model.Duck;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;
import java.util.List;

public class GameView {

    private GameController controller;
    private Pane pane;

    private List<ImageView> duckImages;

    public GameController getController() {
        return controller;
    }
    public GameView(){
        init();
    }
    public void init(){
        //Inicializacion de variables.
        duckImages = new ArrayList<>();
        controller = GameController.getInstance();
        List<Duck> ducks = GameController.getDucks();
        pane = new Pane();

        //Fondo y color.
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
        Background background = new Background(backgroundFill);
        pane.setBackground(background);

        //Añadir imagen de fondo y su configuracion.
        ImageView imageView = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/Background/background.png").toString()));
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);
        pane.getChildren().add(imageView);

        //Añadir rectangulos para balas, vidas y puntaje
        Rectangle rectangle = new Rectangle(100, 360, 400, 30); // (x, y, width, height)
        rectangle.setFill(Color.BLACK);
        rectangle.setStroke(Color.GREEN);
        rectangle.setStrokeWidth(2);
        pane.getChildren().add(rectangle);

        //Añadir patos (No funcional por ahora). REVISAR ANIMATION (Programa del profe) PARA AÑADIR PATOS.
        for (int i = 0; i < ducks.size(); i++) {
            ImageView duckImage = new ImageView(ducks.get(i).getId() + "");
            label.setLayoutX(ducks.get(i).getX());
            label.setLayoutY(ducks.get(i).getY());
            labels.add(label);
            pane.getChildren().add(label);
        }

        //Permitir matar patos (No funcional por ahora).
        for (int i = 0; i < labels.size(); i++) {
            Label label = labels.get(i);
            label.setOnMouseClicked(event -> {
                Duck duck = controller.getDucks().get(labels.indexOf(label));
                duck.kill();
            });
        }
    }

    public void updateView(){
        Platform.runLater(() -> {
            for (int i = 0; i < labels.size(); i++) {
                labels.get(i).setLayoutX(controller.getDucks().get(i).getX());
                labels.get(i).setLayoutY(controller.getDucks().get(i).getY());
            }        });

    }

    public Pane getPane() {
        return pane;
    }
}