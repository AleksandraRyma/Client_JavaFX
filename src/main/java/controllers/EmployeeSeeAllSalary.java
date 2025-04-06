package controllers;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ClientWork.Connect;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import model.Salary;
import java.util.ArrayList;
import java.util.List;

public class EmployeeSeeAllSalary {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button applyBtn;

    @FXML
    private TableColumn<Salary, Double> bonus_cl;

    @FXML
    private DatePicker byDate;

    @FXML
    private TableColumn<Salary, String> date_cl;

    @FXML
    private TableColumn<Salary, Integer> employy_id_cl;

    @FXML
    private DatePicker fromDate;

    @FXML
    private Button goBackBtn;

    @FXML
    private TableColumn<Salary, Double> net_salary_cl;

    @FXML
    private AnchorPane scrool_panel;

    @FXML
    private TableView<Salary> tableSeeUserAdmin;

    @FXML
    private TableColumn<Salary, Double> tax_cl;


    @FXML
    void returnMainMenuAdmin(ActionEvent event) {
        goBackBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
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
        // Предполагается, что пользователь уже аутентифицирован и в сессии сохранен его ID
        int employeeId = SessionManager.getSessionEmpId();
        Connect.client.sendMessage("employeeSeeAllSalary");
        Connect.client.sendMessage(String.valueOf(employeeId));
        System.out.println(employeeId);

        List<Salary> salaries = (List<Salary>) Connect.client.readObject();

        configureTableColumns();
        loadEmployeeSalaries(salaries);
    }

    private void configureTableColumns() {
        employy_id_cl.setCellValueFactory(cellData -> {
            // Получаем индекс строки и увеличиваем его на 1 для отображения порядкового номера
            int index = tableSeeUserAdmin.getItems().indexOf(cellData.getValue());
            return new SimpleIntegerProperty(index + 1).asObject();
        });
        date_cl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPayment_date().toString())); // Предполагается, что это LocalDate
        net_salary_cl.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getNet_salary()).asObject());
        tax_cl.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getTax_percentage()).asObject());
        bonus_cl.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getBonus_sum()).asObject());
    }

    private void loadEmployeeSalaries(List<Salary> salaries) {
        ObservableList<Salary> observableSalaries = FXCollections.observableArrayList(salaries);
        tableSeeUserAdmin.setItems(observableSalaries);
    }

    // Функция для фильтрации по дате
    @FXML
    void applyFilter(ActionEvent event) {
        LocalDate startDate = fromDate.getValue(); // Получаем начальную дату фильтрации
        LocalDate endDate = byDate.getValue(); // Получаем конечную дату фильтрации

        // Получаем текущий список зарплат
        ObservableList<Salary> currentSalaries = tableSeeUserAdmin.getItems();
        List<Salary> filteredSalaries = new ArrayList<>();

        for (Salary salary : currentSalaries) {
            // Преобразуем строку payment_date в LocalDate
            LocalDate paymentDate = null;
            if (salary.getPayment_date() != null) {
                try {
                    // Параметры формата даты можно настроить в зависимости от ваших требований
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    paymentDate = LocalDate.parse(salary.getPayment_date(), formatter);
                } catch (DateTimeParseException e) {
                    e.printStackTrace(); // Обработка ошибок преобразования (при необходимости)
                    continue; // Пропускаем зарплату в случае некорректного формата даты
                }
            }
            // Фильтрация по дате
            if ((startDate == null || (paymentDate != null && !paymentDate.isBefore(startDate))) &&
                    (endDate == null || (paymentDate != null && !paymentDate.isAfter(endDate)))) {
                filteredSalaries.add(salary);
            }
        }

        // Обновляем таблицу с отфильтрованными данными
        ObservableList<Salary> observableFilteredSalaries = FXCollections.observableArrayList(filteredSalaries);
        tableSeeUserAdmin.setItems(observableFilteredSalaries);

    }
}
