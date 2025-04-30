package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import ClientWork.Connect;
import checks.DialogAlert;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.chart.NumberAxis;
import model.Employee;
import model.Salary;

public class FinanceGraphicsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private LineChart<Integer, Double> chartGrowsSalary;

    @FXML
    private BarChart<String, Number> salaryAllMonthChart;

    @FXML
    private Label salaryAllMonthLabel;


    @FXML
    private Button goBackBtn;

    @FXML
    private Label lineChartSalaryLabel;
    private static final String[] ROLE_NAMES = {
            "Администратор", // Role ID 1
            "Руководитель отдела", // Role ID 2
            "Бухгалтер", // Role ID 3
            "Сотрудник" // Role ID 4
    };
    @FXML
    void returnMainMenuAdmin(ActionEvent event) {
            FXMLLoader loader = new FXMLLoader();
            goBackBtn.getScene().getWindow().hide();
            loader.setLocation(getClass().getResource("/accountantHome.fxml"));
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
        getInfoForLinerChartSalary();
        getInfoForBarChartSalary();
    }

    void getInfoForLinerChartSalary(){
        Connect.client.sendMessage("getInfoForLinerChartSalary");
        List<Salary> salariesData = (List<Salary>) Connect.client.readObject();
        Platform.runLater(() -> buildChart(salariesData));
    }
    private void buildChart(List<Salary> salaryDataList) {
        chartGrowsSalary.getData().clear(); // Очистка существующих данных графика

        // Создание контейнера для хранения данных по ролям
        Map<Integer, XYChart.Series<Integer, Double>> seriesMap = new HashMap<>();

        // Инициализация серий для ролей
        for (int roleId = 1; roleId <= ROLE_NAMES.length; roleId++) {
            seriesMap.put(roleId, new XYChart.Series<>());
            seriesMap.get(roleId).setName(ROLE_NAMES[roleId - 1]); // Используем название роли
        }

        // Группировка данных по ролям
        for (Salary salary : salaryDataList) {
            int roleId = salary.getRole_id();
            String paymentDate = salary.getPayment_date();

            LocalDate date = LocalDate.parse(paymentDate); // Преобразование строки в дату
            int month = date.getMonthValue(); // Получаем номер месяца

            // Добавление данных в соответствующую серию
            seriesMap.get(roleId).getData().add(new XYChart.Data<>(month, salary.getNet_salary()));
        }

        // Добавление серий на график
        for (XYChart.Series<Integer, Double> series : seriesMap.values()) {
            if (!series.getData().isEmpty()) { // Проверка на пустые серии
                chartGrowsSalary.getData().add(series);
            }
        }

        chartGrowsSalary.getXAxis().setLabel("Месяц");
        chartGrowsSalary.getYAxis().setLabel("Чистая зарплата");
    }

    void getInfoForBarChartSalary(){
        Connect.client.sendMessage("getInfoForBarChartSalary");
        List<Salary> salariesData = (List<Salary>) Connect.client.readObject();
        Platform.runLater(() -> buildBarChart(salariesData));
        for (Salary salary : salariesData) {
            System.out.println(salary);
        }
    }
    private String getMonthName(int month) {
        switch (month) {
            case 1: return "Январь";
            case 2: return "Февраль";
            case 3: return "Март";
            case 4: return "Апрель";
            case 5: return "Май";
            case 6: return "Июнь";
            case 7: return "Июль";
            case 8: return "Август";
            case 9: return "Сентябрь";
            case 10: return "Октябрь";
            case 11: return "Ноябрь";
            case 12: return "Декабрь";
            default: return "Неизвестно";
        }
    }

    private void buildBarChart(List<Salary> salaryDataList) {
        salaryAllMonthChart.getData().clear();  // Очистка существующих данных графика

        // Составление данных для столбчатой диаграммы (по месяцам)
        Map<Integer, Double> monthlySalaries = new HashMap<>();
        for (Salary salary : salaryDataList) {
            String paymentDate = salary.getPayment_date();
            try {
                LocalDate date = LocalDate.parse(paymentDate); // Преобразование строки в дату
                int month = date.getMonthValue();  // Получаем номер месяца

                // Группировка данных по месяцам и добавление зарплаты
                monthlySalaries.put(month, monthlySalaries.getOrDefault(month, 0.0) + salary.getNet_salary());
            } catch (Exception e) {
                System.out.println("Ошибка при парсинге даты: " + paymentDate);
                e.printStackTrace();
            }
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<Integer, Double> entry : monthlySalaries.entrySet()) {
            String monthName = getMonthName(entry.getKey());
            series.getData().add(new XYChart.Data<>(monthName, entry.getValue()));
        }

        salaryAllMonthChart.getData().add(series);

        salaryAllMonthChart.getXAxis().setLabel("Месяц");
        salaryAllMonthChart.getYAxis().setLabel("Сумма зарплаты");

        // Увеличиваем ширину столбцов и расстояние между ними
        salaryAllMonthChart.setCategoryGap(20);  // Увеличиваем промежуток между столбцами
        salaryAllMonthChart.setBarGap(10);  // Увеличиваем ширину столбцов

        // Поворот подписей по оси X для удобства
        salaryAllMonthChart.getXAxis().setTickLabelRotation(0);  // Оставляем метки горизонтальными

        // Настраиваем ось X для плотности
        salaryAllMonthChart.getXAxis().setTickMarkVisible(true); // Показываем метки на оси X
        salaryAllMonthChart.getXAxis().setTickLabelsVisible(false); // Показываем подписи под столбцами
    }




}
