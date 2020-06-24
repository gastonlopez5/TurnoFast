package com.example.turnofast.ui.servicio;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.turnofast.request.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaPrestacionesViewModel extends AndroidViewModel {
    private Context context;
    MutableLiveData<ArrayList<Servicio>> listaServicios;

    public ListaPrestacionesViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<ArrayList<Servicio>> getListaServicios(){
        if (listaServicios==null){
            listaServicios = new MutableLiveData<>();
        }
        return listaServicios;
    }

    public void cargarDatos() {
        Call<ArrayList<Servicio>> dato = ApiClient.getMyApiClient().obtenerServicios(obtenerToken());
        dato.enqueue(new Callback<ArrayList<Servicio>>() {
            @Override
            public void onResponse(Call<ArrayList<Servicio>> call, Response<ArrayList<Servicio>> response) {
                if (response.isSuccessful()) {
                    listaServicios.setValue(response.body());
                } else {
                    Log.d("salida", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Servicio>> call, Throwable t) {
                Log.d("salida", t.getMessage());
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
