package com.example.turnofast.ui.prestacion;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrestacionViewModel extends AndroidViewModel {
    private Context context;

    public PrestacionViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void agregarPrestacion(Prestacion prestacion) {
        Call<Prestacion> dato= ApiClient.getMyApiClient().registrarPrestacion(obtenerToken(), prestacion);
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
