package com.example.carwashdriver_android.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashdriver_android.Adapters.AdapterDetails;
import com.example.carwashdriver_android.Config.ClientManager;
import com.example.carwashdriver_android.Config.SocketManager;
import com.example.carwashdriver_android.Models.DetailsModel;
import com.example.carwashdriver_android.R;
import com.example.carwashdriver_android.Retrofit.ApiService;
import com.example.carwashdriver_android.Retrofit.RetrofitClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallesTrabajo extends AppCompatActivity {


    private MapView mapView;
    private GoogleMap googleMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    Context context = this;

    RecyclerView recycleViewLista;
    TextView cliente,marca,placa,color;
    MaterialCardView btnEmpezarTrabajo, btnTrazarRuta;
    AdapterDetails adapter;

    private boolean isListenerRegistered = false;

    DetailsModel informacionGeneral;
    List<DetailsModel>informacionDetalles = new ArrayList<>();

    private String nota ;
    private int idCotizacion, estado;
    ClientManager clientManager;
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

    private final Emitter.Listener trabajosListener = args -> {
        this.runOnUiThread(() -> {
            Log.d("SOCKET", "Actualización recibida, IniciandoTrabajo...");
            estado = 4;
            adapter.setPuedeInteractuar(true);
            btnEmpezarTrabajo.setVisibility(View.GONE);
            SocketManager.dejarDeEscucharEvento("Trabajos");
        });
    };

    private final Emitter.Listener detallesTrabajosListener = args -> {
        this.runOnUiThread(() -> {
            Log.d("SOCKET", "Actualización recibida, Recuperando cambios en ...");
            actualizarDetalles();
        });
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalles_trabajo);
        idCotizacion = getIntent().getIntExtra("idCotiazcion",0);
        estado = getIntent().getIntExtra("estadoCotizacion",0);
        clientManager = new ClientManager(this);

        if(estado == 4){
            adapter.setPuedeInteractuar(true);
            btnEmpezarTrabajo.setVisibility(View.GONE);
        }else{
            SocketManager.escucharEvento("Trabajo",trabajosListener);
        }

        if (!isListenerRegistered) {
            SocketManager.escucharEvento("Trabajo"+idCotizacion,detallesTrabajosListener);
            isListenerRegistered = true;
        }


        cargarVistas();
        cargarEventos();
        llenarDatosGenerales();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void cargarVistas(){
        recycleViewLista = findViewById(R.id.recycleViewListDetails);
        btnEmpezarTrabajo = findViewById(R.id.itemListCardItemEmpezarTrabajo);
        btnTrazarRuta = findViewById(R.id.itemListCardItemTrazarRuta);
        cliente = findViewById(R.id.NombreClienteDetalle);
        placa = findViewById(R.id.placaCarroDetalles);
        color = findViewById(R.id.colorCarroDetalles);
        marca = findViewById(R.id.marcaCarroDetalles);
    }
    private void cargarEventos(){
        btnTrazarRuta.setOnClickListener(v -> abrirGoogleMapsParaRuta());

        btnEmpezarTrabajo.setOnClickListener(v -> {
            btnEmpezarTrabajo.setClickable(false);
            empezarTrabajo();
        });
    }

    private void abrirGoogleMapsParaRuta() {
        if (informacionGeneral.getLat() != 0.0 && informacionGeneral.getLon() != 0.0) {
            Uri uri = Uri.parse("google.navigation:q=" + informacionGeneral.getLat() + "," + informacionGeneral.getLon() + "&mode=d");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.e("MapScreenActivity", "Google Maps no está instalado");
            }
        } else {
            Log.e("MapScreenActivity", "Coordenadas no válidas para la ruta");
        }
    }

    private void llenarDatosGenerales(){
        String token= "Bearer " + clientManager.getClientToken();
        Call<DetailsModel> call = apiService.obtenerDatosGeneralesTrabajo(token,idCotizacion);
        call.enqueue(new Callback<DetailsModel>() {
            @Override
            public void onResponse(Call<DetailsModel> call, Response<DetailsModel> response) {
                if(response.isSuccessful()){
                    informacionGeneral = response.body();
                    cliente.setText(informacionGeneral.getNombreCLiente());
                    marca.setText(informacionGeneral.getMarca());
                    placa.setText(informacionGeneral.getPlaca());
                    color.setText(informacionGeneral.getColor());

                    llenarDatosDetalles();
                }else{
                    Log.e("Retrofit", "Fallo en la solicitud: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DetailsModel> call, Throwable t) {
                Log.d("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }

    private void llenarDatosDetalles(){
        String token= "Bearer " + clientManager.getClientToken();
        Call<List<DetailsModel>> call = apiService.obtenerDetallesTrabajo(token,idCotizacion);
        call.enqueue(new Callback<List<DetailsModel>>() {
            @Override
            public void onResponse(Call<List<DetailsModel>> call, Response<List<DetailsModel>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        informacionDetalles.addAll(response.body());

                        adapter = new AdapterDetails(informacionDetalles, context, new AdapterDetails.OnDetalleActionListener() {
                            @Override
                            public void onMarcarTrabajo(DetailsModel model) {
                                marcarTrabajo(model.getId());
                            }
                        });
                        recycleViewLista.setLayoutManager((new LinearLayoutManager(context)));
                        recycleViewLista.setAdapter(adapter);
                    }
                }else {
                    Log.e("Retrofit", "Fallo en la solicitud: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<DetailsModel>> call, Throwable t) {
                Log.d("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }
    private void actualizarDetalles() {
        String token = "Bearer " + clientManager.getClientToken();
        Call<List<DetailsModel>> call = apiService.obtenerDetallesTrabajo(token, idCotizacion);
        call.enqueue(new Callback<List<DetailsModel>>() {
            @Override
            public void onResponse(Call<List<DetailsModel>> call, Response<List<DetailsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DetailsModel> nuevosDetalles = response.body();

                    for (int i = 0; i < nuevosDetalles.size(); i++) {
                        DetailsModel nuevo = nuevosDetalles.get(i);

                        for (int j = 0; j < informacionDetalles.size(); j++) {
                            DetailsModel actual = informacionDetalles.get(j);

                            if (nuevo.getId() == actual.getId()) {
                                boolean haCambiado = false;

                                if (actual.getEstado() != nuevo.getEstado()) {
                                    actual.setEstado(nuevo.getEstado());
                                    haCambiado = true;
                                }

                                if (!igual(actual.getNotaAdministrador(), nuevo.getNotaAdministrador())) {
                                    actual.setNotaAdministrador(nuevo.getNotaAdministrador());
                                    haCambiado = true;
                                }

                                if (nuevo.getContadorMultimedia() != actual.getContadorMultimedia()) {
                                    actual.setContadorMultimedia(nuevo.getContadorMultimedia());
                                    haCambiado = true;
                                }

                                if (haCambiado && adapter != null) {
                                    adapter.notifyItemChanged(j);
                                }

                                break;
                            }
                        }
                    }
                } else {
                    Log.e("Retrofit", "Fallo en la solicitud: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<DetailsModel>> call, Throwable t) {
                Log.d("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
        });
    }

    private boolean igual(String a, String b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }


    private void empezarTrabajo(){
        String token= "Bearer " + clientManager.getClientToken();
        Call<Void> call = apiService.empezarTrabajo(token,idCotizacion);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    adapter.setPuedeInteractuar(true);
                    btnEmpezarTrabajo.setVisibility(View.GONE);
                }else{
                    btnEmpezarTrabajo.setClickable(true);
                    Log.e("Retrofit", "Fallo en la solicitud: " + response.message());
                    Toast.makeText(getApplicationContext(), "Error al iniciar trabajo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                btnEmpezarTrabajo.setClickable(true);
                Log.d("Retrofit", "Fallo en la solicitud: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Error al iniciar trabajo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void marcarTrabajo(int idTrabajo){
        String token= "Bearer " + clientManager.getClientToken();
        Call<Void> call = apiService.marcarTrabajo(token,idTrabajo);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    private void subirEvidencias(int idTrabajo){
        String token= "Bearer " + clientManager.getClientToken();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketManager.dejarDeEscucharEvento("Trabajos");
        SocketManager.dejarDeEscucharEvento("Trabajo" + idCotizacion);
        isListenerRegistered = false;
    }
}