module com.example.client_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;


    opens main to javafx.fxml;
    exports main;
    exports controllers;
    opens controllers to javafx.fxml;
    opens model to javafx.base;
}