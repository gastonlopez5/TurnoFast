package com.tuempresa.turnofast.request;

import android.util.Log;

import com.tuempresa.turnofast.modelos.Feriado;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;


public class ApiClientFeriados {
    private static MyApiInterfaceFeriados myApiInterfaceFeriados;


    public static MyApiInterfaceFeriados getMyApiClientFeriados(){
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://nolaborables.com.ar/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        myApiInterfaceFeriados=retrofit.create(MyApiInterfaceFeriados.class);
        Log.d("salida",retrofit.baseUrl().toString());
        return myApiInterfaceFeriados;
    }

    public interface MyApiInterfaceFeriados {

        @GET("feriados/{anio}")
        Call<List<Feriado>> obtenerFeriados(@Header("Authorization") String token, @Path("anio") int anio);

    }
}
