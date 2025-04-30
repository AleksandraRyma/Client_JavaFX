package model;

import java.io.Serializable;
import java.util.List;

public class Salary implements Serializable {
    private int salary_id;
    private int employee_id;
    private int position_id;
    private int role_id;
    private int bonus_id;
    private int tax_percentage;
    private double net_salary;
    private String payment_date;

    private List<Bonus> bonusList;

    public List<Bonus> getBonusList() {
        return bonusList;
    }

    public void setBonusList(List<Bonus> bonusList) {
        this.bonusList = bonusList;
    }

    private double bonus_sum;

    public double getBonus_sum() {
        return bonus_sum;
    }

    public void setBonus_sum(double bonus_sum) {
        this.bonus_sum = bonus_sum;
    }

    public Salary(int salary_id, int employee_id, int position_id, int role_id, int bonus_id, int tax_percentage, double net_salary, String payment_date) {
        this.salary_id = salary_id;
        this.employee_id = employee_id;
        this.position_id = position_id;
        this.role_id = role_id;
        this.bonus_id = bonus_id;
        this.tax_percentage = tax_percentage;
        this.net_salary = net_salary;
        this.payment_date = payment_date;
    }

    public Salary() {
        this.salary_id = -1;
        this.employee_id = -1;
        this.position_id = -1;
        this.role_id = -1;
        this.bonus_id = -1;
        this.tax_percentage = 13;
        this.net_salary = 0;
        this.payment_date = "";
    }

    public int getSalary_id() {
        return salary_id;
    }

    public void setSalary_id(int salary_id) {
        this.salary_id = salary_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getBonus_id() {
        return bonus_id;
    }

    public void setBonus_id(int bonus_id) {
        this.bonus_id = bonus_id;
    }

    public int getTax_percentage() {
        return tax_percentage;
    }

    public void setTax_percentage(int tax_percentage) {
        this.tax_percentage = tax_percentage;
    }

    public double getNet_salary() {
        return net_salary;
    }

    public void setNet_salary(double net_salary) {
        this.net_salary = net_salary;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    @Override
    public String toString() {
        return net_salary + " " + payment_date;
    }
}
