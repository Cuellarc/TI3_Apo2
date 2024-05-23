package icesi.edu.co.duckhunt.view;

import icesi.edu.co.duckhunt.controllers.GameController;
import icesi.edu.co.duckhunt.model.Duck;
import icesi.edu.co.duckhunt.model.UpdateView;

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
            duckView.setLayoutX(ducks.get(i).getX());
            duckView.setLayoutY(ducks.get(i).getY());

            //Añadir imagen al pane.
            pane.getChildren().add(duckView);
            duckImages.add(duckView);
        }

        //Añadir imagen de fondo y su configuracion.
        /*ImageView imageView = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/Background/background.png").toString()));
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);
        pane.getChildren().add(imageView);*/

        /*ImageView imagBulet = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/Bulet/WhatsApp Image 2024-05-15 at 09.19.00_cbb595db (1).jpg").toString()));
        imageView.setFitWidth(20);
        imageView.setFitHeight(30);
        pane.getChildren().add(imagBulet);*/

        //Añadir rectangulos para balas, vidas y puntaje
        Rectangle rectangle = new Rectangle(125, 360, 400, 30); // (x, y, width, height)
        rectangle.setFill(Color.BLACK);
        rectangle.setStroke(Color.GREEN);
        rectangle.setStrokeWidth(2);
        pane.getChildren().add(rectangle);

        Rectangle rectangleBults = new Rectangle(15, 360, 60, 30); // (x, y, width, height)
        rectangleBults.setFill(Color.BLACK);
        rectangleBults.setStroke(Color.GREEN);
        rectangleBults.setStrokeWidth(2);
        pane.getChildren().add(rectangleBults);

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

    //Actualizar patos (Activar Runnable).
    public void updateDucks(){
        List<Duck> ducks = GameController.getDucks();
        //for(int i = 0; i < ducks.size(); i++){

            Duck duck = controller.getActualDuck();
            ImageView duckView = duckImages.get(controller.getActualDuckIndex());
            String imagePath = controller.getActualDuck().getImagePath();

            Image image = null;
            if(!imageHashMap.containsKey(imagePath)){
                image = new Image(imagePath);
                imageHashMap.put(imagePath, image);
            }
            else {
                image = imageHashMap.get(imagePath);
            }
            duckView.setLayoutX(duck.getX());
            duckView.setLayoutY(duck.getY());
            duckView.setImage(image);
        //}
    }

    public Pane getPane() {
        return pane;
    }
}