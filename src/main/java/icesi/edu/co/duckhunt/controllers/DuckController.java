package icesi.edu.co.duckhunt.controllers;

import icesi.edu.co.duckhunt.model.*;
import icesi.edu.co.duckhunt.model.moveFunctions.MoveFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DuckController {
    private static DuckController instance;
    private updateView updateView;

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

    public void notifyView(){
        if(updateView != null){
            updateView.update();
        }
    }

    public void generateDucks(int numb){
        Random random = new Random();
        MoveFunction[] moves = new MoveFunction[]{new };

        for(int i = 0; i < numb; i++){
            int randomNumb = random.nextInt(3);
            switch (randomNumb){
                case 0:
                    ducks.add(new BlackDuck(random.nextInt(WIDTH), random.nextInt(HEIGHT), moves[i%2]));
                break;

                case 1:
                    ducks.add(new BlueDuck(random.nextInt(WIDTH), random.nextInt(HEIGHT), moves[i%2]));
                break;

                case 2:
                    ducks.add(new BrownDuck(random.nextInt(WIDTH), random.nextInt(HEIGHT), moves[i%2]));
                break;
            }

        }
    }

    public static DuckController getInstance(){
        if(instance == null){
            instance = new DuckController();
        }
        return instance;
    }

    public void setUpdateView(updateView updateView){
        this.updateView = updateView;
    }
}
