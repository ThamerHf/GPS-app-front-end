package com.akatsuki.gps_app_front.ui.register;

/**
 * Class exposing authenticated user details to the UI.
 */
public class RegisteredUserView {
    private String message;
    //... other data fields that may be accessible to the UI

    public RegisteredUserView(String message) {
        this.message = message;
    }

    String getMessage() {
        return message;
    }
}