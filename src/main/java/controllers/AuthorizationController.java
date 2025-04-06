package controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import ClientWork.Connect;
import checks.DialogAlert;
import experience.SessionManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Employee;

public class AuthorizationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authSigninButton;

    @FXML
    private Button goBack;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    void initialize() {

    }
    @FXML
    public void goOut(ActionEvent actionEvent) throws IOException {
        Platform.exit();
    }

    @FXML
            public void authorizationEmployee(ActionEvent actionEvent) throws IOException {
        Employee employee = new Employee();
        employee.setLogin(login_field.getText().trim());
        employee.setPassword(password_field.getText().trim());
        Connect.client.sendMessage("authorizationEmployee");
        Connect.client.sendObject(employee);
        System.out.println("Запись отправлена");


        Object response = Connect.client.readObject();

        if (response instanceof Employee) {
            Employee authenticatedEmployee = (Employee) response;
            System.out.println(authenticatedEmployee);
            System.out.println("Авторизация успешна! Роль: " + authenticatedEmployee.getRole_id());
            //
            authSigninButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            SessionManager.setCurrentUser(employee);
            SessionManager.setSessionEmpId(authenticatedEmployee.getEmployee_id());
            SessionManager.setSessionRoleId(authenticatedEmployee.getRole_id());
            if(authenticatedEmployee.getRole_id() == 1)
            {
                loader.setLocation(getClass().getResource("/adminHome.fxml"));
            }
            else if(authenticatedEmployee.getRole_id() == 2)
            {
                loader.setLocation(getClass().getResource("/bossHome.fxml"));
            }
            else if(authenticatedEmployee.getRole_id() == 3)
            {
                loader.setLocation(getClass().getResource("/accountantHome.fxml"));
            }
            else if(authenticatedEmployee.getRole_id() == 4)
            {
                loader.setLocation(getClass().getResource("/employeeHome.fxml"));
            }

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            //
        } else if (response instanceof String) {
            System.out.println(response);

                DialogAlert.showAlertAuthorisationErrow(response);
        }


    }


    @FXML
    public void registration(ActionEvent actionEvent) throws IOException {
        loginSignUpButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/registration_employee.fxml"));
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
