package com.example.turnofast.ui.home;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> mText;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        mText = new MutableLiveData<>();
        mText.setValue("Bienvenidos a TurnoFast!!");
    }

    public LiveData<String> getText() {
        return mText;
    }



}