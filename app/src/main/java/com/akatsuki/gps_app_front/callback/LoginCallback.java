package com.akatsuki.gps_app_front.callback;

import com.akatsuki.gps_app_front.data.model.entity.LoggedInUser;

import java.io.IOException;

public interface LoginCallback {
    void onLoginSuccess(LoggedInUser user);

    void onLoginError(IOException exception);
}
