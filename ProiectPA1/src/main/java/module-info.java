module com.example.proiectpa1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.proiectpa1 to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
    exports main;
    opens main to javafx.fxml;
}