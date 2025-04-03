package com.example.carwashdriver_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class DetalleServicioFragment extends Fragment {

    public DetalleServicioFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_servicio, container, false);

        Button btnAnadir = view.findViewById(R.id.btnAnadirNota);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        btnAnadir.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ResumenServiciosFragment())
                    .addToBackStack(null)
                    .commit();
        });


        btnCancelar.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }
}

