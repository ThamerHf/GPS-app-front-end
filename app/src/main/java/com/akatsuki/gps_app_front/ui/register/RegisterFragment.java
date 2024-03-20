package com.akatsuki.gps_app_front.ui.register;

import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akatsuki.gps_app_front.MainActivity;
import com.akatsuki.gps_app_front.R;
import com.akatsuki.gps_app_front.databinding.FragmentRegisterBinding;
import com.akatsuki.gps_app_front.ui.login.LoginFragment;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;
    private FragmentRegisterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerViewModel = new ViewModelProvider(this,
                new RegisterViewModelFactory(requireContext()))
                .get(RegisterViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText firstNameEditText = binding.firstName;
        final EditText lastNameEditText = binding.lastName;
        final EditText emailEditText = binding.email;
        final EditText passwordEditText = binding.password;
        final EditText confirmPasswordEditText = binding.confirmPassword;
        final Button registerButton = binding.register;
        final ProgressBar loadingProgressBar = binding.loading;
        final Button switchLoginButton = binding.switchLoginButton;
        final LinearLayout backButton = binding.backButton;

        switchLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragment(new LoginFragment());
            }
        });

        backButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        }));

        registerViewModel.getRegisterFormState().observe(getViewLifecycleOwner(),
                new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                registerButton.setEnabled(registerFormState.isDataValid());

                int buttonColor = registerButton.isEnabled() ? Color.parseColor("#66BF81") :
                        /* Couleur par défaut */ Color.GRAY;
                registerButton.setBackgroundColor(buttonColor);
                if (registerFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getFirstNameError() != null) {
                    firstNameEditText.setError(getString(registerFormState.getFirstNameError()));
                }
                if (registerFormState.getLastNameError() != null) {
                    lastNameEditText.setError(getString(registerFormState.getLastNameError()));
                }
                if (registerFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registerFormState.getEmailError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getConfirmPasswordError() != null) {
                    confirmPasswordEditText.setError(getString(registerFormState.getConfirmPasswordError()));
                }
            }
        });


        registerViewModel.getRegisterResult().observe(getViewLifecycleOwner(), new Observer<RegisterResult>() {
            @Override
            public void onChanged(@Nullable RegisterResult registerResult) {
                if (registerResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (registerResult.getError() != null) {
                    showLoginFailed(registerResult.getError());
                }
                if (registerResult.getSuccess() != null) {
                    updateUiWithUser(registerResult.getSuccess());
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.registerDataChanged(
                        usernameEditText.getText().toString(),
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        confirmPasswordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        firstNameEditText.addTextChangedListener(afterTextChangedListener);
        lastNameEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registerViewModel.register(firstNameEditText.getText().toString(),
                            lastNameEditText.getText().toString(),
                            usernameEditText.getText().toString(),
                            emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                registerViewModel.register(firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        usernameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(RegisteredUserView model) {
        String welcome = getString(R.string.welcome) + model.getMessage();
        // TODO : initiate successful logged in experience
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(getContext().getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        }
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void switchToFragment(Fragment fragment) {
        FragmentTransaction transaction = requireActivity()
                .getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_register_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}