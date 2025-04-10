package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import ClientWork.Connect;
import checks.DialogAlert;
import experience.SessionManager;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import model.EmployeeLoad;
import model.Status;
import model.Vacation;

public class EmployeeVacationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addBtn1;

    @FXML
    private TableColumn<Vacation, String> endDatecl;

    @FXML
    private DatePicker endVacationDate;

    @FXML
    private Label endVacationLabel111;

    @FXML
    private Label firstNameLabel;

    @FXML
    private TextField firstNameText;

    @FXML
    private Button goBackBtn;

    @FXML
    private Label lastNameLabel1;

    @FXML
    private TextField lastNameText1;

    @FXML
    private TableColumn<Vacation, Integer> number_cl;

    @FXML
    private AnchorPane scrool_panel;

    @FXML
    private TableColumn<Vacation, String> startDatecl;

    @FXML
    private DatePicker startVacationDate;

    @FXML
    private Label startVacationLabel11;

    @FXML
    private TableColumn<Vacation, String> status_cl;

    @FXML
    private TableView<Vacation> tableSeeUserAdmin;


    @FXML
    void returnMainMenuAdmin(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        goBackBtn.getScene().getWindow().hide();
        loader.setLocation(getClass().getResource("/employeeHome.fxml"));
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
    void initialize() {

        configureTableColumns();
        loadEmployeeData();
        printNameInForm();
    }

    private void configureTableColumns() {
        number_cl.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(tableSeeUserAdmin.getItems().indexOf(cellData.getValue()) + 1).asObject()
        );

        startDatecl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStart_date().toString())
        );
        endDatecl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEnd_date().toString())
        );

        status_cl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus().getStatus_name())
        );
    }

    private void printNameInForm(){
        Connect.client.sendMessage("getNameEmployee");
        Connect.client.sendObject(SessionManager.getSessionEmpId());
        Employee employee = (Employee) Connect.client.readObject();
        firstNameText.setText(employee.getFirst_name());
        lastNameText1.setText(employee.getLast_name());
        System.out.println(employee.getLast_name());
    }
   private void loadEmployeeData() {
        Connect.client.sendMessage("getVacationList");
        Connect.client.sendObject(SessionManager.getSessionEmpId());

        List<Vacation> vacations = (List<Vacation>) Connect.client.readObject();
        System.out.println(vacations);
            if (vacations != null && !vacations.isEmpty()) {
                ObservableList<Vacation> observableEmployees = FXCollections.observableArrayList(vacations);
                tableSeeUserAdmin.setItems(observableEmployees);
            } else {
                DialogAlert.showAlertInfo("Список отпусков пуст.");
            }
    }

    @FXML
    void collectDataFromFormToAddVacation(ActionEvent event) {
        if (startVacationDate.getValue() == null || endVacationDate.getValue() == null) {
            DialogAlert.showAlertInfo("Заполните пожалуйста все данные о датах отпуска.");
            return;
        }

        String startDate = startVacationDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDate = endVacationDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Vacation newVacation = new Vacation();
        newVacation.setStart_date(startDate);
        newVacation.setEnd_date(endDate);
        newVacation.getEmployee().setEmployee_id(SessionManager.getSessionEmpId());

        Connect.client.sendMessage("addVacationRequest");
        Connect.client.sendObject(newVacation);
        Vacation vacations = (Vacation) Connect.client.readObject();
        updateTable(vacations);
        DialogAlert.showAlertInfo("Заявка на отпуск успешно добавлена.");
        clearFields();

    }

    private void updateTable(Vacation newVacation) {
        ObservableList<Vacation> observableVacations = tableSeeUserAdmin.getItems();
        observableVacations.add(newVacation); // Добавление нового объекта в таблицу
        tableSeeUserAdmin.setItems(observableVacations);
    }

    private void clearFields() {
        startVacationDate.setValue(null);
        endVacationDate.setValue(null);
    }

}


