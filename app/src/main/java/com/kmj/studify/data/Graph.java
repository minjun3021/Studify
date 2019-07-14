package com.kmj.studify.data;

import java.io.Serializable;

public class Graph implements Serializable {
    private double amount;
    private String date;
    private String name;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Graph(double amount, String date, String name) {
        this.amount = amount;
        this.date = date;
        this.name = name;
    }
}
