package icesi.edu.co.duckhunt.model;

import icesi.edu.co.duckhunt.controllers.GameController;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DuckAnimation extends Thread{
    private List<Duck> ducks;
    private GameController gameController;
    private ScheduledExecutorService scheduler;


    public DuckAnimation(List<Duck> ducks, GameController gameController){
        this.ducks = ducks;
        this.gameController = gameController;
        this.scheduler = Executors.newScheduledThreadPool(1);

    }

    @Override
    public void run(){

        while(true){
            for(int i = 0; i < ducks.size(); i++){
                ducks.get(i).move();
                if(!ducks.get(i).isClickable()){
                    int finalI = i;
                    scheduler.schedule(() -> ducks.get(finalI).killMove(), 1, TimeUnit.SECONDS);
                }
            }
            gameController.notifyView();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void schedulerShutdown(){
        scheduler.shutdown();
    }
}
