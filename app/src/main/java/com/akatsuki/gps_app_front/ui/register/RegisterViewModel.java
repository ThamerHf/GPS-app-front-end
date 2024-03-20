package com.akatsuki.gps_app_front.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.akatsuki.gps_app_front.R;
import com.akatsuki.gps_app_front.data.Result;
import com.akatsuki.gps_app_front.data.model.entity.RegisterMessage;
import com.akatsuki.gps_app_front.data.repositories.repository.RegisterRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();
    private RegisterRepository registerRepository;

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    private boolean isUsernameModified = false;
    private boolean isFirstNameModified = false;
    private boolean isLastNameModified = false;
    private boolean isEmailModified = false;
    private boolean isPasswordModified = false;
    private boolean isConfirmPasswordModified = false;

    RegisterViewModel(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<RegisterResult> getRegisterResult() {
        return registerResult;
    }

    public void register(String firstName, String lastName, String username,
                         String email, String password) {
        // can be launched in a separate asynchronous job
        Result<RegisterMessage> result = registerRepository.register(firstName, lastName,
                username, email, password, registerResult);
    }

    public void registerDataChanged(String username, String firstName, String lastName,
                                    String email, String password, String confirmPassword) {

        if (!isUsernameModified && !username.isEmpty()) {
            isUsernameModified = true;
        }
        if (!isFirstNameModified && !firstName.isEmpty()) {
            isFirstNameModified = true;
        }
        if (!isLastNameModified && !lastName.isEmpty()) {
            isLastNameModified = true;
        }
        if (!isEmailModified && !email.isEmpty()) {
            isEmailModified = true;
        }
        if (!isPasswordModified && !password.isEmpty()) {
            isPasswordModified = true;
        }
        if (!isConfirmPasswordModified && !confirmPassword.isEmpty()) {
            isConfirmPasswordModified = true;
        }

        Integer isUserNameValid = isUserNameValid(username) ? null : R.string.invalid_username;
        Integer isFirstNameValid = isFirstNameValid(firstName) ? null : R.string
                .not_a_valid_firstname;
        Integer isLastNameValid = isLastNameValid(lastName) ? null :  R.string
                .not_a_valid_lastname;
        Integer isEmailValid = isEmailValid(email) ? null : R.string.not_a_valid_email;
        Integer isPasswordValid = isPasswordValid(password) ? null : R.string.not_a_valid_password;
        Integer isConfirmPasswordValid = isConfirmPasswordValid(password, confirmPassword) ? null
                : R.string.doesn_t_match_entred_password;

        if (isAllErrorNull(isUserNameValid, isFirstNameValid, isLastNameValid,
                isEmailValid, isPasswordValid, isConfirmPasswordValid)) {
            registerFormState.setValue(new RegisterFormState(true));
        } else {
            registerFormState.setValue(new RegisterFormState(
                    isUserNameValid,
                    isFirstNameValid,
                    isLastNameValid,
                    isEmailValid,
                    isPasswordValid,
                    isConfirmPasswordValid)
            );
        }

    }

    private boolean isAllErrorNull(Integer isUserNameValid, Integer isFirstNameValid,
                                   Integer isLastNameValid, Integer isEmailValid,
                                   Integer isPasswordValid, Integer isConfirmPasswordValid) {
        return (isUserNameValid == null) && (isFirstNameValid == null)
                && (isLastNameValid == null) && (isEmailValid == null)
                && (isPasswordValid == null) && (isConfirmPasswordValid == null)
                && isUsernameModified && isPasswordModified
                && isFirstNameModified && isLastNameModified
                && isEmailModified && isConfirmPasswordModified;
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

    private boolean isFirstNameValid(String firstName) {
        if (!isFirstNameModified) {
            return true;
        }

        if (firstName == null) {
            return false;
        }

        return !firstName.trim().isEmpty();
    }

    private boolean isLastNameValid(String lastName) {
        if (!isLastNameModified) {
            return true;
        }

        if (lastName == null) {
            return false;
        }

        return !lastName.trim().isEmpty();
    }

    private boolean isEmailValid(String email) {
        if (!isEmailModified) {
            return true;
        }

        if (email == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        if (!isPasswordModified) {
            return true;
        }

        return password != null && password.trim().length() > 5;
    }

    private boolean isConfirmPasswordValid(String password, String confirmPassword) {
        if (!isConfirmPasswordModified) {
            return true;
        }

        if (isPasswordValid(password)) {
            return confirmPassword.equals(password);
        }

        return false;
    }
}