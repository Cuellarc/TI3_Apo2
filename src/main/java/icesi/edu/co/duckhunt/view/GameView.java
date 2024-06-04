package icesi.edu.co.duckhunt.view;

import icesi.edu.co.duckhunt.controllers.GameController;
import icesi.edu.co.duckhunt.model.Duck;
import icesi.edu.co.duckhunt.model.Player;
import icesi.edu.co.duckhunt.model.UpdateView;

import javafx.scene.control.Button;
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

    private Button restartButton;

    private ImageView gameOverImageView;

    private List<ImageView> duckImages;
    private List<Pane> duckImagesPanes;
    private HashMap<String, Image> imageHashMap;

    private Image cursorImage;

    private Player player;

    private int numPatosToKill;

    private int killDucks=0;

    private List<ImageView> lifeImages;
    private List<ImageView> bulletImages;

    private boolean isGameFinished = false;


    public GameController getController() {
        return controller;
    }

    public GameView(int speed, int numPatosToKill) {
        this.numPatosToKill = numPatosToKill;
        controller.setGameSpeed(speed);
        controller.start();
        initialize();
    }

    public void initialize() {
        // Inicialización de variables.
        duckImages = new ArrayList<>();
        imageHashMap = new HashMap<>();
        duckImagesPanes = new ArrayList<>();
        lifeImages = new ArrayList<>();
        bulletImages = new ArrayList<>();
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
                    updateBulletsAndLives(player.getBullets(), player.getLives()); // Actualizar la vista del contador de balas y vidas
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
                player.disparar(false); // Consumir una bala y una vida si falla
                updateBulletsAndLives(player.getBullets(), player.getLives()); // Actualizar la vista del contador de balas y vidas
            }
        });



        // Añadir imagen de fondo y su configuración.
        ImageView imageView = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/Background/background.png").toString()));
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);
        pane.getChildren().add(imageView);

        // Añadir rectángulos para vidas y puntaje.
        Rectangle rectangle = new Rectangle(125, 360, 400, 30); // (x, y, width, height)
        rectangle.setFill(Color.BLACK);
        rectangle.setStroke(Color.GREEN);
        rectangle.setStrokeWidth(2);
        pane.getChildren().add(rectangle);

        // Rectángulo para balas.
        Rectangle rectangleBults = new Rectangle(15, 360, 60, 30); // (x, y, width, height)
        rectangleBults.setFill(Color.BLACK);
        rectangleBults.setStroke(Color.GREEN);
        rectangleBults.setStrokeWidth(2);
        pane.getChildren().add(rectangleBults);

        // Añadir vidas.
        ImageView life1 = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/life/59995589-la-muestra-del-corazón-del-pixel-imagen-del-amor-icono-de-color-blanco-sobre-fondo-negro.jpg").toString()));
        ImageView life2 = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/life/59995589-la-muestra-del-corazón-del-pixel-imagen-del-amor-icono-de-color-blanco-sobre-fondo-negro.jpg").toString()));
        ImageView life3 = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/life/59995589-la-muestra-del-corazón-del-pixel-imagen-del-amor-icono-de-color-blanco-sobre-fondo-negro.jpg").toString()));

        life1.setFitWidth(10);
        life1.setFitHeight(15);
        life2.setFitWidth(10);
        life2.setFitHeight(15);
        life3.setFitWidth(10);
        life3.setFitHeight(15);

        lifeImages.add(life1);
        lifeImages.add(life2);
        lifeImages.add(life3);

        // Posicionar las vidas dentro del rectángulo.
        double rectXLife = rectangle.localToScene(rectangle.getBoundsInLocal()).getMinX();
        double rectYLife = rectangle.localToScene(rectangle.getBoundsInLocal()).getMinY();

        double lifeSpacing = 10; // Espacio entre las vidas

        life1.setLayoutX(rectXLife + 10);
        life1.setLayoutY(rectYLife + (rectangle.getHeight() - life1.getFitHeight()) / 2);

        life2.setLayoutX(life1.getLayoutX() + life1.getFitWidth() + lifeSpacing);
        life2.setLayoutY(rectYLife + (rectangle.getHeight() - life2.getFitHeight()) / 2);

        life3.setLayoutX(life2.getLayoutX() + life2.getFitWidth() + lifeSpacing);
        life3.setLayoutY(rectYLife + (rectangle.getHeight() - life3.getFitHeight()) / 2);

        pane.getChildren().add(life1);
        pane.getChildren().add(life2);
        pane.getChildren().add(life3);

        // Añadir balas.
        ImageView imagBulet1 = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/Bulet/WhatsApp Image 2024-05-15 at 09.19.00_cbb595db (1).jpg").toString()));
        ImageView imagBulet2 = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/Bulet/WhatsApp Image 2024-05-15 at 09.19.00_cbb595db (1).jpg").toString()));
        ImageView imagBulet3 = new ImageView(new Image(GameView.class.getResource("/icesi/edu/co/duckhunt/images/Bulet/WhatsApp Image 2024-05-15 at 09.19.00_cbb595db (1).jpg").toString()));


        // Ajustar dimensiones de las balas.
        imagBulet1.setFitWidth(10);
        imagBulet1.setFitHeight(15);
        imagBulet2.setFitWidth(10);
        imagBulet2.setFitHeight(15);
        imagBulet3.setFitWidth(10);
        imagBulet3.setFitHeight(15);

        bulletImages.add(imagBulet1);
        bulletImages.add(imagBulet2);
        bulletImages.add(imagBulet3);

        // Posicionar la primera bala dentro del rectángulo.
        double rectX = rectangleBults.localToScene(rectangleBults.getBoundsInLocal()).getMinX();
        double rectY = rectangleBults.localToScene(rectangleBults.getBoundsInLocal()).getMinY();

        imagBulet1.setLayoutX(rectX + (rectangleBults.getWidth() - imagBulet1.getFitWidth()) / 2);
        imagBulet1.setLayoutY(rectY + (rectangleBults.getHeight() - imagBulet1.getFitHeight()) / 2);

        // Posicionar la segunda bala al lado de la primera bala.
        double spacing = 10; // Espacio entre las balas.
        imagBulet2.setLayoutX(imagBulet1.getLayoutX() + imagBulet1.getFitWidth() + spacing);
        imagBulet2.setLayoutY(rectY + (rectangleBults.getHeight() - imagBulet2.getFitHeight()) / 2);

        // Posicionar la tercera bala al lado de la primera bala.
        double spacing3 = 10; // Espacio entre las balas.
        imagBulet3.setLayoutX(imagBulet1.getLayoutX() - imagBulet1.getFitWidth() - spacing3);
        imagBulet3.setLayoutY(rectY + (rectangleBults.getHeight() - imagBulet3.getFitHeight()) / 2);

        pane.getChildren().add(imagBulet1);
        pane.getChildren().add(imagBulet2);
        pane.getChildren().add(imagBulet3);

        controller.setUpdateView(this::updateDucks);
    }

    @Override
    public void update() {
        Platform.runLater(this::updateDucks);
    }

    public void updateBulletsAndLives(int bullets, int lives) {
        // Recorremos las balas y las ocultamos según la cantidad actual de balas restantes.
        for (int i = 0; i < bulletImages.size(); i++) {
            if (i < bullets) {
                bulletImages.get(i).setVisible(true); // Mostramos la bala si es una de las balas restantes.
            } else {
                bulletImages.get(i).setVisible(false); // Ocultamos la bala si ya se ha disparado.
            }
        }

        // Recorremos las vidas y las ocultamos según la cantidad actual de vidas restantes.
        for (int i = 0; i < lifeImages.size(); i++) {
            if (i < lives) {
                lifeImages.get(i).setVisible(true); // Mostramos la vida si es una de las vidas restantes.
            } else {
                lifeImages.get(i).setVisible(false); // Ocultamos la vida si ya se ha perdido.
            }
        }

        // Verificar si el jugador ha perdido todas sus vidas
        if (lives <= 0) {
            System.out.println("Game Over");
            resetGame(); // Reiniciar el juego
        }
    }

    // Método para manejar la muerte de un pato
    private void handleDuckKill() {
        if (!isGameFinished) {
            killDucks++;
            // Verificar si se ha alcanzado la victoria
            checkForVictory();
        }
    }

    public void resetGame() {
        // Restablecer la posición de los patos
        List<Duck> ducks = GameController.getDucks();
        for (Duck duck : ducks) {
            duck.resetPosition();
        }

        // Restablecer las balas y las vidas del jugador
        player.resetBulletAndLives();

        // Restablecer las imágenes de balas y vidas
        resetBulletAndLifeImages(); // Nuevo método para restablecer las imágenes

        // Actualizar la vista de las balas y vidas
        updateBulletsAndLives(player.getBullets(), player.getLives());
    }

    private void resetBulletAndLifeImages() {
        // Restablecer la imagen de las balas
        for (ImageView bulletImage : bulletImages) {
            bulletImage.setVisible(true);
        }

        // Restablecer la imagen de las vidas
        for (ImageView lifeImage : lifeImages) {
            lifeImage.setVisible(true);
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

        // Verificar si se hizo clic en un pato.
        duckPane.setOnMouseClicked(event -> {
            if (duck.isClickable()) {
                duck.kill();
                handleDuckKill(); // Llama al método para manejar la eliminación del pato
                update(); // Llama a update después de cada clic en el pato para verificar la condición de victoria
            }
        });

        checkForVictory(); // Llama a checkForVictory después de actualizar los patos
    }

    // Método para verificar si se ha alcanzado la victoria
    private void checkForVictory() {
        if (!isGameFinished && killDucks >= numPatosToKill) {
            // Mostrar la imagen de victoria y el botón de reinicio si se ha alcanzado el número requerido de patos eliminados
            gameOverImageView.setVisible(true);
            restartButton.setVisible(true);
            isGameFinished = true;
        }
    }

    public Pane getPane() {
        return pane;
    }
}
