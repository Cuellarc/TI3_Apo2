package icesi.edu.co.duckhunt.model;

public class Player {
    private int life;
    private int points;
    private int bullets;

    public Player(){
        this.life = 3;
        this.points = 0;
        this.bullets = 5;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getBullets() {
        return bullets;
    }

    public void setBullets(int bullets) {
        this.bullets = bullets;
    }
}
