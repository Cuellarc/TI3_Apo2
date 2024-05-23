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
    private static Duck actualDuck;
    private static int actualDuckIndex;
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

    //Generar 3 patos, uno de cada color en posicion X random, y posicion Y abajo detras del pasto.
    public void generateDucks(){
        Random random = new Random();

        ducks.add(new Duck(random.nextInt(WIDTH), HEIGHT-100, "blackduck"));
        ducks.get(0).setDirection();

        ducks.add(new Duck(random.nextInt(WIDTH), HEIGHT-100, "blueduck"));
        ducks.get(1).setDirection();

        ducks.add(new Duck(random.nextInt(WIDTH-20), HEIGHT-100, "brownduck"));
        ducks.get(2).setDirection();

        actualDuck = ducks.get(random.nextInt(3));
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

    //Enviar la lista de patos creados.
    public static List<Duck> getDucks() {
        return ducks;
    }

    public static void setActualDuck(){
        Random random = new Random();
        int numb = random.nextInt(3);
        actualDuck = ducks.get(numb);
        actualDuckIndex = numb;
    }

    public Duck getActualDuck() {
        return actualDuck;
    }

    public int getActualDuckIndex() {
        return actualDuckIndex;
    }
}
