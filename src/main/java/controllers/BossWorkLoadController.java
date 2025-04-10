package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import ClientWork.Connect;
import checks.DialogAlert;
import experience.SessionManager;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import model.EmployeeLoad;

public class BossWorkLoadController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Integer> IDComboBox;

    @FXML
    private Label IDLavel;

    @FXML
    private DatePicker dateChoice;

    @FXML
    private Label dateLabel;

    @FXML
    private TableColumn<EmployeeLoad, String> dateMounthcl;

    @FXML
    private ComboBox<Integer> difficultComboBox;

    @FXML
    private Label difficultLabel;

    @FXML
    private TableColumn<EmployeeLoad, Integer> difficult_cl;

    @FXML
    private TableColumn<EmployeeLoad, Integer> employy_id_cl;

    @FXML
    private TableColumn<EmployeeLoad, String> first_name_cl;

    @FXML
    private Button goBackBtn;

    @FXML
    private Label hourworkedLabel;

    @FXML
    private TextField overtimeHourText;

    @FXML
    private Label overtimeLabel;

    @FXML
    private TableColumn<EmployeeLoad, Integer> overtime_hour_cl;

    @FXML
    private Button savebtn;

    @FXML
    private AnchorPane scrool_panel;

    @FXML
    private TableView<EmployeeLoad> tableSeeUserAdmin;

    @FXML
    private TextField workHourText;

    @FXML
    private TableColumn<EmployeeLoad, Integer> workHour_cl;
    @FXML
    private Button addBtn1;

    @FXML
    void resetFilters(ActionEvent event) {

    }

    @FXML
    void returnMainMenuAdmin(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        goBackBtn.getScene().getWindow().hide();
        loader.setLocation(getClass().getResource("/bossHome.fxml"));
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
        loadEmployeeIDs();
        Connect.client.sendMessage("seeWorkLoad");
        difficultComboBox.setItems(FXCollections.observableArrayList(
                1, 2, 3, 4, 5));

        List<EmployeeLoad> employeesLoad = (List<EmployeeLoad>) Connect.client.readObject();
        configureTableColumns();
        loadEmployeeData(employeesLoad);

    }
    private void configureTableColumns () {
        employy_id_cl.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEmployee().getEmployee_id()).asObject());
        first_name_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getFirst_name()));
        dateMounthcl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWork_load_date().toString()));
        workHour_cl.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHour_worked()).asObject());
        overtime_hour_cl.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOvertime_hour()).asObject());
        difficult_cl.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getDifficulty_rating()).asObject());
    }

    // Метод для загрузки данных сотрудников в таблицу
    private void loadEmployeeData(List<EmployeeLoad> employLoad) {
        ObservableList<EmployeeLoad> observableEmployees = FXCollections.observableArrayList(employLoad);
        tableSeeUserAdmin.setItems(observableEmployees);
    }

    private void loadEmployeeIDs() {
        Connect.client.sendMessage("getEmployeeIDs");
        List<Integer> employeeIDs = (List<Integer>) Connect.client.readObject();
        IDComboBox.setItems(FXCollections.observableArrayList(employeeIDs));
    }

    @FXML
    void collectDataFromFormToAdd(ActionEvent event) {
        if(IDComboBox.getSelectionModel().getSelectedItem() == null || difficultComboBox.getSelectionModel().getSelectedItem() == null
        || workHourText.getText().isEmpty() || overtimeHourText.getText().isEmpty() || difficultLabel.getText().isEmpty() ||
                dateChoice.getValue() == null){
            DialogAlert.showAlertInfo("Заполните пожалуйста все данные");
            return;
        }
        int employeeID = IDComboBox.getValue();



        int hourWorked;
        int overtimeHour;

        try {
            hourWorked = Integer.parseInt(workHourText.getText());
            overtimeHour = Integer.parseInt(overtimeHourText.getText());

            if (hourWorked < 0 || overtimeHour < 0) {
                DialogAlert.showAlertInfo("Часы работы и сверхурочные часы должны быть положительными числами.");
                return;
            }
        } catch (NumberFormatException e) {
            DialogAlert.showAlertInfo("Пожалуйста, введите целые числа для часов работы и сверхурочных часов.");
            return;
        }

        int difficultyRating = difficultComboBox.getValue();
        String workLoadDateString = dateChoice.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        EmployeeLoad newEmployeeLoad = new EmployeeLoad();
        newEmployeeLoad.setEmployee_id(employeeID);
        newEmployeeLoad.setHour_worked(hourWorked);
        newEmployeeLoad.setOvertime_hour(overtimeHour);
        newEmployeeLoad.setDifficulty_rating(difficultyRating);
        newEmployeeLoad.setWork_load_date(workLoadDateString);
        Connect.client.sendMessage("addNewEmployeeLoad");
        Connect.client.sendObject(newEmployeeLoad);

        Object response = Connect.client.readObject();

        if (response instanceof EmployeeLoad) {
            if(newEmployeeLoad.getEmployee() == null) {newEmployeeLoad.setEmployee(new Employee());
            }
            String firstName = ((EmployeeLoad) response).getEmployee().getFirst_name();
            newEmployeeLoad.getEmployee().setFirst_name(firstName);
            newEmployeeLoad.getEmployee().setEmployee_id(employeeID);
            updateTable(newEmployeeLoad);
            DialogAlert.showAlertInfo("Запись успешно добавлена");
            clearFields();
        }
        else if (response instanceof String) {
            System.out.println(response);
            DialogAlert.showAlertObj(response);

        }


    }

    private void updateTable(EmployeeLoad newEmployeeLoad) {
        ObservableList<EmployeeLoad> observableEmployees = tableSeeUserAdmin.getItems();
        observableEmployees.add(newEmployeeLoad); // Добавление нового объекта в таблицу
        tableSeeUserAdmin.setItems(observableEmployees);
    }
    private void clearFields() {
        IDComboBox.getSelectionModel().clearSelection();
        difficultComboBox.getSelectionModel().clearSelection();
        workHourText.clear();
        overtimeHourText.clear();
        dateChoice.setValue(null);
    }
}


