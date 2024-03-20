package com.akatsuki.gps_app_front.ui.location;

import android.telecom.Call;
import android.widget.Button;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LocationViewModel extends ViewModel {
    private final MutableLiveData<String> editText1Text = new MutableLiveData<>();
    private final MutableLiveData<String> editText2Text = new MutableLiveData<>();

    public LocationViewModel() {
        // Set initial values for EditTexts
        editText1Text.setValue("Yacine");
        editText2Text.setValue("XXX");
    }

    // Setters for EditTexts
    public void setEditText1Text(String text) {
        editText1Text.setValue(text);
    }

    public void setEditText2Text(String text) {
        editText2Text.setValue(text);
    }

    // Getters for EditTexts
    public LiveData<String> getEditText1Text() {
        return editText1Text;
    }

    public LiveData<String> getEditText2Text() {
        return editText2Text;
    }

    // Action listeners for buttons
    public void onDeleteButtonClick() {

    }
    private int getLocationIdToDelete() {
        return 0;
    }

    public void onEditButtonClick() {
        // Implement edit button action here
    }
    public void onShareButtonClick() {
        // Implement share button action here
    }
    public void onDownloadButtonClick() {
        // Implement download button action here
    }

}
