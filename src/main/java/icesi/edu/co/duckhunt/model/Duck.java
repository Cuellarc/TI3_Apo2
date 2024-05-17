package icesi.edu.co.duckhunt.model;

public abstract class Duck {
    public static final int WIDTH = 81;
    public static final int HEIGHT = 75;
    public static final int IMAGE_COUNT = 3;
    public static final String IMAGE_EXTENSION = ".png";
    private int imageIndex;
    private String imagePath;
    private int id;

    public static int speed;
    private int x;
    private int y;


    public Duck(int x, int y){
        this.x = x;
        this.y = y;
        this.imageIndex = 0;

    }

    public void changeImage(){
        imageIndex = (imageIndex + 1) % IMAGE_COUNT;

    }

    public void kill(){

    }

    public void move(){

    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public String getImagePath(){
        return imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRadius() {
        return 70;
    }
}
