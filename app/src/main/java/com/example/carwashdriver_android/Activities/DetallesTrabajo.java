package com.example.carwashdriver_android.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashdriver_android.Adapters.AdapterDetails;
import com.example.carwashdriver_android.Models.DetailsModel;
import com.example.carwashdriver_android.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class DetallesTrabajo extends AppCompatActivity {

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
            btnEmpezarTrabajo.setVisibility(View.GONE); // Ocultamos el botón si quieres
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}