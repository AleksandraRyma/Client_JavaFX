package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import ClientWork.Connect;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;

public class AdminSeeUserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Employee, String> access_cl;

    @FXML
    private TableColumn<Employee, String> email_cl;

    @FXML
    private TableColumn<Employee, Integer> employy_id_cl;

    @FXML
    private TableColumn<Employee, String> first_name_cl;

    @FXML
    private Button goBackBtn;

    @FXML
    private TableColumn<Employee, String> hire_date_id_cl;

    @FXML
    private TableColumn<Employee, String> last_name_cl;

    @FXML
    private TableColumn<Employee, String> login_cl;

    @FXML
    private TableColumn<Employee, String> password_id_cl;

    @FXML
    private TableColumn<Employee, String> position_cl;

    @FXML
    private TableColumn<Employee, String> role_cl;

    @FXML
    private TableView<Employee> tableSeeUserAdmin;

    @FXML
    private TableColumn<Employee, String> union_member_id_cl;

    @FXML
    private AnchorPane scrool_panel;

    @FXML
    void returnMainMenuAdmin(ActionEvent event) {
        goBackBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/adminHome.fxml"));
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
    void initialize() throws ClassNotFoundException {
        Connect.client.sendMessage("adminSeeUser");
        loadTableData();
    }
    private void loadTableData() throws ClassNotFoundException {
        // Получение ответа от сервера
        List<Employee> employeeList = (List<Employee>) Connect.client.readObject();
        // Заполнение TableView данными
        ObservableList<Employee> observableList = FXCollections.observableArrayList(employeeList);
        tableSeeUserAdmin.setItems(observableList);

        // Настройка столбцов, если не была ранее настроена
        employy_id_cl.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEmployee_id()).asObject());
        first_name_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name()));
        last_name_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLast_name()));
        login_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLogin()));
        password_id_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        hire_date_id_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHire_date()));
        email_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        access_cl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAccess() == 0 ? "Доступ закрыт" : "Доступ открыт")); // Условие для доступа
        position_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPosition()));
        role_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));
        union_member_id_cl.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIs_union_member())));

    }

}
