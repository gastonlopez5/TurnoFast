package com.example.turnofast.ui.registro;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.turnofast.modelos.Usuario;
import com.example.turnofast.request.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class RegistroViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Bitmap> foto;

    public RegistroViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Bitmap> getFoto(){
        if (foto == null){
            foto = new MutableLiveData<>();
        }
        return foto;
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

    public void cargarImagen(int requestCode, int resultCode, @Nullable Intent data){
        if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = context.getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

            //Rutina para optimizar la foto,
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.PNG,100, baos);
            foto.setValue(selectedImage);
        }
    }
}
