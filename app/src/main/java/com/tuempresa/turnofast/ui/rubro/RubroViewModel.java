package com.tuempresa.turnofast.ui.rubro;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tuempresa.turnofast.modelos.Rubro;
import com.tuempresa.turnofast.request.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RubroViewModel extends AndroidViewModel {
    private Context context;
    MutableLiveData<ArrayList<Rubro>> listaRubros;

    public RubroViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<ArrayList<Rubro>> getListaRubros(){
        if (listaRubros==null){
            listaRubros = new MutableLiveData<>();
        }
        return listaRubros;
    }

    public void cargarDatos(){
        Call<ArrayList<Rubro>> dato= ApiClient.getMyApiClient().obtenerRubros(obtenerToken());
        dato.enqueue(new Callback<ArrayList<Rubro>>() {
            @Override
            public void onResponse(Call<ArrayList<Rubro>> call, Response<ArrayList<Rubro>> response) {
                if (response.isSuccessful()){
                    listaRubros.setValue(response.body());
                } else {
                    Log.d("salida",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rubro>> call, Throwable t) {
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
