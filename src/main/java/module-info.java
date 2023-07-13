module lk.ijse.play_tech {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports lk.ijse.play_tech;
    opens lk.ijse.play_tech to javafx.fxml;
    exports lk.ijse.play_tech.controller;
    opens lk.ijse.play_tech.controller to javafx.fxml;
}