module icesi.edu.co.duckhunt {
    requires javafx.controls;
    requires javafx.fxml;


    opens icesi.edu.co.duckhunt to javafx.fxml;
    exports icesi.edu.co.duckhunt;
    exports icesi.edu.co.duckhunt.controllers;
    opens icesi.edu.co.duckhunt.controllers to javafx.fxml;
    //opens icesi.edu.co.duckhunt.images.sprites.blueduck;
    //opens icesi.edu.co.duckhunt.images.sprites.brownduck;
    //opens icesi.edu.co.duckhunt.images.sprites.blackduck;
}