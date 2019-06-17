package com.kmj.studify.data;

public class UserModel {
    private String _id;
    private String name;
    private String facebookId;
    private String profileURL;
    private String current;
    private int start_time;
    private int end_time;
    private int average_time;
    private int max_time;
    private String token;
    private int __v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public int getAverage_time() {
        return average_time;
    }

    public void setAverage_time(int average_time) {
        this.average_time = average_time;
    }

    public int getMax_time() {
        return max_time;
    }

    public void setMax_time(int max_time) {
        this.max_time = max_time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public UserModel(String _id, String name, String facebookId, String profileURL, String current, int start_time, int end_time, int average_time, int max_time, String token, int __v) {
        this._id = _id;
        this.name = name;
        this.facebookId = facebookId;
        this.profileURL = profileURL;
        this.current = current;
        this.start_time = start_time;
        this.end_time = end_time;
        this.average_time = average_time;
        this.max_time = max_time;
        this.token = token;
        this.__v = __v;
    }
}
