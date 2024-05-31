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
        if (bullets > 0) {
            if (acierto) {
                // Si acierta, no se consumen balas y se suman 50 puntos
                points += 50;
            } else {
                // Si falla, se consume una bala
                bullets--;
            }
        } else {
            System.out.println("No tienes más balas.");
        }

        // Verificar si el jugador aún tiene vidas
        if (life <= 0) {
            System.out.println("Has perdido todas tus vidas.");
        }
    }

    // Método para simular disparo fallido
    public void dispararFallido() {
        if (bullets > 0) {
            // Si falla, se consume una bala
            bullets--;
        } else {
            System.out.println("No tienes más balas.");
        }
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
