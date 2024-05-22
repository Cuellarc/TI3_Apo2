package icesi.edu.co.duckhunt.model;

import icesi.edu.co.duckhunt.controllers.GameController;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DuckAnimation extends Thread{
    private List<Duck> ducks;
    private int currentDuck;
    private GameController gameController;
    private ScheduledExecutorService scheduler;
    private Duck actualDuck;


    public DuckAnimation(List<Duck> ducks, GameController gameController){
        this.ducks = ducks;
        this.gameController = gameController;
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.actualDuck = gameController.getActualDuck();
    }

    @Override
    public void run(){

        while(true){
            //for(int i = 0; i < ducks.size(); i++){
            actualDuck.move();
                if(!actualDuck.isClickable()){
                    //int finalI = i;
                    scheduler.schedule(() -> actualDuck.killMove(), 1, TimeUnit.SECONDS);
                }
            //}
            gameController.notifyView();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void setCurrentDuck(int currentDuck) {
        this.currentDuck = currentDuck;
    }

    public void schedulerShutdown(){
        scheduler.shutdown();
    }
}
