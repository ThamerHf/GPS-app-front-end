package com.akatsuki.gps_app_front.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
public class LoginResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    public LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    public LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}