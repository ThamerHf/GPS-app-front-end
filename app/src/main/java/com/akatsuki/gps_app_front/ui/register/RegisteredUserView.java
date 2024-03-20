package com.akatsuki.gps_app_front.ui.register;

/**
 * Class exposing authenticated user details to the UI.
 */
public class RegisteredUserView {
    private String userName;

    private String message;
    //... other data fields that may be accessible to the UI


    public RegisteredUserView(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public RegisteredUserView(String message) {
        this.message = message;
    }

    String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}