package com.example.carwashdriver_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Button;


import androidx.fragment.app.Fragment;


public class SeleccionVehiculoFragment extends Fragment {
    public SeleccionVehiculoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seleccion_vehiculo, container, false);

        ListView lista = view.findViewById(R.id.listaVehiculos);
        Button agregar = view.findViewById(R.id.btnAgregarVehiculo);

        agregar.setOnClickListener(v -> {
        });

        return view;
    }
}

