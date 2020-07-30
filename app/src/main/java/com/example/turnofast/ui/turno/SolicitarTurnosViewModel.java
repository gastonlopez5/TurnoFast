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

import com.example.turnofast.modelos.Feriado;
import com.example.turnofast.modelos.Msj;
import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.modelos.Rubro;
import com.example.turnofast.modelos.Turno;
import com.example.turnofast.request.ApiClient;
import com.example.turnofast.request.ApiClientFeriados;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitarTurnosViewModel extends AndroidViewModel {
    Context context;
    MutableLiveData<ArrayList<Turno>> listaTurnos;
    MutableLiveData<List<Feriado>> feriados;
    MutableLiveData<String> sinTurnos;
    String msj = "No hay turnos disponibles!";

    public SolicitarTurnosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Feriado>> getFeriados(){
        if (feriados == null){
            feriados = new MutableLiveData<>();
        }
        return feriados;
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

    public void obtenerFeriados(){
        Calendar c = Calendar.getInstance();
        Call<List<Feriado>> dato= ApiClientFeriados.getMyApiClientFeriados().obtenerFeriados(obtenerToken(), c.get(Calendar.YEAR));
        dato.enqueue(new Callback<List<Feriado>>() {
            @Override
            public void onResponse(Call<List<Feriado>> call, Response<List<Feriado>> response) {
                if (response.isSuccessful()){
                    feriados.setValue(response.body());
                } else {
                    Toast.makeText(context, "Error onResponse!", Toast.LENGTH_LONG).show();
                    Log.d("salida", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Feriado>> call, Throwable t) {
                Toast.makeText(context, "Error onFailure!", Toast.LENGTH_LONG).show();
                Log.d("salida",t.getMessage());
            }
        });
    }

    public void verificarFecha(String fecha){
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH)-1;
        int mes = calendar.get(Calendar.MONTH)+1;
        String[] a = fecha.split("-");
        String diaSelec = a[2];
        String mesSelec = a[1];

        if (Integer.parseInt(diaSelec) < dia && Integer.parseInt(mesSelec) <= mes){
            sinTurnos.setValue(msj);
        }
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
