package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import checks.DialogAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class adminHomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button accessEmployeeBtn;

    @FXML
    private Button addEmployeeBtn;

    @FXML
    private Button deleteEmployeeBtn;

    @FXML
    private Button editEmployeeBtn;

    @FXML
    private Button goBackBtn;

    @FXML
    private Button roleEmployeeBtn;

    @FXML
    private Button seeEmployeeBtn;

    @FXML
    void returnAuthorization(ActionEvent event) {
        boolean confirmationResult = DialogAlert.showConfirmationDialog("Подтверждение", "Выход из системы", "Вы действительно хотите выйти из системы?");

        if (confirmationResult) {
            goBackBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/authorization.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    void initialize() {


    }

}

