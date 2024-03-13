package com.akatsuki.gps_app_front.ui.profile;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.akatsuki.gps_app_front.LoginRegisterActivity;
import com.akatsuki.gps_app_front.MainActivity;
import com.akatsuki.gps_app_front.R;
import com.akatsuki.gps_app_front.databinding.FragmentLocationsBinding;
import com.akatsuki.gps_app_front.databinding.FragmentProfileBinding;
import com.akatsuki.gps_app_front.ui.location.LocationsViewModel;
import com.akatsuki.gps_app_front.ui.login.LoginFragment;
import com.akatsuki.gps_app_front.ui.register.RegisterFragment;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button goLogin = binding.logingo;

        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
            }
        });



        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}