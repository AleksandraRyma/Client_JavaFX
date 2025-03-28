package model;

import java.io.Serializable;

public class Vacation implements Serializable {
    private int vacation_id;
    private int employee_id;
    private int position_id;
    private int role_id;
    private String start_date;
    private String end_date;
    private String status;

    public Vacation(int vacation_id, int employee_id, int role_id, int position_id, String end_date, String start_date, String status) {
        this.vacation_id = vacation_id;
        this.employee_id = employee_id;
        this.role_id = role_id;
        this.position_id = position_id;
        this.end_date = end_date;
        this.start_date = start_date;
        this.status = status;
    }


    public Vacation() {
        this.vacation_id = -1;
        this.employee_id = -1;
        this.role_id = -1;
        this.position_id = -1;
        this.end_date = "";
        this.start_date = "";
        this.status = "";
    }

    public int getVacation_id() {
        return vacation_id;
    }

    public void setVacation_id(int vacation_id) {
        this.vacation_id = vacation_id;
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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
