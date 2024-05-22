package icesi.edu.co.duckhunt.controllers;

import icesi.edu.co.duckhunt.model.*;
import icesi.edu.co.duckhunt.view.GameView;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    private static GameController instance;
    private UpdateView updateView;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private static List<Duck> ducks;
    private DuckAnimation duckAnimation;


    private GameController(){
        this.ducks = new ArrayList<>();
        generateDucks();
        duckAnimation = new DuckAnimation(ducks, this);
        duckAnimation.start();
    }

    //Actualizar vista
    public void notifyView(){
        if(updateView != null){
            updateView.update();
        }
    }

    public void generateDucks(){
        Random random = new Random();

        ducks.add(new Duck(random.nextInt(WIDTH), HEIGHT-100, "blackduck"));
        ducks.get(0).setDirection();

        ducks.add(new Duck(random.nextInt(WIDTH), HEIGHT-100, "blueduck"));
        ducks.get(1).setDirection();

        ducks.add(new Duck(random.nextInt(WIDTH-20), HEIGHT-100, "brownduck"));
        ducks.get(2).setDirection();

    }

    //Singleton
    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        return instance;
    }

    //Definir el actualizador de vista.
    public void setUpdateView(UpdateView updateView){
        this.updateView = updateView;
    }

    public static List<Duck> getDucks() {
        return ducks;
    }


}
