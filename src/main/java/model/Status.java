package model;

import java.io.Serializable;

public class Status implements Serializable {
    private int status_id;
    private String status_name;

    public Status(int status_id, String status_name) {
        this.status_id = status_id;
        this.status_name = status_name;
    }

    public Status() {
        this.status_id = 1;
        this.status_name = "Pending";
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}

