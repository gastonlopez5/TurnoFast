package com.example.turnofast.ui.turno;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.turnofast.modelos.Msj;
import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.modelos.Rubro;
import com.example.turnofast.modelos.Turno;
import com.example.turnofast.request.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitarTurnosViewModel extends AndroidViewModel {
    Context context;
    MutableLiveData<ArrayList<Turno>> listaTurnos;
    MutableLiveData<String> sinTurnos;
    String msj = "No hay turnos disponibles!";

    public SolicitarTurnosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<ArrayList<Turno>> getListaTurnos(){
        if (listaTurnos == null){
            listaTurnos = new MutableLiveData<>();
        }
        return listaTurnos;
    }

    public LiveData<String> getSinTurnos(){
        if (sinTurnos == null){
            sinTurnos = new MutableLiveData<>();
        }
        return sinTurnos;
    }

    public void cargarTurnosDisponibles(int prestacionId, int nroDia, String fecha){
        Call<ArrayList<Turno>> dato= ApiClient.getMyApiClient().obtenerTurnosDisponibles(obtenerToken(), prestacionId, nroDia, fecha);
        dato.enqueue(new Callback<ArrayList<Turno>>() {
            @Override
            public void onResponse(Call<ArrayList<Turno>> call, Response<ArrayList<Turno>> response) {
                if (response.isSuccessful()){
                    listaTurnos.setValue(response.body());
                } else {
                    sinTurnos.setValue(msj);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Turno>> call, Throwable t) {
                Toast.makeText(context, "Error onFailure!", Toast.LENGTH_LONG).show();
                Log.d("salida",t.getMessage());
            }
        });

    }

    public void agregarTurno(Turno turno) {
        Call<Msj> dato= ApiClient.getMyApiClient().cargarTurno(obtenerToken(), turno);
        dato.enqueue(new Callback<Msj>() {
            @Override
            public void onResponse(Call<Msj> call, Response<Msj> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, response.body().getMensaje(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Error onResponse!", Toast.LENGTH_LONG).show();
                    Log.d("salida",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Msj> call, Throwable t) {
                Toast.makeText(context, "Error onFailure!", Toast.LENGTH_LONG).show();
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
