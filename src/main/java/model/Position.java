package model;

import java.io.Serializable;

public class Position implements Serializable {
    private int position_id;
    private String position_name;
    private double base_salary;

    public Position(int position_id, String position_name, double base_salary) {
        this.position_id = position_id;
        this.position_name = position_name;
        this.base_salary = base_salary;
    }

    public Position() {
        this.position_id = -1;
        this.position_name = "";
        this.base_salary = 0;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public double getBase_salary() {
        return base_salary;
    }

    public void setBase_salary(double base_salary) {
        this.base_salary = base_salary;
    }
}
