package model;


import java.io.Serializable;

public class EmployeeLoad implements Serializable {
    private int load_id;
    private int employee_id;
    private int position;
    private int role_id;
    private String work_load_date;
    private int hour_worked;
    private int overtime_hour;
    private int difficulty_rating;

    public EmployeeLoad(int load_id, int employee_id, int position, int role_id, String work_load_date, int hour_worked, int overtime_hour, int difficulty_rating) {
        this.load_id = load_id;
        this.employee_id = employee_id;
        this.position = position;
        this.role_id = role_id;
        this.work_load_date = work_load_date;
        this.hour_worked = hour_worked;
        this.overtime_hour = overtime_hour;
        this.difficulty_rating = difficulty_rating;
    }

    public EmployeeLoad() {
        this.load_id = -1;
        this.employee_id = -1;
        this.position = -1;
        this.role_id = -1;
        this.work_load_date = "";
        this.hour_worked = 0;
        this.overtime_hour = 0;
        this.difficulty_rating = 0;
    }


    public int getLoad_id() {
        return load_id;
    }

    public void setLoad_id(int load_id) {
        this.load_id = load_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getWork_load_date() {
        return work_load_date;
    }

    public void setWork_load_date(String work_load_date) {
        this.work_load_date = work_load_date;
    }

    public int getHour_worked() {
        return hour_worked;
    }

    public void setHour_worked(int hour_worked) {
        this.hour_worked = hour_worked;
    }

    public int getOvertime_hour() {
        return overtime_hour;
    }

    public void setOvertime_hour(int overtime_hour) {
        this.overtime_hour = overtime_hour;
    }

    public int getDifficulty_rating() {
        return difficulty_rating;
    }

    public void setDifficulty_rating(int difficulty_rating) {
        this.difficulty_rating = difficulty_rating;
    }
}
