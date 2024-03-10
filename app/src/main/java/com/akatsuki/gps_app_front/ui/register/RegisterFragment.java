package com.akatsuki.gps_app_front.ui.register;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.akatsuki.gps_app_front.R;
import com.akatsuki.gps_app_front.databinding.FragmentLoginBinding;
import com.akatsuki.gps_app_front.databinding.FragmentRegisterBinding;
import com.akatsuki.gps_app_front.ui.login.LoginFragment;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;

    private FragmentRegisterBinding binding;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final ProgressBar loadingProgressBar = binding.loading;
        final Button switchLoginButton = binding.switchLoginButton;

        switchLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragment(new LoginFragment());
            }
        });
    }

    private void switchToFragment(Fragment fragment) {
        FragmentTransaction transaction = requireActivity()
                .getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}