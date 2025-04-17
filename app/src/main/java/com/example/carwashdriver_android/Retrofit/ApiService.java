package com.example.carwashdriver_android.Retrofit;

import com.example.carwashdriver_android.Models.DetailsModel;
import com.example.carwashdriver_android.Models.ToDoModel;
import com.example.carwashdriver_android.Models.UsuarioModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @PUT("trabajos/empezar/{id}")
    Call<Void> empezarTrabajo(
            @Header("Authorization") String token,
            @Path("id") int idTrabajo
    );

    @GET("trabajos/infoGeneral/{id}")
    Call<DetailsModel>obtenerDatosGeneralesTrabajo(
            @Header("Authorization") String token,
            @Path("id") int idCotizacion
    );

    @GET("trabajos/infoDetalles/{id}")
    Call<List<DetailsModel>>obtenerDetallesTrabajo(
            @Header("Authorization") String token,
            @Path("id") int idCotizacion
    );

    @PUT("trabajos/check/{id}&{idCotizacion}")
    Call<Void> marcarTrabajo(
            @Header("Authorization") String token,
            @Path("id") int idTrabajo,
            @Path("idCotizacion") int idCotizacion
            );

    @Multipart
    @POST("trabajos/evidencia")
    Call<Void> subirEvidencia(
            @Header("Authorization") String token,
            @Part MultipartBody.Part file,
            @Part("idTrabajo") RequestBody idTrabajo,
            @Part("idCotizacion") RequestBody idCotizacion,
            @Part("nota") RequestBody nota
    );

}
