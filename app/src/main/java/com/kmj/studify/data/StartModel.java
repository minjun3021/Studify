package com.kmj.studify.data;

public class StartModel {
    private double amount;
    private String message;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StartModel(int amount, String message) {
        this.amount = amount;
        this.message = message;
    }
}
