package com.example.turnofast.request;

import android.util.Log;


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

import static com.example.turnofast.MainActivity.PATH;

public class ApiClient {
    private static  MyApiInterface myApiInteface;

    public static MyApiInterface getMyApiClient(){
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(PATH + "/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        myApiInteface=retrofit.create(MyApiInterface.class);
        Log.d("salida",retrofit.baseUrl().toString());
        return myApiInteface;
    }

    public interface MyApiInterface {

        @POST("usuarios/registrar")
        Call<Usuario> registrarUsuario(@Body Usuario user);

        @POST("usuarios/login")
        Call<String> login(@Body Login login);

        @GET("usuarios/perfil")
        Call<Usuario> perfil(@Header("Authorization") String token);

        @PUT("usuarios")
        Call<Usuario> actualizar(@Header("Authorization") String token, @Body Usuario p);

        @GET("rubros")
        Call<ArrayList<Rubro>> obtenerRubros(@Header("Authorization") String token);

        @GET("prestaciones")
        Call<ArrayList<Prestacion>> obtenerPrestaciones(@Header("Authorization") String token);

        @GET("prestaciones/{id}")
        Call<Prestacion> recuperarPrestacion(@Header("Authorization") String token, @Path("id") int prestacionId);

        @GET("prestaciones/disponibles/{categoriaId}")
        Call<ArrayList<Prestacion>> recuperarPrestacionesDisponibles(@Header("Authorization") String token, @Path("categoriaId") int categoriId);

        @POST("prestaciones")
        Call<Msj> registrarPrestacion(@Header("Authorization") String token, @Body Prestacion prestacion);

        @PUT("prestaciones")
        Call<Msj> actualizarPrestacion(@Header("Authorization") String token, @Body Prestacion p);

        @DELETE("prestaciones/{id}")
        Call<Msj> eliminarPrestacion(@Header("Authorization") String token, @Path("id") int prestacionId);

        @POST("horarios")
        Call<Msj> cargarHorario(@Header("Authorization") String token, @Body Horario2 p);

        @GET("horarios/{id}/{nrodia}")
        Call<Horario2> recuperarHorarios(@Header("Authorization") String token, @Path("id") int prestacionId, @Path("nrodia") int nrodia);

        @DELETE("horarios/{id}/{nrodia}")
        Call<Msj> eliminarHorario(@Header("Authorization") String token, @Path("id") int prestacionId, @Path("nrodia") int nrodia);

        @PUT("horarios")
        Call<Msj> actualizarHorario(@Header("Authorization") String token, @Body Horario2 p);

        @GET("turnos/{prestacionid}/{nrodia}/{fecha}")
        Call<ArrayList<Turno>> obtenerTurnosDisponibles(@Header("Authorization") String token, @Path("prestacionid") int prestacionId, @Path("nrodia") int nrodia, @Path("fecha") String fecha);

        @POST("turnos")
        Call<Msj> cargarTurno(@Header("Authorization") String token, @Body Turno p);

        @GET("turnos/pormes/{mes}/{anio}")
        Call<ArrayList<Turno>> obtenerTurnosPorMes(@Header("Authorization") String token, @Path("mes") String mes, @Path("anio") String anio);

        @GET("turnos/pordia/{fecha}")
        Call<ArrayList<Turno>> recuperarTurnosPorFecha(@Header("Authorization") String token, @Path("fecha") String fecha);
    }
}
