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
    private DuckAnimation duckAnimation;

    private GameController(){
        this.ducks = new ArrayList<>();
        int duckCount = 6;
        generateDucks(duckCount);
        duckAnimation = new DuckAnimation(ducks, this);
        duckAnimation.start();
    }

    //Actualizar vista
    public void notifyView(){
        if(updateView != null){
            updateView.update();
        }
    }

    public void generateDucks(int numb){
        Random random = new Random();

        for(int i = 0; i < numb; i++){
            int randomNumb = random.nextInt(3);
            switch (randomNumb){
                case 0:
                    ducks.add(new BlackDuck(random.nextInt(WIDTH), HEIGHT));
                break;

                case 1:
                    ducks.add(new BlueDuck(random.nextInt(WIDTH), HEIGHT));
                break;

                case 2:
                    ducks.add(new BrownDuck(random.nextInt(WIDTH), HEIGHT));
                break;
            }

        }
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
