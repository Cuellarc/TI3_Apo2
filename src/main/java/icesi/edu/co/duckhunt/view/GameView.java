package icesi.edu.co.duckhunt.view;

import icesi.edu.co.duckhunt.controllers.GameController;
import icesi.edu.co.duckhunt.model.Duck;
import icesi.edu.co.duckhunt.model.UpdateView;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GameView implements UpdateView {

    private GameController controller = GameController.getInstance();
    private Pane pane;

    private List<ImageView> duckImages;
    private List<Pane> duckImagesPanes;
    private HashMap<String, Image> imageHashMap;

    private Image cursorImage;

    public GameController getController() {
        return controller;
    }
    public GameView(){
        initialize();
    }

    public void initialize(){
        //Inicializacion de variables.
        duckImages = new ArrayList<>();
        imageHashMap = new HashMap<>();
        duckImagesPanes = new ArrayList<>();
        List<Duck> ducks = GameController.getDucks();
        pane = new Pane();
        cursorImage= new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/objetivo.png").toString());
        pane.setCursor(new ImageCursor(cursorImage));

        //Fondo y color.
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
        Background background = new Background(backgroundFill);
        pane.setBackground(background);

        //Añadir patos.
        for (int i = 0; i < ducks.size(); i++) {
            String imagePath = ducks.get(i).getImagePath();
            Image image = null;
            if(!imageHashMap.containsKey(imagePath)){ //Si la imagen no esta guardada en la hash.
                image = new Image(imagePath);
                imageHashMap.put(imagePath, image); //Se guarda la imagen.
            }
            else {
                image = imageHashMap.get(imagePath); //Si si esta, solo se carga.
            }
            ImageView duckView = new ImageView(image);
            //Modificacion de imagenes:
            duckView.setFitWidth(Duck.WIDTH);
            duckView.setFitHeight(Duck.HEIGHT);

            Pane duckPane = new Pane();
            duckPane.setLayoutX(ducks.get(i).getX());
            duckPane.setLayoutY(ducks.get(i).getY());
            duckPane.setPrefSize(Duck.WIDTH+10,Duck.HEIGHT+10);

            BackgroundFill paneFill = new BackgroundFill(Color.TRANSPARENT, null, null);
            Background paneBg = new Background(paneFill);
            duckPane.setBackground(paneBg);

            //Añadir imagen al pane.
            duckPane.getChildren().add(duckView);
            pane.getChildren().add(duckPane);
            duckImagesPanes.add(duckPane);
            duckImages.add(duckView);
        }

        //Añadir imagen de fondo y su configuracion.
        ImageView imageView = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/Background/background.png").toString()));
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);
        pane.getChildren().add(imageView);

        //Añadir rectangulos para balas, vidas y puntaje
        /*Rectangle rectangle = new Rectangle(100, 360, 400, 30); // (x, y, width, height)
        rectangle.setFill(Color.BLACK);
        rectangle.setStroke(Color.GREEN);
        rectangle.setStrokeWidth(2);
        pane.getChildren().add(rectangle);*/

        //Permitir matar patos.
        for (int i = 0; i < ducks.size(); i++) {
            Pane paneOfDuck = duckImagesPanes.get(i);
            Duck duck = ducks.get(i);
            paneOfDuck.setOnMouseClicked(event -> {
                duck.kill();
            });
        }
        controller.setUpdateView(this::updateDucks);
    }

    public void update(){
        Platform.runLater(this::updateDucks);
    }

    //Actualizar patos (Activar Runnable).
    public void updateDucks(){
        List<Duck> ducks = GameController.getDucks();

        Duck duck = ducks.get(controller.getActualDuckIndex());
        if(duck.isClickable() && duck.isDead()){
            duck.setDirection();
            duck.setDead(false);
        }
        Pane duckPane = duckImagesPanes.get(controller.getActualDuckIndex());
        ImageView duckView = duckImages.get(controller.getActualDuckIndex());
        String imagePath = duck.getImagePath();

        Image image = null;
        if(!imageHashMap.containsKey(imagePath)){
            image = new Image(imagePath);
            imageHashMap.put(imagePath, image);
        }
        else {
            image = imageHashMap.get(imagePath);
        }
        duckPane.setLayoutX(duck.getX());
        duckPane.setLayoutY(duck.getY());
        duckView.setImage(image);
    }

    public Pane getPane() {
        return pane;
    }
}