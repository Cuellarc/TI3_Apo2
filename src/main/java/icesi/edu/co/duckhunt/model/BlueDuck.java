package icesi.edu.co.duckhunt.model;

import icesi.edu.co.duckhunt.model.moveFunctions.MoveFunction;

public class BlueDuck extends Duck{
    public static final String BASE_PATH = "icesi/edu/co/duckhunt/images/sprites/BlackDuck/blackduck";
    public BlueDuck(int x, int y, MoveFunction moveFunction) {
        super(x, y, moveFunction);
    }
}
