package com.tuempresa.turnofast.ui.prestacion;

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

import com.tuempresa.turnofast.modelos.Horario2;
import com.tuempresa.turnofast.modelos.Msj;
import com.tuempresa.turnofast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrestacionTurnosViewModel extends AndroidViewModel {
    private Context context;
    MutableLiveData<Horario2> errorCarga;
    MutableLiveData<Horario2> botones;
    boolean bandera = true;

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

    public LiveData<Horario2> getBotones(){
        if (botones == null){
            botones = new MutableLiveData<>();
        }
        return botones;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cargarHorario(Horario2 p, boolean turnoManiana, boolean turnoTarde){
        bandera = true;
        verificar(p, turnoManiana, turnoTarde);

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void verificar(Horario2 p, boolean turnoManiana, boolean turnoTarde) {
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
    }

    public void recuperarHorarios(int prestacionId, int nrodia){
        Call<Horario2> dato= ApiClient.getMyApiClient().recuperarHorarios(obtenerToken(), prestacionId, nrodia);
        dato.enqueue(new Callback<Horario2>() {
            @Override
            public void onResponse(Call<Horario2> call, Response<Horario2> response) {
                if (response.isSuccessful()){
                    Horario2 h = response.body();
                    if (h != null){
                        botones.setValue(response.body());
                        errorCarga.setValue(h);
                    } else {

                        //Intent logeo = new Intent(context, MainActivity.class);
                        //context.startActivity(logeo);
                    }
                } else {
                    Toast.makeText(context, "No tiene horarios cargados para el día seleccionado!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Horario2> call, Throwable t) {
                Log.d("salida",t.getMessage());
            }
        });
    }

    public void borrarHorario(int prestacionId, int nrodia){
        Call<Msj> dato= ApiClient.getMyApiClient().eliminarHorario(obtenerToken(), prestacionId, nrodia);
        dato.enqueue(new Callback<Msj>() {
            @Override
            public void onResponse(Call<Msj> call, Response<Msj> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, response.body().getMensaje(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Hubo un problema y no se pudo eliminar el horario!", Toast.LENGTH_LONG).show();
                    Log.d("salida",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Msj> call, Throwable t) {
                Log.d("salida",t.getMessage());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void actualizar(Horario2 p, boolean turnoManiana, boolean turnoTarde){
        bandera = true;
        verificar(p, turnoManiana, turnoTarde);

        if (bandera){
            Call<Msj> dato= ApiClient.getMyApiClient().actualizarHorario(obtenerToken(), p);
            dato.enqueue(new Callback<Msj>() {
                @Override
                public void onResponse(Call<Msj> call, Response<Msj> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(context, response.body().getMensaje(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Hubo un problema y no se pudo actualizar el horario!", Toast.LENGTH_LONG).show();
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

    private String obtenerToken(){
        SharedPreferences sp = context.getSharedPreferences("token",0);
        String token = sp.getString("token","-1");
        String tokenFull = "Bearer " + token;

        return  tokenFull;
    }

}
