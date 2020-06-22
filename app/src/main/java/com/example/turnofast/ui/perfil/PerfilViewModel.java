package com.example.turnofast.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.turnofast.modelos.Usuario;
import com.example.turnofast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private Context context;
    MutableLiveData<Usuario> usuarioMLD;
    MutableLiveData<String> msgLD;
    private Usuario usuario = null;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Usuario> getUsuarioMLD(){
        if (usuarioMLD==null){
            usuarioMLD = new MutableLiveData<Usuario>();
        }
        return usuarioMLD;
    }

    public LiveData<String> getMsgLD(){
        if (msgLD==null){
            msgLD = new MutableLiveData<String>();
        }
        return msgLD;
    }

    public void cargarUsuario(){
        Call<Usuario> dato= ApiClient.getMyApiClient().perfil(obtenerToken());
        dato.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){
                    Usuario u = response.body();
                    usuarioMLD.postValue(response.body());
                } else {
                    Log.d("salida",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("salida",t.getMessage());
                t.printStackTrace();
            }
        });

    }

    public void actualizarUsuario(Usuario u){
        if (u.getNombre().length() != 0 && u.getApellido().length() != 0 && u.getTelefono().length() != 0
                && u.getEmail().length() != 0){
            Call<Usuario> dato= ApiClient.getMyApiClient().actualizar(obtenerToken(), u);
            dato.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if (response.isSuccessful()){
                        Log.d("salida",response.body().toString());
                    } else {
                        Log.d("salida",response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Log.d("salida",t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            msgLD.postValue("Complete todos los campos!");
        }

    }

    private String obtenerToken(){
        SharedPreferences sp = context.getSharedPreferences("token",0);
        String token = sp.getString("token","-1");
        String tokenFull = "Bearer " + token;

        return  tokenFull;
    }
}
