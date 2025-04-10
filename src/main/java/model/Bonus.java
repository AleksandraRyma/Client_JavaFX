package model;

import javafx.beans.property.SimpleBooleanProperty;

import java.io.Serializable;
import java.util.Objects;

public class Bonus implements Serializable {
    private int bonus_id;
    private String bonus_type;
    private double amount;

    public Bonus(int bonus_id, String bonus_type, double amount) {
        this.bonus_id = bonus_id;
        this.bonus_type = bonus_type;
        this.amount = amount;
    }

    public Bonus() {
        this.bonus_id = -1;
        this.bonus_type = "";
        this.amount = 0;
    }

    public int getBonus_id() {
        return bonus_id;
    }

    public void setBonus_id(int bonus_id) {
        this.bonus_id = bonus_id;
    }

    public String getBonus_type() {
        return bonus_type;
    }

    public void setBonus_type(String bonus_type) {
        this.bonus_type = bonus_type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
