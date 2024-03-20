package com.akatsuki.gps_app_front.data.repositories.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.akatsuki.gps_app_front.R;
import com.akatsuki.gps_app_front.callback.AppCallback;
import com.akatsuki.gps_app_front.data.LoginDataSource;
import com.akatsuki.gps_app_front.data.Result;
import com.akatsuki.gps_app_front.data.model.dto.response.TokenResponseDto;
import com.akatsuki.gps_app_front.data.model.entity.AuthenToken;
import com.akatsuki.gps_app_front.data.model.entity.LoggedInUser;
import com.akatsuki.gps_app_front.ui.login.LoggedInUserView;
import com.akatsuki.gps_app_front.ui.login.LoginResult;
import com.akatsuki.gps_app_front.ui.register.RegisterResult;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    private AuthenTokenRepository authenTokenRepository;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource, AuthenTokenRepository authenTokenRepository) {
        this.dataSource = dataSource;
        this.authenTokenRepository = authenTokenRepository;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource,
                                              AuthenTokenRepository authenTokenRepository) {
        if (instance == null) {
            instance = new LoginRepository(dataSource, authenTokenRepository);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password,
                                      MutableLiveData<LoginResult> loginResult) {
        AuthenToken authenToken = new AuthenToken();
        LoggedInUser loggedInUserAuth = new LoggedInUser("dafault", "dafault");
        // handle login

        CompletableFuture<TokenResponseDto> future = new CompletableFuture<>();

        dataSource.login(username, password, new AppCallback<TokenResponseDto>() {
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

                        dataSource.getAuthenticatedUser(authenToken.getToken(),
                                new AppCallback<LoggedInUser>() {
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
                                loginResult.setValue(new LoginResult(R.string.login_failed));
                            }
                        });
                    }

                    @Override
                    public void onCallBackError(IOException exception) {
                        loginResult.setValue(new LoginResult(R.string.authentication_is_required));
                        Log.e("Update token", Objects.requireNonNull(exception.getMessage()));
                    }
                });
                Log.d("token ", token.getToken());
            }

            @Override
            public void onCallBackError(IOException exception) {
                loginResult.setValue(new LoginResult(R.string.login_failed));
                Log.e("Login", "Authentication failed");
            }
        });

        return new Result.Success<>(loggedInUserAuth);
    }


}