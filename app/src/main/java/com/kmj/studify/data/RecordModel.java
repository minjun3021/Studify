package com.kmj.studify.data;

public class RecordModel {
    private String _id;
    private String date;
    private double amount;
    private String userToken;
    private String token;
    private String __v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    public RecordModel(String _id, String date, double amount, String userToken, String token, String __v) {
        this._id = _id;
        this.date = date;
        this.amount = amount;
        this.userToken = userToken;
        this.token = token;
        this.__v = __v;
    }
}
