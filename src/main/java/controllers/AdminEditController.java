package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Function;

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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;

public class AdminEditController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Employee, String> access_cl;

    @FXML
    private TableColumn<Employee, String> first_name_cl;
    @FXML
    private TableColumn<Employee, Integer> employy_id_cl;
    @FXML
    private Button goBackBtn;

    @FXML
    private TableColumn<Employee, String> hire_date_id_cl;

    @FXML
    private TableColumn<Employee, String> last_name_cl;

    @FXML
    private TableColumn<Employee, String> position_cl;

    @FXML
    private TableColumn<Employee, String> role_cl;

    @FXML
    private Button saveUserBtn;

    @FXML
    private AnchorPane scrool_panel;

    @FXML
    private Label saveLabelText;

    @FXML
    private TableView<Employee> tableSeeUserAdmin;

    private List<Employee> modifyEmployeeList = new ArrayList<>();

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
        Connect.client.sendMessage("adminEditUser");
        loadTableData();
        saveLabelText.setVisible(false);
    }

    private void loadTableData() throws ClassNotFoundException {
        List<Employee> employeeList = (List<Employee>) Connect.client.readObject();
        ObservableList<Employee> observableList = FXCollections.observableArrayList(employeeList);
        tableSeeUserAdmin.setItems(observableList);

        employy_id_cl.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEmployee_id()).asObject());
        first_name_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name()));
        last_name_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLast_name()));
        hire_date_id_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHire_date()));
        position_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPosition()));
        role_cl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));

        access_cl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAccess() == 0 ? "Доступ закрыт" : "Доступ открыт"));

        access_cl.setCellFactory(col -> createComboBoxCell(
                new String[]{"Доступ открыт", "Доступ закрыт"},
                (employee, value) -> employee.setAccess(value.equals("Доступ открыт") ? 1 : 0)
        ));

        role_cl.setCellFactory(col -> createComboBoxCell(
                new String[]{"руководитель отдела", "администратор", "бухгалтер", "сотрудник"},
                (employee, value) -> {
                    employee.setRole(value); // сохраняем имя роли

                    // 👇 вручную устанавливаем ID по названию
                    if (value.equals("руководитель отдела")) {
                        employee.setRole_id(2);
                    } else if (value.equals("администратор")) {
                        employee.setRole_id(1);
                    } else if (value.equals("бухгалтер")) {
                        employee.setRole_id(3);
                    } else if (value.equals("сотрудник")) {
                        employee.setRole_id(4);
                    }
                }
        ));

        position_cl.setCellFactory(col -> createComboBoxCell(
                new String[]{"Генеральный директор", "Главный бухгалтер", "Стажер бухгалтера",
                        "Менеджер по финансовым операциям", "Главный экономист", "Администратор"},
                (employee, value) -> {
                    employee.setPosition(value); // сохраняем имя позиции

                    // 👇 вручную устанавливаем ID по названию
                    if (value.equals("Генеральный директор")) {
                        employee.setPosition_id(1);
                    } else if (value.equals("Главный бухгалтер")) {
                        employee.setPosition_id(2);
                    } else if (value.equals("Стажер бухгалтера")) {
                        employee.setPosition_id(3);
                    } else if (value.equals("Менеджер по финансовым операциям")) {
                        employee.setPosition_id(4);
                    } else if (value.equals("Главный экономист")) {
                        employee.setPosition_id(5);
                    } else if (value.equals("Администратор")) {
                        employee.setPosition_id(6);
                    }
                }
        ));

    }

    private TableCell<Employee, String> createComboBoxCell(String[] options, BiConsumer<Employee, String> assignFunction) {
        return new TableCell<Employee, String>() {
            private ComboBox<String> comboBox;

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (comboBox == null) {
                        comboBox = new ComboBox<>(FXCollections.observableArrayList(options));
                        comboBox.setOnAction(event -> {
                            Employee employee = getTableView().getItems().get(getIndex());
                            String selectedValue = comboBox.getSelectionModel().getSelectedItem();
                            assignFunction.accept(employee, selectedValue); // ← вот здесь происходит присвоение
                            if (!modifyEmployeeList.contains(employee)) {
                                modifyEmployeeList.add(employee);
                            }
                        });
                    }

                    comboBox.getSelectionModel().select(item);
                    setGraphic(comboBox);
                }
            }
        };
    }

    @FXML
    public void saveUserAccessChanged(ActionEvent event) throws IOException {

        if(!modifyEmployeeList.isEmpty()) {
            Connect.client.sendObject(modifyEmployeeList);
System.out.println(modifyEmployeeList);
            String mes =  (String) Connect.client.readObject();
            if (mes.equals("Data changed")) {
                saveLabelText.setText("Данные успешно обновлены.");
                saveLabelText.setVisible(true);
            } else if (mes.equals("Data not changed")){
                saveLabelText.setText("Ошибка при обновлении данных.");
                saveLabelText.setVisible(true);
            }
        }

    }

}
