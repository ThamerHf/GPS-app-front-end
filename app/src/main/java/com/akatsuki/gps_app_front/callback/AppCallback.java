package com.akatsuki.gps_app_front.callback;

import java.io.IOException;

public interface AppCallback<T> {
    void onCallBackSuccess(T t);

    void onCallBackError(IOException exception);
}
