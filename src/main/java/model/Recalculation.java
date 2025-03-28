package model;

import java.io.Serializable;

public class Recalculation implements Serializable {
    private int recalculation_id;
    private int employee_id;
    private int position_id;
    private int role_id;
    private String date_application;
    private String status_application;
    private String reason_application;

    public Recalculation(int recalculation_id, int employee_id, int position_id, int role_id, String date_application, String status_application, String reason_application) {
        this.recalculation_id = recalculation_id;
        this.employee_id = employee_id;
        this.position_id = position_id;
        this.role_id = role_id;
        this.date_application = date_application;
        this.status_application = status_application;
        this.reason_application = reason_application;
    }

    public Recalculation() {
        this.recalculation_id = -1;
        this.employee_id = -1;
        this.position_id = -1;
        this.role_id = -1;
        this.date_application = "";
        this.status_application = "";
        this.reason_application = "";
    }

    public int getRecalculation_id() {
        return recalculation_id;
    }

    public void setRecalculation_id(int recalculation_id) {
        this.recalculation_id = recalculation_id;
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

    public String getDate_application() {
        return date_application;
    }

    public void setDate_application(String date_application) {
        this.date_application = date_application;
    }

    public String getStatus_application() {
        return status_application;
    }

    public void setStatus_application(String status_application) {
        this.status_application = status_application;
    }

    public String getReason_application() {
        return reason_application;
    }

    public void setReason_application(String reason_application) {
        this.reason_application = reason_application;
    }
}
