package icesi.edu.co.duckhunt.view;

import icesi.edu.co.duckhunt.controllers.GameController;
import icesi.edu.co.duckhunt.model.Duck;
import icesi.edu.co.duckhunt.model.UpdateView;

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


public class GameView implements UpdateView {

    private GameController controller = GameController.getInstance();
    private Pane pane;

    private List<ImageView> duckImages;

    public GameController getController() {
        return controller;
    }
    public GameView(){
        initialize();
    }

    public void initialize(){
        //Inicializacion de variables.
        duckImages = new ArrayList<>();
        List<Duck> ducks = GameController.getDucks();
        pane = new Pane();

        //Fondo y color.
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
        Background background = new Background(backgroundFill);
        pane.setBackground(background);

        //Añadir patos.
        for (int i = 0; i < ducks.size(); i++) {
            Image duckImage = new Image(ducks.get(i).getImagePath());
            ImageView duckView = new ImageView(duckImage);
            duckView.setFitWidth(Duck.WIDTH);
            duckView.setFitHeight(Duck.HEIGHT);
            duckView.setLayoutX(ducks.get(i).getX());
            duckView.setLayoutY(ducks.get(i).getY());

            pane.getChildren().add(duckView);
            duckImages.add(duckView);
        }

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

        //Permitir matar patos (No funcional por ahora).
        for (int i = 0; i < ducks.size(); i++) {
            ImageView duckImage = duckImages.get(i);
            Duck duck = ducks.get(i);
            duckImage.setOnMouseClicked(event -> {
                duck.kill();
            });
        }
        controller.setUpdateView(this::updateDucks);
    }

    public void update(){
        Platform.runLater(this::updateDucks);
    }

    public void updateDucks(){
        List<Duck> ducks = GameController.getDucks();
        for(int i = 0; i < ducks.size(); i++){
            Duck duck = ducks.get(i);
            ImageView duckView = duckImages.get(i);
            duckView.setLayoutX(duck.getX());
            duckView.setLayoutY(duck.getY());

            Image duckImage = new Image(duck.getImagePath());
            duckView.setImage(duckImage);
        }
    }

    public Pane getPane() {
        return pane;
    }
}