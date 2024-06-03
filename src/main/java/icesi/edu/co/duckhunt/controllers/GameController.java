package icesi.edu.co.duckhunt.controllers;

import icesi.edu.co.duckhunt.model.*;
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
    private int gameSpeed;

    private Player player;

    private GameController(){
        this.player = new Player();
        this.ducks = new ArrayList<>();
    }

    public void start(){
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

        ducks.add(new Duck(random.nextInt(WIDTH), HEIGHT-100, "blackduck", gameSpeed));
        ducks.get(0).setDirection();

        ducks.add(new Duck(random.nextInt(WIDTH), HEIGHT-100, "blueduck", gameSpeed));
        ducks.get(1).setDirection();

        ducks.add(new Duck(random.nextInt(WIDTH), HEIGHT-100, "brownduck", gameSpeed));
        ducks.get(2).setDirection();

        setActualDuck();
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
        actualDuckIndex = numb;
    }

    public Duck getActualDuck() {
        return actualDuck;
    }

    public int getActualDuckIndex() {
        return actualDuckIndex;
    }

    public Player getPlayer() {
        return player;
    }

    public void handleDuckClick(boolean acierto) {
        player.disparar(acierto);
        notifyView();
    }

    public void setGameSpeed(int speed){
        this.gameSpeed = speed;
    }
}
