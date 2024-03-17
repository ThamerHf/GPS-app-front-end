package com.akatsuki.gps_app_front.callback;

import java.io.IOException;

public interface AppCallback<T> {
    void onLoginSuccess(T t);

    void onLoginError(IOException exception);
}
