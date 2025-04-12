package com.example.carwashdriver_android.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashdriver_android.Adapters.AdapterDetails;
import com.example.carwashdriver_android.Models.DetailsModel;
import com.example.carwashdriver_android.R;
import com.example.carwashdriver_android.Utils.PermisosUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class DetallesTrabajo extends AppCompatActivity {


    private MapView mapView;
    private GoogleMap googleMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private double latitude;
    private double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalles_trabajo);


        AdapterDetails adapter;
        RecyclerView recycleViewLista;

        ArrayList<DetailsModel> listaEjemplo = new ArrayList<>();

        listaEjemplo.add(new DetailsModel(101, "Lavado completo", "Dejar bien seco el interior", 1));
        listaEjemplo.add(new DetailsModel(102, "Encerado", "Cuidado con los rayones", 6));
        listaEjemplo.add(new DetailsModel(103, "Lavado de motor", "Evitar mojar componentes eléctricos", 3));
        listaEjemplo.add(new DetailsModel(104, "Lavado básico", "Revisar alfombras traseras", 1));
        listaEjemplo.add(new DetailsModel(105, "Desinfección interior", "Usar producto hipoalergénico", 6));

        adapter = new AdapterDetails(listaEjemplo, this);
        recycleViewLista = findViewById(R.id.recycleViewListDetails);
        recycleViewLista.setLayoutManager((new LinearLayoutManager(this)));
        recycleViewLista.setAdapter(adapter);

        MaterialCardView btnEmpezarTrabajo = findViewById(R.id.itemListCardItemEmpezarTrabajo);
        btnEmpezarTrabajo.setOnClickListener(v -> {
            adapter.setPuedeInteractuar(true);
            btnEmpezarTrabajo.setVisibility(View.GONE);
        });

        MaterialCardView btnTrazarRuta = findViewById(R.id.itemListCardItemTrazarRuta);
        btnTrazarRuta.setOnClickListener(v -> abrirGoogleMapsParaRuta());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void abrirGoogleMapsParaRuta() {
        if (latitude != 0.0 && longitude != 0.0) {
            Uri uri = Uri.parse("google.navigation:q=" + latitude + "," + longitude + "&mode=d");
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

}