package icesi.edu.co.duckhunt.model;

import icesi.edu.co.duckhunt.controllers.DuckController;
import icesi.edu.co.duckhunt.model.moveFunctions.MoveFunction;

public abstract class Duck {
    public static final int WIDTH = 81;
    public static final int HEIGHT = 75;
    public static final int DELTA = 5;
    public static final int IMAGE_COUNT = 3;
    public static final String IMAGE_EXTENSION = ".png";
    private double x;
    private double y;
    private int imageIndex;
    private String imagePath;
    private MoveFunction moveFunction;

    public Duck(int x, int y, MoveFunction moveFunction){
        this.x = x;
        this.y = y;
        this.imageIndex = 0;
        this.moveFunction = moveFunction;
    }

    public void changeImage(){
        imageIndex = (imageIndex + 1) % IMAGE_COUNT;

    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public String getImagePath(){
        return imagePath;
    }

    public void move(){
        changeImage();
        x = (x + DELTA) % DuckController.WIDTH;
        y = moveFunction.move(x);
    }
}
