package icesi.edu.co.duckhunt.model;

public class Player {
    private int life;
    private int points;
    private int bullets;

    public Player() {
        this.life = 3;
        this.points = 0;
        this.bullets = 3;
    }

    // Método para simular disparo a un objetivo
    public void disparar(boolean acierto) {
        if (acierto) {
            // No reducimos balas ni vidas si acertamos.
        } else {
            if (bullets > 0) {
                bullets--; // Reducimos una bala si fallamos.
            }
            if (life > 0) {
                life--; // Reducimos una vida si fallamos.
            }
        }
    }


    public int getLives() {
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
