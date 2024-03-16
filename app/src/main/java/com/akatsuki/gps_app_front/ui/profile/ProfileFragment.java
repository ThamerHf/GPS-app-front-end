package com.akatsuki.gps_app_front.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.akatsuki.gps_app_front.LoginRegisterActivity;
import com.akatsuki.gps_app_front.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private boolean isAuthentified;
    private boolean editMode;
    private FragmentProfileBinding binding;

    public boolean getAuthentified() {
        return isAuthentified;
    }

    public void setAuthentified(boolean authentified) {
        this.isAuthentified = authentified;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }


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