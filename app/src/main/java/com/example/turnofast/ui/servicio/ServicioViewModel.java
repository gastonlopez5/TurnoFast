package com.example.turnofast.ui.servicio;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.turnofast.modelos.Servicio;
import com.example.turnofast.request.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioViewModel extends AndroidViewModel {
    private Context context;

    public ServicioViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void agregarServicio(Servicio servicio) {
        Call<Servicio> dato= ApiClient.getMyApiClient().registrarServicio(obtenerToken(), servicio);
        dato.enqueue(new Callback<Servicio>() {
            @Override
            public void onResponse(Call<Servicio> call, Response<Servicio> response) {
                if (response.isSuccessful()){
                    Log.d("salida",response.body().toString());
                } else {
                    Log.d("salida",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Servicio> call, Throwable t) {
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
