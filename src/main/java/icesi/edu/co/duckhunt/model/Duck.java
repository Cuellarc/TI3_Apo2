package icesi.edu.co.duckhunt.model;

import icesi.edu.co.duckhunt.controllers.GameController;

import java.util.Random;

public class Duck {
    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int IMAGE_COUNT = 3;
    public static final String BASE_PATH = "/icesi/edu/co/duckhunt/images/sprites/";
    public static final String IMAGE_EXTENSION = ".png";
    private String colorExtension;
    private int imageIndex;
    private String imagePath;

    private int speed;
    private int x;
    private int y;
    private int directionX;
    private int directionY;
    private int positionX;
    private int positionY;
    private boolean clickable;
    private boolean dead;
    private GameController controller;


    public Duck(int x, int y, String color, int speed){
        this.positionX = x;
        this.positionY = y;
        this.imageIndex = 0;
        this.colorExtension = color;
        this.clickable = true;
        this.speed = speed;

        //Inicializacion de la direccion de la imagen.
        this.imagePath = BASE_PATH + colorExtension + "/" + colorExtension + "Right" + (imageIndex + 1) + IMAGE_EXTENSION;
        System.out.println(imagePath);
    }

    //Cambiar la imagen del pato en base a hacia donde se mueve o si esta muerto.
    public void changeImage(){
        imageIndex = (imageIndex + 1) % IMAGE_COUNT;
        if(x > 0){
            imagePath = BASE_PATH + colorExtension + "/" + colorExtension + "Right" + (imageIndex + 1) + IMAGE_EXTENSION;
            if(y * directionY < -10 || y * directionY > 10 ) {
                imagePath = BASE_PATH + colorExtension + "/" + colorExtension + "UpR" + (imageIndex + 1) + IMAGE_EXTENSION;
            }
        }
        else if(x < 0){
            imagePath = BASE_PATH + colorExtension + "/" + colorExtension + "Left" + (imageIndex + 1) + IMAGE_EXTENSION;
            if(y * directionY < -10 || y * directionY > 10 ) {
                imagePath = BASE_PATH + colorExtension + "/" + colorExtension + "UpL" + (imageIndex + 1) + IMAGE_EXTENSION;
            }
        }
    }

    //Definir como se dirige el pato.
    public void setDirection(){
        Random random = new Random();
        x = random.nextBoolean() ? 1 : -1; //En X, positivo = derecha, negativo = izquierda.
        y = -1;                            //Y en negativo para que el pato inicie volando hacia arriba.
        directionX = random.nextInt(speed+1) + 5;
        directionY = random.nextInt(speed+1) + 5;
    }

    // En la clase Duck
    public void resetPosition() {
        // Restablecer la posición del pato a la posición inicial
        this.setX(positionX);
        this.setY(positionY);
        this.setDirection(); // Restablecer la dirección del pato
        this.clickable = true; // Restablecer la capacidad de hacer clic en el pato
    }


    //Codigo de movimiento de los patos.
    public void move(){
        if(clickable){
            changeImage();
            if(positionX <= 0 & x == -1|| positionX >= GameController.WIDTH-50 & x == 1){
                x *= -1; //Si choca con los bordes de izquierda o derecha, revertir direccion en X.
            }

            if(positionY <= 0 & y == -1 || positionY >= GameController.HEIGHT-50 & y == 1){
                y *= -1; //Si choca con los bordes de arriba o abajo, revertir direccion en Y.
            }

            positionX += (x * directionX);
            positionY += (y * directionY);
        }
    }

    //Recibir la posicion en X.
    public int getX(){
        return positionX;
    }

    //Recibir la posicion en Y.
    public int getY() {
        return positionY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //Recibir la imagen actual.
    public String getImagePath(){
        return imagePath;
    }

    //Matar al pato y sumar punto.
    public void kill() {
        if(clickable){
            this.clickable = false;
            imagePath = BASE_PATH + colorExtension + "/" + colorExtension + "Death" + 1 + IMAGE_EXTENSION;
            x = 0;
            y = 0;
            directionX = 0;
            directionY = 0;
            //killMove();
        }
    }

    //Movimiento del pato tras morir.
    public boolean killMove(){
        if(positionY <= GameController.HEIGHT-50){
            imagePath = BASE_PATH + colorExtension + "/" + colorExtension + "Death" + 2 + IMAGE_EXTENSION;

            positionY += 10;
            return true;
        }
        else {
            GameController.setActualDuck();
            this.clickable = true;
            this.dead = false;
            setDirection();
            return false;
        }
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }
}
