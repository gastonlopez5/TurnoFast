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

import com.example.turnofast.modelos.Horario2;
import com.example.turnofast.modelos.Msj;
import com.example.turnofast.modelos.Turno;
import com.example.turnofast.request.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisTurnosViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<ArrayList<Turno>> listaTurnos;
    private MutableLiveData<String> sinTurnos;
    private String msj = "No hay turnos agendados!";

    public MisTurnosViewModel(@NonNull Application application) {
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

    public void eventosPorMes(String mes, String anio){
        Call<ArrayList<Turno>> dato= ApiClient.getMyApiClient().obtenerTurnosPorMes(obtenerToken(), mes, anio);
        dato.enqueue(new Callback<ArrayList<Turno>>() {
            @Override
            public void onResponse(Call<ArrayList<Turno>> call, Response<ArrayList<Turno>> response) {
                if (response.isSuccessful()){
                    listaTurnos.setValue(response.body());
                } else {
                    sinTurnos.setValue(msj);
                    Log.d("salida",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Turno>> call, Throwable t) {
                Toast.makeText(context, "Error onFailure!", Toast.LENGTH_LONG).show();
                Log.d("salida",t.getMessage());
            }
        });
    }

    public void turnoSolicitadosPorMes(String mes, String anio){
        Call<ArrayList<Turno>> dato= ApiClient.getMyApiClient().obtenerTurnosSolicitadosPorMes(obtenerToken(), mes, anio);
        dato.enqueue(new Callback<ArrayList<Turno>>() {
            @Override
            public void onResponse(Call<ArrayList<Turno>> call, Response<ArrayList<Turno>> response) {
                if (response.isSuccessful()){
                    listaTurnos.setValue(response.body());
                } else {
                    sinTurnos.setValue(msj);
                    Log.d("salida",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Turno>> call, Throwable t) {
                Toast.makeText(context, "Error onFailure!", Toast.LENGTH_LONG).show();
                Log.d("salida",t.getMessage());
            }
        });
    }

    public void turnosPorDia(String fecha){
        Call<ArrayList<Turno>> dato= ApiClient.getMyApiClient().recuperarTurnosPorFecha(obtenerToken(), fecha);
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
                Toast.makeText(context, "ErrorOnFailure!", Toast.LENGTH_LONG).show();
                Log.d("salida",t.getMessage());
            }
        });
    }

    public void turnosSolicitadosPorDia(String fecha){
        Call<ArrayList<Turno>> dato= ApiClient.getMyApiClient().recuperarTurnosSolicitadosPorDia(obtenerToken(), fecha);
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
                Toast.makeText(context, "ErrorOnFailure!", Toast.LENGTH_LONG).show();
                Log.d("salida",t.getMessage());
            }
        });
    }

    public void cancelarTurno(int turnoId){
        Call<Msj> dato= ApiClient.getMyApiClient().cancelarTurno(obtenerToken(), turnoId);
        dato.enqueue(new Callback<Msj>() {
            @Override
            public void onResponse(Call<Msj> call, Response<Msj> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, response.body().getMensaje(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Hubo un problema y no se pudo eliminar el turno!", Toast.LENGTH_LONG).show();
                    Log.d("salida",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Msj> call, Throwable t) {
                Toast.makeText(context, "Error en onFailure", Toast.LENGTH_LONG).show();
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