package com.example.turnofast.request;

import android.util.Log;


import com.example.turnofast.modelos.Horario2;
import com.example.turnofast.modelos.Login;
import com.example.turnofast.modelos.Msj;
import com.example.turnofast.modelos.Prestacion;
import com.example.turnofast.modelos.Rubro;
import com.example.turnofast.modelos.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
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

        @POST("prestaciones")
        Call<Prestacion> registrarPrestacion(@Header("Authorization") String token, @Body Prestacion prestacion);

        @POST("horarios")
        Call<Msj> cargarHorario(@Header("Authorization") String token, @Body Horario2 p);

        @GET("horarios/{id}/{nrodia}")
        Call<Horario2> recuperarHorarios(@Header("Authorization") String token, @Path("id") int prestacionId, @Path("nrodia") int nrodia);
/*
        @POST("inmuebles")
        Call<InmuebleFoto> altaInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);

        @PUT("inmuebles")
        Call<Inmueble> actualizarInmueble(@Header("Authorization") String token, @Body Inmueble i);



        @GET("tiposinmuebles")
        Call<ArrayList<TipoInmueble>> tiposInmuebles(@Header("Authorization") String token);

        @GET("contratos/{id}")
        Call<ArrayList<Contrato>> listarContratos(@Header("Authorization") String token, @Path("id") int InmuebleId);

        @DELETE("inmuebles/{id}")
        Call<Mensaje> eliminarInmueble(@Header("Authorization") String token, @Path("id") int InmuebleId);

        //listarClientes.php
        //@GET("listarClientes.php")
        //Call<List<Cliente>> getClientes();

        //@GET("insertarClientes.php")
        //Call<Cliente> createCliente(@Query("dni") int dni, @Query("apellido") String apellido, @Query("nombre") String nombre);

         */
    }
}
