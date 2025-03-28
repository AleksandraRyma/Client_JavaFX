module com.example.client_javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens main to javafx.fxml;
    exports main;
    exports controllers;
    opens controllers to javafx.fxml;
}