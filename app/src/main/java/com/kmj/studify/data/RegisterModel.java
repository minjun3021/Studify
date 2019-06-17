package com.kmj.studify.data;

import java.io.Serializable;

public class RegisterModel implements Serializable {
    private String message;
    private UserModel userModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RegisterModel(String message, UserModel userModel) {
        this.message = message;
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }




}
