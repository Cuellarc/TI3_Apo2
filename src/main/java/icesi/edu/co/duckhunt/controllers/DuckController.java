package icesi.edu.co.duckhunt.controllers;

import icesi.edu.co.duckhunt.model.*;
import icesi.edu.co.duckhunt.model.moveFunctions.Move;
import icesi.edu.co.duckhunt.model.moveFunctions.MoveFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DuckController {
    private static DuckController instance;
    private UpdateView updateView;
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    private List<Duck> ducks;
    private DuckAnimation duckAnimation;

    private DuckController(){
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
                    ducks.add(new BlackDuck(random.nextInt(WIDTH), HEIGHT, speed));
                break;

                case 1:
                    ducks.add(new BlueDuck(random.nextInt(WIDTH), HEIGHT, speed));
                break;

                case 2:
                    ducks.add(new BrownDuck(random.nextInt(WIDTH), HEIGHT, speed));
                break;
            }

        }
    }

    //Singleton
    public static DuckController getInstance(){
        if(instance == null){
            instance = new DuckController();
        }
        return instance;
    }

    //Definir el actualizador de vista.
    public void setUpdateView(UpdateView updateView){
        this.updateView = updateView;
    }
}
