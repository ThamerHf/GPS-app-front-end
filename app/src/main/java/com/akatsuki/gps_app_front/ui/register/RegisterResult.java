package com.akatsuki.gps_app_front.ui.register;

import androidx.annotation.Nullable;

import com.akatsuki.gps_app_front.ui.login.LoggedInUserView;

/**
 * Authentication result : success (user details) or error message.
 */
public class RegisterResult {
    @Nullable
    private RegisteredUserView success;
    @Nullable
    private Integer error;

    public RegisterResult(@Nullable Integer error) {
        this.error = error;
    }

    public RegisterResult(@Nullable RegisteredUserView success) {
        this.success = success;
    }

    @Nullable
    RegisteredUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}