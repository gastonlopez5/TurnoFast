package com.example.turnofast.ui.servicio;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class HorariosDicponiblesViewModel extends AndroidViewModel {
    private Context context;
    MutableLiveData<Boolean> turnoManianaMLD;
    MutableLiveData<Boolean> turnoTardeMLD;

    public HorariosDicponiblesViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<Boolean> getTurnoTardeMLD() {
        if (turnoTardeMLD == null){
            turnoTardeMLD = new MutableLiveData<>();
        }
        return turnoManianaMLD;
    }

    public MutableLiveData<Boolean> getTurnoManianaMLD() {
        if (turnoManianaMLD == null){
            turnoManianaMLD = new MutableLiveData<>();
        }
        return turnoManianaMLD;
    }

    public void setTurnoManianaMLD(Boolean valor){
        turnoManianaMLD.setValue(valor);
    }

    public void setTurnoTardeMLD(Boolean valor){
        turnoTardeMLD.setValue(valor);
    }
}
