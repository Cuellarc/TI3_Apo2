package icesi.edu.co.duckhunt.model;

import icesi.edu.co.duckhunt.controllers.DuckController;
import icesi.edu.co.duckhunt.model.moveFunctions.MoveFunction;
import javafx.geometry.Point2D;

public abstract class Duck {
    public static final int WIDTH = 81;
    public static final int HEIGHT = 75;
    public static final int IMAGE_COUNT = 3;
    public static final String IMAGE_EXTENSION = ".png";
    private int imageIndex;
    private String imagePath;

    public static final int DELTA = 5;
    private double x;
    private double y;
    private double m;
    private double b;
    private int mode;
    private MoveFunction moveFunction;

    public Duck(int x, int y, Point2D direction){
        this.x = x;
        this.y = y;
        this.imageIndex = 0;
        this.moveFunction = moveFunction;
    }

    public void changeImage(){
        imageIndex = (imageIndex + 1) % IMAGE_COUNT;

    }

    public void move(){
        //if(death){
        //    return;
        //}

        switch (mode){
            case Board.MOVE_LEFT: //Si el objeto se esta moviendo a la izquierda.
                x -= DELTA; //Resto en X la velocidad (DELTA).
            break;

            case Board.MOVE_RIGHT: //Si el objeto se esta moviendo a la derecha.
                x += DELTA; //Sumo en X la velocidad (DELTA).
            break;
        }
        y = (double) (x * direction.getX() + direction.getY());

        if(x + area >= Board.WIDTH) { //Si estamos por fuera del area por la derecha.
            setMode();                //Cambiar de modo (MOVE_RIGHT to MOVE_LEFT)
        }
        else if (x <= 0) {            //Si estamos por fuera del area por la izquierda.
            setMode();                //Cambiar de modo (MOVE_LEFT to MOVE_RIGHT)
        }

        if(y + area >= Board.HEIGHT || y <= 0){ //Si estamos por fuera del area por arriba o abajo.
            setEquation();                      //Actualizar la funcion.
        }
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
}
