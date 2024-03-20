package com.akatsuki.gps_app_front.data.repositories.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.akatsuki.gps_app_front.callback.AppCallback;
import com.akatsuki.gps_app_front.data.LoginDataSource;
import com.akatsuki.gps_app_front.data.RegisterDataSource;
import com.akatsuki.gps_app_front.data.Result;
import com.akatsuki.gps_app_front.data.model.dto.response.TokenResponseDto;
import com.akatsuki.gps_app_front.data.model.entity.AuthenToken;
import com.akatsuki.gps_app_front.data.model.entity.LoggedInUser;
import com.akatsuki.gps_app_front.ui.login.LoggedInUserView;
import com.akatsuki.gps_app_front.ui.login.LoginResult;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class RegisterRepository {
    private static volatile RegisterRepository instance;

    private RegisterDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

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

    public boolean isLoggedIn() {
        return user != null;
    }


    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password,
                                      MutableLiveData<LoginResult> loginResult) {
        AuthenToken authenToken = new AuthenToken();
        LoggedInUser loggedInUserAuth = new LoggedInUser("aaaaa", "aaaaaaaa");
        // handle login

        CompletableFuture<TokenResponseDto> future = new CompletableFuture<>();

        dataSource.register(username, password, new AppCallback<TokenResponseDto>() {
            @Override
            public void onCallBackSuccess(TokenResponseDto token) {
                authenToken.setToken(token.getToken());

                authenTokenRepository.getAllToken(new AppCallback<List<AuthenToken>>() {
                    @Override
                    public void onCallBackSuccess(List<AuthenToken> authenTokens) {
                        if (authenTokens.isEmpty()) {
                            authenTokenRepository.addToken(authenToken);
                        } else {
                            authenToken.setId(authenTokens.get(0).getId());
                            authenTokenRepository.updateToken(authenToken);
                        }
                    }

                    @Override
                    public void onCallBackError(IOException exception) {
                        Log.e("Update token", Objects.requireNonNull(exception.getMessage()));
                    }
                });
                Log.d("token ", token.getToken());
                dataSource.getAuthenticatedUser(authenToken.getToken(), new AppCallback<LoggedInUser>() {
                    @Override
                    public void onCallBackSuccess(LoggedInUser loggedInUser) {
                        loggedInUserAuth.setUserName(loggedInUser.getUserName());
                        loggedInUserAuth.setFirstName(loggedInUser.getFirstName());
                        loggedInUserAuth.setEmail(loggedInUser.getEmail());
                        loggedInUserAuth.setLastName(loggedInUser.getLastName());
                        Log.d("Get Auth User ", loggedInUserAuth.getUserName());
                        loginResult.setValue(new LoginResult(
                                new LoggedInUserView(loggedInUserAuth.getFirstName())));
                    }

                    @Override
                    public void onCallBackError(IOException exception) {
                        Log.e("Auhtentication info", "Failed to get authenticated User");
                        loginResult.setValue(new LoginResult(
                                new LoggedInUserView("Login Failed")));
                    }
                });
            }

            @Override
            public void onCallBackError(IOException exception) {
                Log.e("Login", "Authentication failed");
            }
        });

        return new Result.Success<>(loggedInUserAuth);
    }
}
