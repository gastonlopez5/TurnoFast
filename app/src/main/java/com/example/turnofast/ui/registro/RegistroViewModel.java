package com.example.turnofast.ui.registro;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.turnofast.modelos.Usuario;
import com.example.turnofast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroViewModel extends AndroidViewModel {
    private Context context;

    public RegistroViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void registrarUsuario(Usuario u){
        if (u.getNombre().length() != 0 && u.getApellido().length() != 0 && u.getClave().length() != 0
        && u.getTelefono().length() != 0 && u.getEmail().length() != 0){
            Call<Usuario> dato= ApiClient.getMyApiClient().registrarUsuario(u);
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
            Toast.makeText(context, "Debe completar todos los campos", Toast.LENGTH_LONG).show();
        }
    }
}
