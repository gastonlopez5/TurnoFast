package com.tuempresa.turnofast.ui.prestacion;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tuempresa.turnofast.modelos.Prestacion;
import com.tuempresa.turnofast.request.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaPrestacionesViewModel extends AndroidViewModel {
    private Context context;
    MutableLiveData<ArrayList<Prestacion>> listaPrestaciones;
    MutableLiveData<String> sinPrestaciones;
    private String msj = "No hay prestaciones cargadas para la categoría seleccionada!";

    public ListaPrestacionesViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<ArrayList<Prestacion>> getListaPrestaciones(){
        if (listaPrestaciones==null){
            listaPrestaciones = new MutableLiveData<>();
        }
        return listaPrestaciones;
    }

    public LiveData<String> getSinPrestaciones(){
        if (sinPrestaciones == null){
            sinPrestaciones = new MutableLiveData<>();
        }
        return sinPrestaciones;
    }

    public void cargarDatos() {
        Call<ArrayList<Prestacion>> dato = ApiClient.getMyApiClient().obtenerPrestaciones(obtenerToken());
        dato.enqueue(new Callback<ArrayList<Prestacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Prestacion>> call, Response<ArrayList<Prestacion>> response) {
                if (response.isSuccessful()) {
                    listaPrestaciones.setValue(response.body());
                } else {
                    Log.d("salida", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Prestacion>> call, Throwable t) {
                Log.d("salida", t.getMessage());
            }
        });
    }

    public void cargarPrestacionesDisponibles(int categoriaId) {
        Call<ArrayList<Prestacion>> dato = ApiClient.getMyApiClient().recuperarPrestacionesDisponibles(obtenerToken(), categoriaId);
        dato.enqueue(new Callback<ArrayList<Prestacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Prestacion>> call, Response<ArrayList<Prestacion>> response) {
                if (response.isSuccessful()) {
                    listaPrestaciones.setValue(response.body());
                } else {
                    sinPrestaciones.setValue(msj);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Prestacion>> call, Throwable t) {
                Toast.makeText(context, "Error onFailure!", Toast.LENGTH_LONG).show();
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
