package com.kmj.studify.data;

public class RegisterModel {

    private UserModel userModel;
    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public RegisterModel(UserModel userModel) {
        this.userModel = userModel;
    }


}
