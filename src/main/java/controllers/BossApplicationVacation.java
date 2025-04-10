package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Vacation;

public class BossApplicationVacation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label changesSave;

    @FXML
    private TableColumn<Vacation, String> endDate_cl;

    @FXML
    private TableColumn<Vacation, String> firstNamecl;

    @FXML
    private Button goBackBtn;

    @FXML
    private TableColumn<Vacation, String> lastNamecl;

    @FXML
    private TableColumn<Vacation, Integer> number_cl;

    @FXML
    private Button saveBtn1;

    @FXML
    private AnchorPane scrool_panel;

    @FXML
    private TableColumn<Vacation, String> startDate_cl;

    @FXML
    private TableColumn<Vacation, String> status_cl;

    @FXML
    private TableView<Vacation> tableSeeUserAdmin;


    private Map<Integer, String> originalStatuses = new HashMap<>();
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

    private void configureTableColumns() {
        firstNamecl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getFirst_name()));
        lastNamecl.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployee().getLast_name()));

        number_cl.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(tableSeeUserAdmin.getItems().indexOf(cellData.getValue()) + 1).asObject()
        );

        startDate_cl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStart_date().toString())
        );
        endDate_cl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEnd_date().toString())
        );

        status_cl.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus().getStatus_name())
        );
        // Настройка колонки статуса с выпадающим списком
        status_cl.setCellFactory(column -> new TableCell<Vacation, String>() {
            private final ComboBox<String> comboBox = new ComboBox<>();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    comboBox.setItems(FXCollections.observableArrayList("В обработке", "Одобрено", "Отказано"));
                    comboBox.setValue(item); // Установить текущее значение
                    comboBox.setOnAction(event -> {
                        if (getTableRow().getItem() != null) {
                            Vacation vacation = getTableRow().getItem();
                            vacation.getStatus().setStatus_name(comboBox.getValue());
                        }
                    });
                    setGraphic(comboBox);
                    setText(null);
                }
            }
        });
    }
    @FXML
    private void loadVacationData() {
        Connect.client.sendMessage("getVacationDataEmployee");
        List<Vacation> vacations = (List<Vacation>) Connect.client.readObject();
        System.out.println(vacations);
        if (vacations != null && !vacations.isEmpty()) {
            ObservableList<Vacation> observableEmployees = FXCollections.observableArrayList(vacations);
            tableSeeUserAdmin.setItems(observableEmployees);
            originalStatuses.clear();
            for (Vacation v : vacations) {
                originalStatuses.put(v.getVacation_id(), v.getStatus().getStatus_name());
            }
        } else {
            DialogAlert.showAlertInfo("Список отпусков пуст.");
        }
    }

    @FXML
    void saveChangedStatuses(ActionEvent event) throws IOException {
        ObservableList<Vacation> currentVacations = tableSeeUserAdmin.getItems();
        List<Vacation> modifiedVacations = new java.util.ArrayList<>();

        for (Vacation v : currentVacations) {
            String originalStatus = originalStatuses.get(v.getVacation_id());
            if (originalStatus != null && !originalStatus.equals(v.getStatus().getStatus_name())) {
                modifiedVacations.add(v);
            }
        }

        if (!modifiedVacations.isEmpty()) {
            Connect.client.sendMessage("updateVacationStatuses");
            Connect.client.sendObject(modifiedVacations);
            DialogAlert.showAlertInfo("Изменения отправлены на сервер.");

        } else {
            DialogAlert.showAlertInfo("Нет изменений для сохранения.");
        }
        String message = (String) Connect.client.readMessage();
        if(message.equals("Изменения сохранены")){
            changesSave.setVisible(true);
            changesSave.setText(message);
        }
        else {
            changesSave.setVisible(true);
            changesSave.setText(message);
        }
    }
    @FXML
    void initialize() {
        changesSave.setVisible(false);
        configureTableColumns();
        loadVacationData();
    }

}
