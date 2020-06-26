package com.example.turnofast.ui.servicio;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.modelos.Rubro;
import com.example.turnofast.request.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrestacionTurnosViewModel extends AndroidViewModel {
    private Context context;
    private int nro = 1;
    MutableLiveData<Prestacion> errorCarga;

    public PrestacionTurnosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Prestacion> getErrorCarga(){
        if (errorCarga == null){
            errorCarga = new MutableLiveData<>();
        }
        return errorCarga;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cargarTurnos(Prestacion p, boolean turnoManiana, boolean turnoTarde){
        if (turnoManiana) {
            if (p.getFechaInicioManiana() == null || p.getFechaFinManiana() == null || p.getHoraInicioManiana() == null
                    || p.getHoraFinManiana() == null){

                Toast.makeText(context, "Complete todos los campos del turno mañana!", Toast.LENGTH_LONG).show();
                nro = nro + 1;
                errorCarga.postValue(p);

            } else if (p.getFechaInicioManiana().isAfter(p.getFechaFinManiana())
                    || p.getHoraInicioManiana().isAfter(p.getHoraFinManiana())){

                Toast.makeText(context, "Alguno de los campos del turno mañna es incorrecto!", Toast.LENGTH_LONG).show();
                nro = nro + 1;
                errorCarga.postValue(p);

            }
        }

        if (turnoTarde) {
            if (p.getFechaFinTarde() == null || p.getFechaInicioTarde() == null
                    || p.getHoraInicioTarde() == null || p.getHoraFinTarde() == null) {

                Toast.makeText(context, "Complete todos los campos del turno tarde!", Toast.LENGTH_LONG).show();
                nro = nro + 1;
                errorCarga.postValue(p);


            } else if ( p.getFechaInicioTarde().isAfter(p.getFechaFinTarde())
                    || p.getHoraInicioTarde().isAfter(p.getHoraFinTarde())) {

                Toast.makeText(context, "Alguno de los campos del turno tarde es incorrecto!", Toast.LENGTH_LONG).show();
                nro = nro + 1;
                errorCarga.postValue(p);

            }
        }


        Call<Prestacion> dato= ApiClient.getMyApiClient().actualizar(obtenerToken(), p);
            dato.enqueue(new Callback<Prestacion>() {
                @Override
                public void onResponse(Call<Prestacion> call, Response<Prestacion> response) {
                    if (response.isSuccessful()){
                        Log.d("salida",response.body().toString());
                    } else {
                        Log.d("salida",response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<Prestacion> call, Throwable t) {
                    Log.d("salida",t.getMessage());
                }
            });
        }

    private String obtenerToken(){
        SharedPreferences sp = context.getSharedPreferences("token",0);
        String token = sp.getString("token","-1");
        String tokenFull = "Bearer " + token;

        return  tokenFull;
    }
}
