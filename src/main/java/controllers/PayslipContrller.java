package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import ClientWork.Connect;
import experience.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Employee;
import model.Salary;

public class PayslipContrller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label base_salary_label;

    @FXML
    private TextField base_salary_number;

    @FXML
    private TextField bonus_number;

    @FXML
    private TextField date_text;

    @FXML
    private TextField first_nameText;

    @FXML
    private Label first_name_label;

    @FXML
    private Button goOut;

    @FXML
    private Label id_label;

    @FXML
    private TextField id_text;

    @FXML
    private TextField last_nameText;

    @FXML
    private Label last_name_label;

    @FXML
    private TextField net_salary_number;

    @FXML
    private TextField posiion_text;

    @FXML
    private Label position_label;

    @FXML
    private Label profcous_label;

    @FXML
    private TextField summa_bonus;

    @FXML
    private TextField summa_tax;

    @FXML
    private TextField tax13_number;

    @FXML
    private TextField tax1_pencia_number;

    @FXML
    private TextField tax_profcom;

    @FXML
    private TextField text_month_date;

    @FXML
    private Label vacation_label;

    @FXML
    private TextField vacation_number;

    @FXML
    void goOut(ActionEvent event) {

    }

    @FXML
    void initialize() {
        first_nameText.setEditable(false);
        last_nameText.setEditable(false);
        id_text.setEditable(false);
        posiion_text.setEditable(false);
        base_salary_number.setEditable(false);
        bonus_number.setEditable(false);
        net_salary_number.setEditable(false);
        date_text.setEditable(false);
        text_month_date.setEditable(false);
        summa_bonus.setEditable(false);
        summa_tax.setEditable(false);
        tax13_number.setEditable(false);
        tax1_pencia_number.setEditable(false);
        tax_profcom.setEditable(false);
        getDataAboutPaySlipFromServer();
    }


    @FXML
    void getDataAboutPaySlipFromServer() {
        int employeeId = SessionManager.getSessionEmpId();
        Connect.client.sendMessage("employeeSeePaySlip");
        Connect.client.sendMessage(String.valueOf(employeeId));
        Employee employeForPaySlip = (Employee) Connect.client.readObject();

        first_nameText.setText(employeForPaySlip.getFirst_name());
        last_nameText.setText(employeForPaySlip.getLast_name());
        id_text.setText(String.valueOf(employeForPaySlip.getEmployee_id()));
        posiion_text.setText(employeForPaySlip.getPosition_empl().getPosition_name());
        base_salary_number.setText(String.valueOf(employeForPaySlip.getPosition_empl().getBase_salary()));


        // Установка данных о зарплате
        Salary salary = employeForPaySlip.getSalary();
        LocalDate paymentDate = LocalDate.parse(salary.getPayment_date());
        String monthName = paymentDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("ru"));
        int year = paymentDate.getYear();

        text_month_date.setText(monthName + " " + year);
        net_salary_number.setText(String.valueOf(salary.getNet_salary()));
        double netSalary = salary.getNet_salary();
        double tax = salary.getTax_percentage();
        double baseSalary = employeForPaySlip.getPosition_empl().getBase_salary();

        double expectedNet = baseSalary * (1 - tax / 100);
// Сравнение с небольшой дельтой
        if (Math.abs(netSalary - expectedNet) < 0.01) {
            bonus_number.setText("Нет");
            summa_bonus.setText(String.format("%.2f", baseSalary));
        } else {
            double grossSalary = netSalary / (1 - tax / 100);
            double bonuses = grossSalary - baseSalary;
            // Округляем до 2 знаков после запятой
            bonus_number.setText(String.format("%.2f", bonuses));
            double totalAccrued = baseSalary + bonuses;
            summa_bonus.setText(String.format("%.2f", totalAccrued));
        }
        date_text.setText(String.valueOf(salary.getPayment_date()));

        double grossSalary = netSalary / (1 - tax / 100);

        double tax13 = grossSalary * 0.13;
        double taxPension = grossSalary * 0.01;
        double taxUnion = 0;
        if (employeForPaySlip.getIs_union_member().equals("Yes")) {
            taxUnion = grossSalary * 0.01;
            tax_profcom.setText(String.format("%.2f", taxUnion));
            profcous_label.setVisible(true);
        } else {
            profcous_label.setVisible(false);
            tax_profcom.setVisible(false);
        }

        double totalTax = tax13 + taxPension + taxUnion;

        tax13_number.setText(String.format("%.2f", tax13));
        tax1_pencia_number.setText(String.format("%.2f", taxPension));
        summa_tax.setText(String.format("%.2f", totalTax));

        System.out.println(employeForPaySlip.getPosition_empl().getPosition_name());
        System.out.println(employeForPaySlip.getPosition_empl().getBase_salary());
        System.out.println(employeForPaySlip.getFirst_name());
        System.out.println(employeForPaySlip.getSalary().getNet_salary());
    }


    @FXML
    void returnMainMenuAdmin(ActionEvent event) {
        goOut.getScene().getWindow().hide();
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
}
