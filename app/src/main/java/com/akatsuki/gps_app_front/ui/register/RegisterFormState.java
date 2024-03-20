package com.akatsuki.gps_app_front.ui.register;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class RegisterFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer firstNameError;
    @Nullable
    private Integer lastNameError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer confirmPasswordError;
    private boolean isDataValid;

    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer firstNameError,
                      @Nullable Integer lastNameError, @Nullable Integer emailError,
                      @Nullable Integer passwordError, @Nullable Integer confirmPasswordError) {
        this.usernameError = usernameError;
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.emailError = emailError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
        this.isDataValid = false;
    }

    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.firstNameError = null;
        this.lastNameError = null;
        this.emailError = null;
        this.passwordError = null;
        this.confirmPasswordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getFirstNameError() {
        return firstNameError;
    }

    @Nullable
    public Integer getLastNameError() {
        return lastNameError;
    }

    @Nullable
    public Integer getEmailError() {
        return emailError;
    }

    @Nullable
    public Integer getConfirmPasswordError() {
        return confirmPasswordError;
    }

    boolean isDataValid() {
        return isDataValid || allErrorNull();
    }

    private boolean allErrorNull() {
        return (usernameError == null) && (passwordError == null) && (firstNameError == null)
                && (lastNameError == null) && (confirmPasswordError == null) && (emailError == null);
    }
}