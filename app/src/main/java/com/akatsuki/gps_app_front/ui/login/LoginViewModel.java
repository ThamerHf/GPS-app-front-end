package com.akatsuki.gps_app_front.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.akatsuki.gps_app_front.data.repositories.repository.LoginRepository;
import com.akatsuki.gps_app_front.data.Result;
import com.akatsuki.gps_app_front.data.model.entity.LoggedInUser;
import com.akatsuki.gps_app_front.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    private boolean isUsernameModified = false;

    private boolean isPasswordModified = false;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password, loginResult);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUsernameModified && !username.isEmpty()) {
            isUsernameModified = true;
        }
        if (!isPasswordModified && !password.isEmpty()) {
            isPasswordModified = true;
        }

        Integer isUserNameValid = isUserNameValid(username) ? null : R.string.invalid_username;
        Integer isPasswordValid = isPasswordValid(password) ? null : R.string.not_a_valid_password;
        if (isAllErrorNull(isUserNameValid, isPasswordValid)) {
            loginFormState.setValue(new LoginFormState(true));
        } else {
            loginFormState.setValue(new LoginFormState(
                    isUserNameValid,
                    isPasswordValid));
        }
    }

    private boolean isAllErrorNull(Integer isUserNameValid, Integer isPasswordValid) {
        return (isUserNameValid == null) && (isPasswordValid == null)
                && isUsernameModified && isPasswordModified;
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (!isUsernameModified) {
            return true;
        }

        if (username == null) {
            return false;
        }

        return !username.trim().isEmpty();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        if (!isPasswordModified) {
            return true;
        }

        return password != null && password.trim().length() > 5;
    }
}