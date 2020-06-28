package com.example.turnofast.ui.prestacion;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.turnofast.modelos.Horario;
import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrestacionTurnosViewModel extends AndroidViewModel {
    private Context context;
    private int nro = 1;
    MutableLiveData<Horario> errorCarga;

    public PrestacionTurnosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Horario> getErrorCarga(){
        if (errorCarga == null){
            errorCarga = new MutableLiveData<>();
        }
        return errorCarga;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cargarHorario(Horario p, boolean turnoManiana, boolean turnoTarde){
        if (turnoManiana) {
            if (p.getHoraDesdeManiana() == null || p.getHoraHastaManiana() == null){

                Toast.makeText(context, "Complete todos los campos del turno mañana!", Toast.LENGTH_LONG).show();
                nro = nro + 1;
                errorCarga.postValue(p);

            } else if (p.getHoraDesdeManiana().isAfter(p.getHoraHastaManiana())){

                Toast.makeText(context, "Alguno de los campos del turno mañna es incorrecto!", Toast.LENGTH_LONG).show();
                nro = nro + 1;
                errorCarga.postValue(p);

            }
        }

        if (turnoTarde) {
            if (p.getHoraDesdeTarde() == null || p.getHoraHastaTarde() == null) {

                Toast.makeText(context, "Complete todos los campos del turno tarde!", Toast.LENGTH_LONG).show();
                nro = nro + 1;
                errorCarga.postValue(p);


            } else if (p.getHoraDesdeTarde().isAfter(p.getHoraHastaTarde())) {

                Toast.makeText(context, "Alguno de los campos del turno tarde es incorrecto!", Toast.LENGTH_LONG).show();
                nro = nro + 1;
                errorCarga.postValue(p);
            }
        }


        Call<Horario> dato= ApiClient.getMyApiClient().cargarHorario(obtenerToken(), p);
            dato.enqueue(new Callback<Horario>() {
                @Override
                public void onResponse(Call<Horario> call, Response<Horario> response) {
                    if (response.isSuccessful()){
                        Log.d("salida",response.body().toString());
                    } else {
                        Log.d("salida",response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<Horario> call, Throwable t) {
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
