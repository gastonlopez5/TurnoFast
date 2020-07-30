package com.example.turnofast.request;

import android.util.Log;

import com.example.turnofast.modelos.Feriado;
import com.example.turnofast.modelos.Horario2;
import com.example.turnofast.modelos.Login;
import com.example.turnofast.modelos.Msj;
import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.modelos.Rubro;
import com.example.turnofast.modelos.Turno;
import com.example.turnofast.modelos.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
