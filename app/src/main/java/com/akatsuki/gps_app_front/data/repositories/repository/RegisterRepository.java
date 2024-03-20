package com.akatsuki.gps_app_front.data.repositories.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.akatsuki.gps_app_front.R;
import com.akatsuki.gps_app_front.callback.AppCallback;
import com.akatsuki.gps_app_front.data.RegisterDataSource;
import com.akatsuki.gps_app_front.data.Result;
import com.akatsuki.gps_app_front.data.model.dto.response.GenericResponseDto;
import com.akatsuki.gps_app_front.data.model.dto.response.TokenResponseDto;
import com.akatsuki.gps_app_front.data.model.entity.RegisterMessage;
import com.akatsuki.gps_app_front.ui.register.RegisterResult;
import com.akatsuki.gps_app_front.ui.register.RegisteredUserView;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class RegisterRepository {
    private static volatile RegisterRepository instance;

    private RegisterDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private RegisterMessage user = null;

    // private constructor : singleton access
    private RegisterRepository(RegisterDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegisterRepository getInstance(RegisterDataSource dataSource) {
        if (instance == null) {
            instance = new RegisterRepository(dataSource);
        }
        return instance;
    }

    public Result<RegisterMessage> register(String firstName, String lastName, String username,
                                            String email, String password,
                                            MutableLiveData<RegisterResult> registerResult) {
        RegisterMessage registerMessage = new RegisterMessage("default");
        // handle login

        CompletableFuture<TokenResponseDto> future = new CompletableFuture<>();

        dataSource.register(firstName, lastName,
                username, email, password,  new AppCallback<RegisterMessage>() {
            @Override
            public void onCallBackSuccess(RegisterMessage registerMessage) {
                registerResult.setValue(new RegisterResult(
                        new RegisteredUserView(username, registerMessage.getMessage())));
            }

            @Override
            public void onCallBackError(IOException exception) {
                if (exception.getMessage() != null && exception.getMessage().equals("EXISTS")) {
                    registerResult.setValue(new RegisterResult(
                            R.string.register_failed_username_already_used));
                } else {
                    registerResult.setValue(new RegisterResult(R.string.register_failed));
                }
                Log.e("Register", "Register failed");
            }
        });

        return new Result.Success<>(registerMessage);
    }
}
