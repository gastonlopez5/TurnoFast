package com.example.turnofast.ui.servicio;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class PrestacionTurnosViewModel extends AndroidViewModel {
    private Context context;

    public PrestacionTurnosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }


}
