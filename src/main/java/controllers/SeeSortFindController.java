package controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import model.Salary;

public class SeeSortFindController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton RoleRadioBtn;

    @FXML
    private Button applyBtn;

    @FXML
    private RadioButton ascRadioBtn11;

    @FXML
    private TableColumn<Employee, Integer> employy_id_cl;

    @FXML
    private Label filterLabel;

    @FXML
    private Label filterLabel1;

    @FXML
    private Label filterLabel11;

    @FXML
    private Button findBtn;

    @FXML
    private TextField findFild;

    @FXML
    private TableColumn<Employee, String> first_name_cl;

    @FXML
    private TextField fromFilterValue;

    @FXML
    private Button goBackBtn;

    @FXML
    private TableColumn<Employee, String> last_name_cl;

    @FXML
    private ComboBox<String> parametreSortComboBox;

    @FXML
    private TableColumn<Employee, String> role_cl;

    @FXML
    private CheckBox salaryCheckBox;

    @FXML
    private TableColumn<Employee, Double> salary_cl;

    @FXML
    private AnchorPane scrool_panel;

    @FXML
    private Label sortLabel2;

    @FXML
    private TableView<Employee> tableSeeUserAdmin;

    @FXML
    private TextField toFilterValue;

    @FXML
    private CheckBox work_exp_checkBox;

    @FXML
    private TableColumn<Employee, Double> work_experience_cl;

    @FXML
    private Button delAllSettingsbtn;

    @FXML
    void returnMainMenuAdmin(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        goBackBtn.getScene().getWindow().hide();
        System.out.println(SessionManager.getSessionRoleId());
        if(SessionManager.getSessionRoleId()==2){
            loader.setLocation(getClass().getResource("/bossHome.fxml"));
        }
        else {
            loader.setLocation(getClass().getResource("/accountantHome.fxml"));
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
    }

    private void configureTableColumns() {
        employy_id_cl.setCellValueFactory(cellData -> {
            // Получаем индекс строки и увеличиваем его на 1 для отображения порядкового номера
            int index = tableSeeUserAdmin.getItems().indexOf(cellData.getValue());
            return new SimpleIntegerProperty(index + 1).asObject();
        });

        first_name_cl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLast_name()));

        last_name_cl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirst_name()));

        role_cl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getRole()));

        salary_cl.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getSalary().getNet_salary()).asObject());

        work_experience_cl.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getWork_experience()).asObject());
    }

    // Метод для загрузки данных сотрудников в таблицу
    private void loadEmployeeData(List<Employee> employees) {
        ObservableList<Employee> observableEmployees = FXCollections.observableArrayList(employees);
        tableSeeUserAdmin.setItems(observableEmployees);
    }

    @FXML
    void initialize() {
        Connect.client.sendMessage("seeSortFindFilter");
        parametreSortComboBox.setItems(FXCollections.observableArrayList(
                "ЗП по возрастанию",
                "ЗП по убыванию",
                "ФИО в алфавитном порядке"));
        setupRadioButtons();

        // Установка обработчиков для чекбоксов
        setupCheckBoxes();
        List<Employee> employees = (List<Employee>) Connect.client.readObject();
        configureTableColumns();
        loadEmployeeData(employees);
        parametreSortComboBox.setOnAction(event -> {sortDataBySalaryName();});
    }
    private void setupRadioButtons() {
        ToggleGroup group = new ToggleGroup();
        RoleRadioBtn.setToggleGroup(group);
        ascRadioBtn11.setToggleGroup(group);

        // Также можно добавить обработчики для действий при выборе
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) { // Проверяем, если новая кнопка выбрана
                RadioButton selectedRadioButton = (RadioButton) newValue;
                System.out.println("Выбрана кнопка: " + selectedRadioButton.getText());
            }
        });
    }

    private void setupCheckBoxes() {
        salaryCheckBox.setOnAction(event -> {
            if (salaryCheckBox.isSelected()) {
                work_exp_checkBox.setSelected(false);
            }
        });

        work_exp_checkBox.setOnAction(event -> {
            if (work_exp_checkBox.isSelected()) {
                salaryCheckBox.setSelected(false);
            }
        });
    }
    @FXML
    private void resetFilters() {
        // Сброс текстового поля
        Connect.client.sendMessage("seeSortFindFilter");
        findFild.clear();
        fromFilterValue.clear();
        toFilterValue.clear();
        RoleRadioBtn.getToggleGroup().selectToggle(null); // Сброс выбора радиокнопок

        // Сброс чекбоксов
        salaryCheckBox.setSelected(false);
        work_exp_checkBox.setSelected(false);

        List<Employee> employees = (List<Employee>) Connect.client.readObject();
        loadEmployeeData(employees);
    }
    @FXML
    void handleFindButtonClick(ActionEvent event) {
        String searchQuery = findFild.getText().trim(); // Получаем текст из текстового поля

        Toggle selectedToggle = RoleRadioBtn.getToggleGroup().getSelectedToggle();

        String filterType = null;

        if (selectedToggle != null) {
            if (selectedToggle.equals(RoleRadioBtn)) {
                filterType = "role";
            } else if (selectedToggle.equals(ascRadioBtn11)) {
                filterType = "name";
            }
        }
        if(findFild.getText().isEmpty())
        {
            DialogAlert.showAlertInfo("Заполните данными поле");
        }
        else if(selectedToggle == null)
        {
            DialogAlert.showAlertInfo("Выберете пожалуйста параметры поиска");
        }
        else {
            Connect.client.sendMessage("findEmployees");
            Connect.client.sendMessage(searchQuery);
            Connect.client.sendMessage(filterType);

            // Получаем отфильтрованный список сотрудников
            List<Employee> filteredEmployees = (List<Employee>) Connect.client.readObject();
            System.out.println(filteredEmployees);
            if(filteredEmployees.isEmpty()){
                DialogAlert.showAlertInfo("Записи не найдены");}
            else {
                updateloadEmployeeData(filteredEmployees);
            }

        }
    }

    private void updateloadEmployeeData(List<Employee> employees) {
        tableSeeUserAdmin.getItems().clear(); // Очистка предыдущих данных
        tableSeeUserAdmin.getItems().addAll(employees); // Добавляем новые данные

    }

    @FXML
    void sortDataBySalaryName(){
        ObservableList<Employee> currentEmployees = tableSeeUserAdmin.getItems();
        List<Employee> employeesToSort = FXCollections.observableArrayList(currentEmployees);
        String selectedSortParameter = parametreSortComboBox.getSelectionModel().getSelectedItem();
        if (selectedSortParameter != null) {
            switch (selectedSortParameter) {
                case "ЗП по возрастанию":
                    employeesToSort.sort((e1, e2) -> Double.compare(e1.getSalary().getNet_salary(), e2.getSalary().getNet_salary()));
                    break;
                case "ЗП по убыванию":
                    employeesToSort.sort((e1, e2) -> Double.compare(e2.getSalary().getNet_salary(), e1.getSalary().getNet_salary()));
                    break;
                case "ФИО в алфавитном порядке":
                    employeesToSort.sort((e1, e2) -> e1.getFirst_name().compareTo(e2.getFirst_name()));
                    break;
            }
        }
        updateloadEmployeeData(employeesToSort);
    }

    @FXML
    void filterDataBySalaryName(ActionEvent event){
        ObservableList<Employee> currentEmployees = tableSeeUserAdmin.getItems();
        List<Employee> employeesToFilter = FXCollections.observableArrayList(currentEmployees);

        String fromDataText = fromFilterValue.getText().trim();
        String toDataText = toFilterValue.getText().trim();
        if(!salaryCheckBox.isSelected() && !work_exp_checkBox.isSelected()){
            DialogAlert.showAlertInfo("Выберете параметр фильтрации");
        }
        if(fromDataText.isEmpty() || toDataText.isEmpty()){
            DialogAlert.showAlertInfo("Заполните пожалуйста текстовые поля");
        }
        double fromData = 0, toData = 0;
        if(!fromDataText.isEmpty())
        {
            fromData = Double.parseDouble(fromDataText);
        }
        if(!toDataText.isEmpty())
        {
            toData = Double.parseDouble(toDataText);
        }
        if (salaryCheckBox.isSelected()) {
            for (int i = employeesToFilter.size() - 1; i >= 0; i--) {
                Employee employee = employeesToFilter.get(i);
                double salary = employee.getSalary().getNet_salary();
                if (salary < fromData || salary > toData) {
                    employeesToFilter.remove(i);
                }
            }
        }

        if (work_exp_checkBox.isSelected()) {
            for (int i = employeesToFilter.size() - 1; i >= 0; i--) {
                Employee employee = employeesToFilter.get(i);
                double experience = employee.getWork_experience();
                if (experience < fromData || experience > toData) {
                    employeesToFilter.remove(i);
                }
            }
        }
        if (employeesToFilter.isEmpty()) {
            DialogAlert.showAlertInfo("Ничего не найдено");
        }
        else {
            updateloadEmployeeData(employeesToFilter);
        }
    }


}
