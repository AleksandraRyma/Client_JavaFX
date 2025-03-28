package main;

import ClientWork.Client;
import ClientWork.Connect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/authorization.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Connect.client = new Client("127.0.0.2", "9006");
        launch();
    }

}