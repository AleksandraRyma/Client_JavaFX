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

public class accountantHomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button calculateSalaryBtn;

    @FXML
    private Button evaluateFinBtn;

    @FXML
    private Button seeEmployeeBtn;

    @FXML
    private Button goBackBtn;

    @FXML
    private Button reportBtn;

    @FXML
    private Button seeRecalculationBtn;

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
    @FXML
    public void goToSeeSortFilterFindEmployee(ActionEvent actionEvent) throws IOException {
        seeEmployeeBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/SeeSortFindController.fxml"));
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
    @FXML
    public void goToCalculateSalary(ActionEvent actionEvent) throws IOException {
        seeEmployeeBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/salaryCalculationController.fxml"));
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

    @FXML
    public void goToFinanceCharts(ActionEvent actionEvent) throws IOException {
        seeEmployeeBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/financeGraphicsController.fxml"));
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
