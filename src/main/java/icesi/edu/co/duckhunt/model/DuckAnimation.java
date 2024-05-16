package icesi.edu.co.duckhunt.model;

import icesi.edu.co.duckhunt.controllers.DuckController;

import java.util.List;

public class DuckAnimation extends Thread{
    private List<Duck> ducks;
    private DuckController duckController;

    public DuckAnimation(List<Duck> ducks, DuckController duckController){
        this.ducks = ducks;
        this.duckController = duckController;
    }

    @Override
    public void run(){
        while(true){
            for(int i = 0; i < ducks.size(); i++){
                ducks.get(i).move();
            }
            duckController.notifyView();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
