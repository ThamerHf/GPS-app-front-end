package com.akatsuki.gps_app_front.data;

import android.util.Log;

import com.akatsuki.gps_app_front.callback.LoginCallback;
import com.akatsuki.gps_app_front.data.model.dto.response.AuthenticatedUserResponseDto;
import com.akatsuki.gps_app_front.data.model.dto.response.TokenResponseDto;
import com.akatsuki.gps_app_front.data.model.entity.AuthenToken;
import com.akatsuki.gps_app_front.data.model.entity.LoggedInUser;

import java.io.IOException;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
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

    public Result<LoggedInUser> login(String username, String password) {
        AuthenToken authenToken = new AuthenToken();
        LoggedInUser loggedInUserAuth = new LoggedInUser(username, username);
        // handle login
        dataSource.login(username, password, new LoginCallback() {
            @Override
            public void onLoginSuccess(LoggedInUser loggedInUser) {
                //authenToken.setToken(token.getToken());
                //Log.d("Authorization  ", "Bearer " + user.getUserName());
                Log.d("aaaa ", loggedInUser.getUserName());
            }

            @Override
            public void onLoginError(IOException exception) {
                Log.e("Login", "Authentication failed");
            }
        });

        /*dataSource.getAuthenticatedUser(authenToken.getToken(), new LoginCallback<LoggedInUser>() {
            @Override
            public void onLoginSuccess(LoggedInUser loggedInUser) {
                loggedInUserAuth.setUserName(loggedInUser.getUserName());
                loggedInUserAuth.setFirstName(loggedInUser.getFirstName());
                loggedInUserAuth.setEmail(loggedInUser.getEmail());
                loggedInUserAuth.setLastName(loggedInUser.getLastName());
            }

            @Override
            public void onLoginError(IOException exception) {
                Log.e("Auhtentication info", "Failed to get authenticated User");
            }
        });*/

        return new Result.Success<>(loggedInUserAuth);
    }
}