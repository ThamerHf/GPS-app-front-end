package com.akatsuki.gps_app_front.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.akatsuki.gps_app_front.LoginRegisterActivity;
import com.akatsuki.gps_app_front.R;
import com.akatsuki.gps_app_front.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private boolean isAuthentified = false;
    private boolean editMode = false;
    private FragmentProfileBinding binding;

    public boolean getAuthentified() {
        return isAuthentified;
    }

    public void setAuthentified(boolean authentified) {
        this.isAuthentified = false;
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

        this.setAuthentified(true);

        if(this.getAuthentified()) {
            binding.avatar.setVisibility(View.VISIBLE);
            binding.username.setVisibility(View.VISIBLE);
            binding.card.setVisibility(View.VISIBLE);
            binding.userProfileButton.setVisibility(View.VISIBLE);
            binding.notAuthUserPage.setVisibility(View.GONE);
        } else {
            binding.avatar.setVisibility(View.GONE);
            binding.username.setVisibility(View.GONE);
            binding.card.setVisibility(View.GONE);
            binding.userProfileButton.setVisibility(View.GONE);
            binding.notAuthUserPage.setVisibility(View.VISIBLE);
        }

        binding.avatar.setImageResource(R.drawable.user_default_avatar);

        final ImageButton editProfile = binding.editProfile;

       editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditMode(true);
                chageToEditMode();
            }
        });

       final Button saveProfile = binding.saveProfile;

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditMode(false);
                chageToEditMode();
            }
        });


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

    private void chageToEditMode() {
        if(this.isEditMode()) {
            binding.editProfile.setVisibility(View.GONE);
            binding.avatar.setClickable(true);
            binding.saveProfile.setVisibility(View.VISIBLE);
            binding.logout.setVisibility(View.GONE);
            binding.username.setEnabled(true);
            binding.yourName.setEnabled(true);
            binding.email.setEnabled(true);
        } else {
            binding.editProfile.setVisibility(View.VISIBLE);
            binding.avatar.setClickable(false);
            binding.saveProfile.setVisibility(View.GONE);
            binding.logout.setVisibility(View.VISIBLE);
            binding.username.setEnabled(false);
            binding.yourName.setEnabled(false);
            binding.email.setEnabled(false);
        }
    }

}