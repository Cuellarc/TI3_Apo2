package icesi.edu.co.duckhunt.view;

import icesi.edu.co.duckhunt.controllers.GameController;
import icesi.edu.co.duckhunt.model.Duck;
import icesi.edu.co.duckhunt.model.Player;
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
    private List<Pane> duckImagesPanes;
    private HashMap<String, Image> imageHashMap;

    private Image cursorImage;

    private Player player;

    public GameController getController() {
        return controller;
    }

    public GameView() {
        initialize();
    }

    public void initialize() {
        // Inicialización de variables.
        duckImages = new ArrayList<>();
        imageHashMap = new HashMap<>();
        duckImagesPanes = new ArrayList<>();
        List<Duck> ducks = GameController.getDucks();
        pane = new Pane();
        player = new Player();
        cursorImage = new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/objetivo.png").toString());
        pane.setCursor(new ImageCursor(cursorImage));

        // Fondo y color.
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
        Background background = new Background(backgroundFill);
        pane.setBackground(background);

        // Permitir matar patos.
        for (int i = 0; i < ducks.size(); i++) {
            Pane paneOfDuck = new Pane();
            Duck duck = ducks.get(i);
            ImageView duckView = new ImageView(duck.getImagePath());
            duckView.setFitWidth(Duck.WIDTH);
            duckView.setFitHeight(Duck.HEIGHT);
            paneOfDuck.getChildren().add(duckView);
            duckImagesPanes.add(paneOfDuck);
            duckImages.add(duckView);
            paneOfDuck.setOnMouseClicked(event -> {
                if (duck.isClickable()) {
                    duck.kill();
                    player.disparar(true); // Consumir una bala si acierta
                    updateBullets(player.getBullets()); // Actualizar la vista del contador de balas
                }
            });
            pane.getChildren().add(paneOfDuck);
        }

        // Permitir disparar fuera de los patos.
        pane.setOnMouseClicked(event -> {
            boolean clickOnDuck = false;
            for (Pane duckPane : duckImagesPanes) {
                if (duckPane.getBoundsInParent().contains(event.getX(), event.getY())) {
                    clickOnDuck = true;
                    break;
                }
            }
            if (!clickOnDuck) {
                player.disparar(false); // Consumir una bala si falla
                updateBullets(player.getBullets()); // Actualizar la vista del contador de balas
            }
        });

        // Añadir imagen de fondo y su configuración.
        ImageView imageView = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/Background/background.png").toString()));
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);
        pane.getChildren().add(imageView);

        // Añadir rectángulos para balas, vidas y puntaje.
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

        // Añadir balas.
        ImageView imagBulet1 = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/Bulet/WhatsApp Image 2024-05-15 at 09.19.00_cbb595db (1).jpg").toString()));
        ImageView imagBulet2 = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/Bulet/WhatsApp Image 2024-05-15 at 09.19.00_cbb595db (1).jpg").toString()));
        ImageView imagBulet3 = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/Bulet/WhatsApp Image 2024-05-15 at 09.19.00_cbb595db (1).jpg").toString()));

        // Ajustar dimensiones de las balas
        imagBulet1.setFitWidth(10);
        imagBulet1.setFitHeight(15);
        imagBulet2.setFitWidth(10);
        imagBulet2.setFitHeight(15);
        imagBulet3.setFitWidth(10);
        imagBulet3.setFitHeight(15);

        // Posicionar la primera bala dentro del rectángulo
        double rectX = rectangleBults.localToScene(rectangleBults.getBoundsInLocal()).getMinX();
        double rectY = rectangleBults.localToScene(rectangleBults.getBoundsInLocal()).getMinY();

        imagBulet1.setLayoutX(rectX + (rectangleBults.getWidth() - imagBulet1.getFitWidth()) / 2);
        imagBulet1.setLayoutY(rectY + (rectangleBults.getHeight() - imagBulet1.getFitHeight()) / 2);

        // Posicionar la segunda bala al lado de la primera bala
        double spacing = 10; // Espacio entre las balas
        imagBulet2.setLayoutX(imagBulet1.getLayoutX() + imagBulet1.getFitWidth() + spacing);
        imagBulet2.setLayoutY(rectY + (rectangleBults.getHeight() - imagBulet2.getFitHeight()) / 2);

        // Posicionar la tercera bala al lado de la primera bala
        double spacing3 = 10; // Espacio entre las balas
        imagBulet3.setLayoutX(imagBulet1.getLayoutX() - imagBulet1.getFitWidth() - spacing3);
        imagBulet3.setLayoutY(rectY + (rectangleBults.getHeight() - imagBulet3.getFitHeight()) / 2);

        pane.getChildren().add(imagBulet1);
        pane.getChildren().add(imagBulet2);
        pane.getChildren().add(imagBulet3);

        controller.setUpdateView(this::updateDucks);
    }

    public void update() {
        Platform.runLater(this::updateDucks);
    }

    public void updateBullets(int bullets) {
        // Recorremos las balas y las ocultamos según la cantidad actual de balas restantes
        for (int i = 0; i < 3; i++) {
            ImageView bulletImage = (ImageView) pane.getChildren().get(i + 6); // Los índices 6, 7 y 8 corresponden a las imágenes de las balas
            if (i < bullets) {
                bulletImage.setVisible(true); // Mostramos la bala si es una de las balas restantes
            } else {
                bulletImage.setVisible(false); // Ocultamos la bala si ya se ha disparado
            }
        }
    }

    // Actualizar patos (Activar Runnable).
    public void updateDucks() {
        List<Duck> ducks = GameController.getDucks();

        Duck duck = ducks.get(controller.getActualDuckIndex());
        if (duck.isClickable() && duck.isDead()) {
            duck.setDirection();
            duck.setDead(false);
        }
        Pane duckPane = duckImagesPanes.get(controller.getActualDuckIndex());
        ImageView duckView = duckImages.get(controller.getActualDuckIndex());
        String imagePath = duck.getImagePath();

        Image image = null;
        if (!imageHashMap.containsKey(imagePath)) {
            image = new Image(imagePath);
            imageHashMap.put(imagePath, image);
        } else {
            image = imageHashMap.get(imagePath);
        }
        duckPane.setLayoutX(duck.getX());
        duckPane.setLayoutY(duck.getY());
        duckView.setImage(image);

        // Verificar si se hizo clic en un pato
        duckPane.setOnMouseClicked(event -> {
            if (duck.isClickable()) {
                duck.kill();
            }
        });
    }

    public Pane getPane() {
        return pane;
    }
}
