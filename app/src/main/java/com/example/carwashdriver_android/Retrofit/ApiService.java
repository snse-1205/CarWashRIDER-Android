package com.example.carwashdriver_android.Retrofit;

import com.example.carwashdriver_android.Models.ToDoModel;
import com.example.carwashdriver_android.Models.UsuarioModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("auth/empleado")
    Call<UsuarioModel> login(
            @Body HashMap<String,String> body
    );
    @GET("auth/verificar")
    Call<Void> verifiarSesion(  @Header("Authorization") String token);

    @GET("trabajos/faltantes")
    Call<List<ToDoModel>> obtenerTrabajosPendientes(
            @Header("Authorization") String token
    );
}
