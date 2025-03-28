package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import ClientWork.Connect;
import checks.DialogAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Employee;
import checks.DialogAlert;


import  java.time.LocalDate;
import java.util.regex.Pattern;


public class RegistrationControllerEmp {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Zа-яА-ЯёЁ]+$");
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email_registration;

    @FXML
    private Button goBack;

    @FXML
    private DatePicker hire_date_employee;

    @FXML
    private CheckBox is_union_member_employee;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private TextField login_field_employee;

    @FXML
    private TextField name_employee;

    @FXML
    private PasswordField password_field_employee;

    @FXML
    private PasswordField password_field_r_employee;

    @FXML
    private TextField surname_employee;
    @FXML
    private Label errowLabel;

    @FXML
    void initialize() {

    }
    @FXML
    public void registrationSuccesswd(ActionEvent actionEvent) throws IOException {
        Employee employee = new Employee();
        employee.setFirst_name(name_employee.getText());
        employee.setLast_name(surname_employee.getText());

        LocalDate hireDate = hire_date_employee.getValue();
        String hireDateStr = (hireDate != null) ? hireDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "Не указано";

        employee.setHire_date(hireDateStr);
        String isUnionMemberStr = is_union_member_employee.isSelected() ? "Yes" : "No";
        employee.setIs_union_member(isUnionMemberStr);
        employee.setEmail(email_registration.getText());
        employee.setLogin(login_field_employee.getText());
        employee.setPassword(password_field_employee.getText());

        String password = password_field_employee.getText();
        String confirmPassword = password_field_r_employee.getText();

        if (!isValidName(name_employee.getText())) {
            DialogAlert.showAlertIsValidName("Ошибка", "Имя должно содержать только буквы!");
            return;
        }

        if (!isValidName(surname_employee.getText())) {
            DialogAlert.showAlertIsValidName("Ошибка", "Фамилия должна содержать только буквы!");
            return;
        }

        if (password.length() < 4) {
           DialogAlert.showAlertShortPassword();
           return;
        }

        if (!password.equals(confirmPassword)) {
            DialogAlert.samePasswordsAlert();
            return;
        }

        Connect.client.sendMessage("registrationEmployee");
        Connect.client.sendObject(employee);
        System.out.println("Запись отправлена");

        Object response = Connect.client.readObject();

        if (response instanceof Employee) {
            System.out.println("Регистрация прошла успешно");
            loginSignUpButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/successful_reg.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } else if (response instanceof String) {
            System.out.println(response);
            DialogAlert.showAlertExistLoginEmployee(response);
        }
    }
    @FXML
    public void returnAuthorization(ActionEvent actionEvent) throws IOException {
        goBack.getScene().getWindow().hide();
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

    private boolean isValidName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }


}
