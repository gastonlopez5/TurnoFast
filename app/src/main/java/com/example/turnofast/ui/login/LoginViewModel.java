package com.example.turnofast.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.turnofast.modelos.Login;
import com.example.turnofast.modelos.Usuario;
import com.example.turnofast.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    MutableLiveData<String> contraseniaVM;
    MutableLiveData<String> emailVM;
    MutableLiveData<String> msg1;
    MutableLiveData<String> msg2;
    private Context context;
    private String error1 = "Usuario o Password incorrectos";

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getContrasenia(){
        if (contraseniaVM == null){
            contraseniaVM = new MutableLiveData<String>();
        }
        return contraseniaVM;
    }

    public LiveData<String> getEmail(){
        if (emailVM == null){
            emailVM = new MutableLiveData<String>();
        }
        return emailVM;
    }

    public LiveData<String> getMsg1(){
        if (msg1 == null){
            msg1 = new MutableLiveData<String>();
        }
        return msg1;
    }

    public LiveData<String> getMsg2(){
        if (msg2 == null){
            msg2 = new MutableLiveData<String>();
        }
        return msg2;
    }

    public void validar(String email, String clave) {
        Login l = new Login(email, clave);
        Call<String> dato= ApiClient.getMyApiClient().login(l);
        dato.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    if(response.body().length()>0) {
                        String token = response.body();

                        SharedPreferences sp = context.getSharedPreferences("token",0);
                        SharedPreferences.Editor editor = sp.edit();

                        editor.putString("token",token);

                        editor.commit();

                        msg1.postValue(token);
                    }
                }else{
                    msg2.postValue(error1);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.d("salida",t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
