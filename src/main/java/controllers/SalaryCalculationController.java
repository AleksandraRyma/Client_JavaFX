package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import ClientWork.Connect;
import checks.DialogAlert;
import experience.SalaryRecord;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.*;

public class SalaryCalculationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addBtn1;

    @FXML
    private TableColumn<SalaryRecord, Double> baseSalaryCl;

    @FXML
    private Label baseSalaryLabel111;

    @FXML
    private TextField baseSalaryText1;

    @FXML
    private TableColumn<SalaryRecord, Double> bonusClAllTable;

    @FXML
    private TableColumn<Bonus, String> bonusName_cl;

    @FXML
    private TableColumn<Bonus, Double> coefficient_cl;

    @FXML
    private Label difficultLabel11111;

    @FXML
    private TextField difficultText111;

    @FXML
    private TableColumn<SalaryRecord, String> firstNameCl;

    @FXML
    private Label firstNameLabel;

    @FXML
    private TextField firstNameText;

    @FXML
    private Button goBackBtn;

    @FXML
    private ComboBox<Integer> idEmplComboBox;

    @FXML
    private Label idEmplLabel1;

    @FXML
    private Label isUnionMemberLabel11;

    @FXML
    private Label lastNameLabel1;

    @FXML
    private TextField lastNameText1;

    @FXML
    private CheckBox noUnionMember;

    @FXML
    private TableColumn<SalaryRecord, Integer> number_cl;

    @FXML
    private TableColumn<SalaryRecord, Double> salaryCl;

    @FXML
    private Button saveBtn;

    @FXML
    private AnchorPane scrool_panel;

    @FXML
    private TableView<Bonus> tableBonus;

    @FXML
    private TableView<SalaryRecord> tableSeeUserAdmin;

    @FXML
    private TableColumn<Bonus, Boolean> tablechoice_cl;

    @FXML
    private TableColumn<SalaryRecord, Integer> taxCl;

    @FXML
    private TableColumn<SalaryRecord, Double> vacationClAllTable;

    @FXML
    private Label vacationLabel;

    @FXML
    private TextField vacationText;

    @FXML
    private Label workHourLabel1111;

    @FXML
    private TextField workHourText11;

    @FXML
    private CheckBox yesUnionMember;


    @FXML
    private Label currentDate;

    @FXML
    private Label dateLabel;

    private Set<String> selectedBonuses = new HashSet<>();
    private Set<Integer> usedEmployeeIDs = new HashSet<>();

    private List<Salary> salaryEmployeee = new ArrayList<>();

    private ObservableList<SalaryRecord> salaryRecords = FXCollections.observableArrayList();

    @FXML
    void collectDataFromFormToAddVacation(ActionEvent event) {

    }

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
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = now.format(formatter);
        currentDate.setText(formattedDate);
        loadEmployeeIDs();
        idEmplComboBox.setOnAction(this::onEmployeeSelected); // Добавьте слушатель для ComboBox
        configureTableColumnsForBonus();
        loadBonusesFromDatabase();
        idEmplComboBox.setCellFactory(comboBox -> new ListCell<Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                    setStyle(usedEmployeeIDs.contains(item) ? "-fx-background-color: green;" : "-fx-background-color: gray;");
                }
            }
        });

        // Устанавливаем конвертер строк для ComboBox
        idEmplComboBox.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer id) {
                return id != null ? id.toString() : "";
            }

            @Override
            public Integer fromString(String string) {
                return Integer.valueOf(string);
            }
        });
    }

    private void onEmployeeSelected(ActionEvent event) {
        Integer selectedId = idEmplComboBox.getValue();
        if (selectedId != null) {
            loadEmployeeData(selectedId);
        }
    }

    private void loadEmployeeData(Integer employeeId) {
        Connect.client.sendMessage("getEmployeeDataCount");
        Connect.client.sendObject(employeeId);
        Employee employee = (Employee) Connect.client.readObject();
        boolean isUnionMember;
        if (employee != null) {
            firstNameText.setText(employee.getFirst_name());
            lastNameText1.setText(employee.getLast_name());
            String unionMember;
            unionMember = employee.getIs_union_member();
            if (unionMember.equals("Yes")) {
                isUnionMember = true;
            } else {
                isUnionMember = false;
            }

            yesUnionMember.setSelected(isUnionMember);
            noUnionMember.setSelected(!isUnionMember);

            loadPositionBaseSalary(employeeId);
            loadEmployeeLoadData(employeeId, currentDate.getText());
            loadVacationData(employeeId, currentDate.getText());
        }
    }

    private void loadPositionBaseSalary(Integer employeeId) {
        Connect.client.sendMessage("getPositionBaseSalaryCount");
        Connect.client.sendObject(employeeId);
        Position position = (Position) Connect.client.readObject();

        if (position != null) {
            baseSalaryText1.setText(String.valueOf(position.getBase_salary()));
        }
    }

    private void loadEmployeeLoadData(Integer employeeId, String date) {
        Connect.client.sendMessage("getEmployeeLoadDataCount");
        Connect.client.sendObject(employeeId);
        Connect.client.sendObject(date);
        EmployeeLoad load = (EmployeeLoad) Connect.client.readObject();

        if (load != null) {
            workHourText11.setText(String.valueOf(load.getOvertime_hour()));
            difficultText111.setText(String.valueOf(load.getDifficulty_rating()));
        } else {
            workHourText11.setText("Данные не найдены");
            difficultText111.setText("Данные не найдены");
        }
    }

    private void loadVacationData(Integer employeeId, String date) {
        Connect.client.sendMessage("getVacationDataCount");
        Connect.client.sendObject(employeeId);
        Connect.client.sendObject(date);
        Vacation vacation = (Vacation) Connect.client.readObject();

        if (vacation != null) {
            double vacationPay;
            vacationPay = Double.parseDouble(baseSalaryText1.getText());
            String currentDateString = currentDate.getText();
            LocalDate currentDate = LocalDate.parse(currentDateString);
            LocalDate nextMonth = currentDate.plusMonths(1);

            int daysInNextMonth = nextMonth.lengthOfMonth();

            LocalDate startDate = LocalDate.parse(vacation.getStart_date());
            LocalDate endDate = LocalDate.parse(vacation.getEnd_date());

            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            vacationPay = vacationPay / daysInNextMonth * (daysBetween);

            DecimalFormat df = new DecimalFormat("#.00");
            String formattedVacationPay = df.format(vacationPay);

            vacationText.setText(String.valueOf(formattedVacationPay));
        } else {
            vacationText.setText("Нет");
        }
    }

    private void loadEmployeeIDs() {
        Connect.client.sendMessage("getEmployeeIDs");
        List<Integer> employeeIDs = (List<Integer>) Connect.client.readObject();
        idEmplComboBox.setItems(FXCollections.observableArrayList(employeeIDs));
    }

    private void configureTableColumnsForBonus() {
        tableBonus.setEditable(true);
        ;
        tablechoice_cl.setCellValueFactory(cellData -> {
            Bonus bonus = cellData.getValue();
            SimpleBooleanProperty property = new SimpleBooleanProperty(selectedBonuses.contains(bonus.getBonus_type()));

            // Добавляем listener, чтобы обновлять selectedBonuses
            property.addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) {
                    selectedBonuses.add(bonus.getBonus_type());
                } else {
                    selectedBonuses.remove(bonus.getBonus_type());
                }
            });

            return property;
        });

        tablechoice_cl.setCellFactory(CheckBoxTableCell.forTableColumn(tablechoice_cl));


        bonusName_cl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBonus_type().toString())
        );
        coefficient_cl.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getAmount()).asObject() // Преобразуем в объект
        );
    }

    private void loadBonusesFromDatabase() {
        Connect.client.sendMessage("getBonuses"); // Здесь вы должны отправить запрос к серверу
        List<Bonus> bonuses = (List<Bonus>) Connect.client.readObject(); // Получаем список бонусов
        ObservableList<Bonus> observableEmployees = FXCollections.observableArrayList(bonuses);

        tableBonus.setItems(observableEmployees);
    }

    public List<Bonus> getSelectedBonuses() {
        List<Bonus> selectedBonusesList = new ArrayList<>();
        for (Bonus bonus : tableBonus.getItems()) {
            if (selectedBonuses.contains(bonus.getBonus_type())) {
                selectedBonusesList.add(bonus);
            }
        }
        return selectedBonusesList;
    }
    private void configureTableColumnsForSalary() {
        number_cl.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(tableSeeUserAdmin.getItems().indexOf(cellData.getValue()) + 1).asObject()
        );
        firstNameCl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFirstName()));
        taxCl.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getTaxPercentage()).asObject());

        baseSalaryCl.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getBaseSalary()).asObject()
        );

        // Преобразуйте базовую зарплату из текстового поля перед использованием
        String baseSalaryText = baseSalaryText1.getText().replace(',', '.'); // Замените запятые на точки
        double baseSalaryFromTextField = Double.parseDouble(baseSalaryText);

        bonusClAllTable.setCellValueFactory(cellData -> {
            double totalBonus = 0.0;
            for (Bonus bonus : getSelectedBonuses()) {
                totalBonus += bonus.getAmount() * baseSalaryFromTextField; // Умножаем на значение из текстового поля
            }
            return new SimpleDoubleProperty(totalBonus).asObject();
        });

        String vacationTextField = vacationText.getText().replace(',', '.'); // Замените запятые на точки
        vacationClAllTable.setCellValueFactory(cellData -> {
            double vacationBonus = 0.0;

            if (!vacationTextField.equals("Нет")) {
                vacationBonus = Double.parseDouble(vacationTextField);
                return new SimpleDoubleProperty(vacationBonus).asObject();
            } else {
                return new SimpleDoubleProperty(0.0).asObject(); // Возвращаем 0 вместо "Нет"
            }
        });

        salaryCl.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getNetSalary()).asObject());
    }
    public void addRecordAboutSalary(ActionEvent event) {

        System.out.println("addRecordAboutSalary");
        List<Bonus> bonuses = getSelectedBonuses();
        if (bonuses.isEmpty()) {
            System.out.println("Выбранные бонусы пусты.");
        } else {
            System.out.println("Выбранные бонусы содержат элементы:");
            for (Bonus bonus : bonuses) {
                System.out.println("Бонус: " + bonus.getBonus_type() + ", Коэффициент: " + bonus.getAmount());
            }
        }

        Integer selectedId = idEmplComboBox.getValue();
        if (selectedId != null) {
            if (usedEmployeeIDs.contains(selectedId)) {
                DialogAlert.showAlertInfo("Вы уже рассчитали зп этому сотруднику");
                return;
            } else {
                configureTableColumnsForSalary();
                loadSalaryFromData();
                usedEmployeeIDs.add(selectedId);
                idEmplComboBox.getSelectionModel().clearSelection();
                loadEmployeeIDs();
            }
        }
        clearFields();
    }
    private void loadSalaryFromData() {
        SalaryRecord salaryRecord = new SalaryRecord();
        salaryRecord.setFirstName(lastNameText1.getText());
System.out.println(salaryRecord.getFirstName());
salaryRecord.setEmployeeID(idEmplComboBox.getValue());
        // Преобразуем строку базовой зарплаты
        String baseSalaryText = baseSalaryText1.getText().replace(',', '.');
        double baseSalaryFromTextField = 0.0;
        try {
            baseSalaryFromTextField = Double.parseDouble(baseSalaryText);
            salaryRecord.setBaseSalary(baseSalaryFromTextField);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод базовой зарплаты: " + baseSalaryText);
            salaryRecord.setBaseSalary(0.0); // Устанавливаем значение по умолчанию
        }
        List<Bonus> bonuses = getSelectedBonuses(); // список бонусов, выбранных бухгалтером
        double totalBonus = 0.00;
        if (bonuses.isEmpty()) {
            System.out.println("Выбранные бонусы пусты.");
        } else {
            for (Bonus bonus : bonuses) {
                totalBonus += bonus.getAmount() * Double.parseDouble(baseSalaryText);
                System.out.println("Бонус: " + bonus.getBonus_type() + ", Коэффициент: " + bonus.getAmount());
            }
        }
        salaryRecord.setTotalBonus(totalBonus);

        String vacationSalary = vacationText.getText().replace(',', '.'); // заменяем запятую на точку
        double vacationBonus = 0.00;
        if (!vacationSalary.equals("Нет")) {
            salaryRecord.setVacationBonus(Double.parseDouble(vacationSalary));
        } else {
            salaryRecord.setVacationBonus(vacationBonus);
        }

        if (yesUnionMember.isSelected()) {
            salaryRecord.setTaxPercentage(15);
        } else {
            salaryRecord.setTaxPercentage(14);
        }

        double baseSalaryFromTextField1 = Double.parseDouble(baseSalaryText);
        double taxSum = salaryRecord.getTaxPercentage() *(salaryRecord.getBaseSalary() + salaryRecord.getTotalBonus() + salaryRecord.getVacationBonus())/100;
        double finalSalary = baseSalaryFromTextField1 + totalBonus + salaryRecord.getVacationBonus() - taxSum;
        DecimalFormat df = new DecimalFormat("#0.00"); // Формат с двумя знаками после запятой
        String formattedFinalSalary = df.format(finalSalary); // Форматируем значение как строку
        salaryRecord.setNetSalary(finalSalary);
        System.out.println(salaryRecord.getNetSalary());
        Salary salaruToAdd = new Salary();
        salaruToAdd.setNet_salary(salaryRecord.getNetSalary());
        salaruToAdd.setTax_percentage(salaryRecord.getTaxPercentage());
        salaruToAdd.setPayment_date(currentDate.getText());
        salaruToAdd.setEmployee_id(idEmplComboBox.getValue());
        salaryEmployeee.add(salaruToAdd);
        // Устанавливаем значение конечной зарплаты в salaryRecord как double
        salaryRecord.setNetSalary(Double.parseDouble(formattedFinalSalary.replace(',', '.'))); //

        salaryRecords.add(salaryRecord);
        tableSeeUserAdmin.setItems(salaryRecords);
    }


    @FXML
    private void clearFields() {
        // Очищаем текстовые поля
        firstNameText.clear();
        lastNameText1.clear();
        baseSalaryText1.clear();
        vacationText.clear();
        workHourText11.clear();
        difficultText111.clear();
        yesUnionMember.setSelected(false);
        noUnionMember.setSelected(false);
    }

    @FXML
    void saveSalaryToServer(ActionEvent event) {
        Connect.client.sendMessage("saveSalaryToServer");
        Connect.client.sendObject(salaryEmployeee);
        salaryEmployeee.clear();
        DialogAlert.showAlertInfo("Данные сохранены");
    }


}
