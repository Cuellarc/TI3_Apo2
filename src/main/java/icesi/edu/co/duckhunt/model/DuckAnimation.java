package icesi.edu.co.duckhunt.model;

import icesi.edu.co.duckhunt.controllers.GameController;

import java.util.List;

public class DuckAnimation extends Thread{
    private List<Duck> ducks;
    private GameController gameController;

    public DuckAnimation(List<Duck> ducks, GameController gameController){
        this.ducks = ducks;
        this.gameController = gameController;
    }

    @Override
    public void run(){
        while(true){
            for(int i = 0; i < ducks.size(); i++){
                ducks.get(i).move();
            }
            gameController.notifyView();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
