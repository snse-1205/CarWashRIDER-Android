package com.example.carwashdriver_android.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    Uri uri;
    int tipo;

    private boolean isListenerRegistered = false;

    private static final int REQUEST_FOTO = 1001;
    private static final int REQUEST_VIDEO = 1002;
    private Uri outputUri;
    private DetailsModel modeloEnProceso;


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

        if(estado != 4) {
            SocketManager.escucharEvento("Trabajo", trabajosListener);
        }

        if (!isListenerRegistered) {
            SocketManager.escucharEvento("Trabajo-"+idCotizacion,detallesTrabajosListener);
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
                    cliente.setText(informacionGeneral.getNombreCliente());
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
        String token = "Bearer " + clientManager.getClientToken();
        Call<List<DetailsModel>> call = apiService.obtenerDetallesTrabajo(token, idCotizacion);
        call.enqueue(new Callback<List<DetailsModel>>() {
            @Override
            public void onResponse(Call<List<DetailsModel>> call, Response<List<DetailsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    informacionDetalles.clear();
                    informacionDetalles.addAll(response.body());

                    Log.d("Adapter", "Elementos cargados: " + informacionDetalles.size());
                    adapter = new AdapterDetails(informacionDetalles, context, new AdapterDetails.OnDetalleActionListener() {
                        @Override
                        public void onMarcarTrabajo(DetailsModel model) {
                            marcarTrabajo(model.getId());
                        }

                        @Override
                        public void onCapturarFoto(DetailsModel model) {
                            abrirCamara(model, "foto");
                        }

                        @Override
                        public void onCapturarVideo(DetailsModel model) {
                            abrirCamara(model, "video");
                        }

                        @Override
                        public void onCrearEvidencia(DetailsModel model) {
                            subirEvidencia(model);
                        }
                    });


                    recycleViewLista.setLayoutManager(new LinearLayoutManager(context));
                    recycleViewLista.setAdapter(adapter);

                    if (estado == 4) {
                        adapter.setPuedeInteractuar(true);
                        btnEmpezarTrabajo.setVisibility(View.GONE);
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

    private void actualizarDetalles() {
        String token = "Bearer " + clientManager.getClientToken();
        Call<List<DetailsModel>> call = apiService.obtenerDetallesTrabajo(token, idCotizacion);
        call.enqueue(new Callback<List<DetailsModel>>() {
            @Override
            public void onResponse(Call<List<DetailsModel>> call, Response<List<DetailsModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    informacionDetalles.clear();
                    informacionDetalles.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<DetailsModel>> call, Throwable t) {
                Log.d("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
        });
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
        Call<Void> call = apiService.marcarTrabajo(token,idTrabajo,idCotizacion);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    private void subirEvidencia(DetailsModel model) {
        String token= "Bearer " + clientManager.getClientToken();
        Log.d("EVIDENCIA", "ID: " + model.getId() + " - Tipo: " + " - Uri: " + uri.toString());

        RequestBody idTrabajoPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(model.getId()));
        RequestBody notaPart = RequestBody.create(MediaType.parse("text/plain"), model.getNota());
        RequestBody idCotizacion = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(this.idCotizacion));

        File file = getFileFromUri(uri);
        RequestBody filePart;
        if(this.tipo == 1){
             filePart = RequestBody.create(MediaType.parse("image/jpeg"), file); // Para foto

        }else{
             filePart = RequestBody.create(MediaType.parse("video/mp4"), file); // Para video
        }
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), filePart);

        Call<Void> call = apiService.subirEvidencia(token,fileToUpload, idTrabajoPart,idCotizacion,notaPart);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Evidencia subida con éxito", Toast.LENGTH_SHORT).show();
                    uri=null;
                } else {
                    Toast.makeText(context, "Error al subir evidencia", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Fallo en la subida", Toast.LENGTH_SHORT).show();
                Log.e("EVIDENCIA", "Error: " + t.getMessage());
            }
        });


    }


    private void abrirCamara(DetailsModel model, String tipo) {
        modeloEnProceso = model;
        Intent intent;
        try {
            if (tipo.equals("foto")) {
                this.tipo =1;
                // Crear un archivo temporal para la foto
                File file = File.createTempFile("foto_", ".jpg", getExternalFilesDir(null));
                outputUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
                startActivityForResult(intent, REQUEST_FOTO);
            } else if (tipo.equals("video")) {
                this.tipo =2;
                // Crear un archivo temporal para el video
                File file = File.createTempFile("video_", ".mp4", getExternalFilesDir(null));
                outputUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
                intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30); // Limitar a 30 segundos
                startActivityForResult(intent, REQUEST_VIDEO);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error abriendo cámara: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && outputUri != null && modeloEnProceso != null) {
            if (requestCode == REQUEST_FOTO) {
                // Foto capturada
                Toast.makeText(this, "Foto capturada", Toast.LENGTH_SHORT).show();
                uri = outputUri; // Asignamos el URI al global
            } else if (requestCode == REQUEST_VIDEO) {
                // Video capturado
                Toast.makeText(this, "Video capturado", Toast.LENGTH_SHORT).show();
                uri = outputUri; // Asignamos el URI al global
            }
        } else {
            Toast.makeText(this, "Captura cancelada", Toast.LENGTH_SHORT).show();
        }
    }


    private File getFileFromUri(Uri uri) {
        try {
            String extension = tipo == 1 ? ".jpg" : ".mp4";
            File file = new File(getCacheDir(), "temp_upload" + extension);

            InputStream inputStream = getContentResolver().openInputStream(uri);
            OutputStream outputStream = new FileOutputStream(file);

            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error leyendo archivo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketManager.dejarDeEscucharEvento("Trabajos");
        SocketManager.dejarDeEscucharEvento("Trabajo" + idCotizacion);
        isListenerRegistered = false;
    }
}