package com.example.turnofast.ui.prestacion;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.turnofast.modelos.Msj;
import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.request.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class PrestacionViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Prestacion> prestacionMLD;
    private MutableLiveData<Bitmap> foto;
    private MutableLiveData<Prestacion> error;

    public PrestacionViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Prestacion> getError(){
        if (error==null){
            error = new MutableLiveData<>();
        }
        return error;
    }

    public LiveData<Prestacion> getPrestacionMLD(){
        if (prestacionMLD == null){
            prestacionMLD = new MutableLiveData<>();
        }
        return prestacionMLD;
    }

    public LiveData<Bitmap> getFoto(){
        if (foto == null){
            foto = new MutableLiveData<>();
        }
        return foto;
    }



    public void agregarPrestacion(Prestacion prestacion) {
        if (prestacion.getLogo() != null){
            Call<Msj> dato= ApiClient.getMyApiClient().registrarPrestacion(obtenerToken(), prestacion);
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
        else {
            error.setValue(prestacion);
        }

    }

    public void recuperarPrestación(int prestacionId){
        Call<Prestacion> dato= ApiClient.getMyApiClient().recuperarPrestacion(obtenerToken(), prestacionId);
        dato.enqueue(new Callback<Prestacion>() {
            @Override
            public void onResponse(Call<Prestacion> call, Response<Prestacion> response) {
                if (response.isSuccessful()){
                    prestacionMLD.setValue(response.body());
                } else {
                    Toast.makeText(context, "Error onResponse!", Toast.LENGTH_LONG).show();
                    Log.d("salida",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Prestacion> call, Throwable t) {
                Toast.makeText(context, "Error onFailure!", Toast.LENGTH_LONG).show();
                Log.d("salida",t.getMessage());
            }
        });
    }

    public void actualizarPrestacion(Prestacion p){
        Call<Msj> dato= ApiClient.getMyApiClient().actualizarPrestacion(obtenerToken(), p);
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

    public void eliminarPrestacion(int prestacionId){
        Call<Msj> dato= ApiClient.getMyApiClient().eliminarPrestacion(obtenerToken(), prestacionId);
        dato.enqueue(new Callback<Msj>() {
            @Override
            public void onResponse(Call<Msj> call, Response<Msj> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, response.body().getMensaje(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, response.body().getMensaje(), Toast.LENGTH_LONG).show();
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

    private String obtenerToken(){
        SharedPreferences sp = context.getSharedPreferences("token",0);
        String token = sp.getString("token","-1");
        String tokenFull = "Bearer " + token;

        return  tokenFull;
    }
}
