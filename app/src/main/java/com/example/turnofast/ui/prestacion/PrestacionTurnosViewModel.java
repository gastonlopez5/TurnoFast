package com.example.turnofast.ui.prestacion;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.turnofast.MainActivity;
import com.example.turnofast.modelos.Horario2;
import com.example.turnofast.modelos.Msj;
import com.example.turnofast.request.ApiClient;
import com.example.turnofast.ui.login.LoginActivity;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrestacionTurnosViewModel extends AndroidViewModel {
    private Context context;
    MutableLiveData<Horario2> errorCarga;

    public PrestacionTurnosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Horario2> getErrorCarga(){
        if (errorCarga == null){
            errorCarga = new MutableLiveData<>();
        }
        return errorCarga;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cargarHorario(Horario2 p, boolean turnoManiana, boolean turnoTarde){
        boolean bandera = true;

        if (turnoManiana) {
            if (p.getHoraDesdeManiana() == null || p.getHoraHastaManiana() == null){

                Toast.makeText(context, "Complete todos los campos del turno mañana!", Toast.LENGTH_LONG).show();
                errorCarga.setValue(p);

                bandera = false;

            } else if (p.getHoraDesdeManiana().isAfter(p.getHoraHastaManiana())){

                Toast.makeText(context, "Alguno de los campos del turno mañna es incorrecto!", Toast.LENGTH_LONG).show();
                errorCarga.setValue(p);

                bandera = false;

            }
        }

        if (turnoTarde) {
            if (p.getHoraDesdeTarde() == null || p.getHoraHastaTarde() == null) {

                Toast.makeText(context, "Complete todos los campos del turno tarde!", Toast.LENGTH_LONG).show();
                errorCarga.setValue(p);

                bandera = false;

            } else if (p.getHoraDesdeTarde().isAfter(p.getHoraHastaTarde())) {

                Toast.makeText(context, "Alguno de los campos del turno tarde es incorrecto!", Toast.LENGTH_LONG).show();
                errorCarga.setValue(p);

                bandera = false;
            }
        }

        if (bandera){
            //Gson gson = new Gson();
            //String JSON = gson.toJson(p);
            Call<Msj> dato= ApiClient.getMyApiClient().cargarHorario(obtenerToken(), p);
            dato.enqueue(new Callback<Msj>() {
                @Override
                public void onResponse(Call<Msj> call, Response<Msj> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, response.body().getMensaje(), Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("salida",response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<Msj> call, Throwable t) {
                    Log.d("salida",t.getMessage());
                }
            });
        }
    }

    public void recuperarHorarios(int prestacionId){
        Call<Horario2> dato= ApiClient.getMyApiClient().recuperarHorarios(obtenerToken(), prestacionId);
        dato.enqueue(new Callback<Horario2>() {
            @Override
            public void onResponse(Call<Horario2> call, Response<Horario2> response) {
                if (response.isSuccessful()){
                    Horario2 h = response.body();
                    if (h != null){
                        errorCarga.setValue(h);
                    } else {
                        Toast.makeText(context, "No tiene horarios cargados la prestacion seleccionada!", Toast.LENGTH_LONG).show();
                        Intent logeo = new Intent(context, MainActivity.class);
                        context.startActivity(logeo);
                    }
                } else {
                    Log.d("salida",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Horario2> call, Throwable t) {
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
