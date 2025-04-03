package com.example.carwashdriver_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class SeleccionLugarFragment extends Fragment {
    public SeleccionLugarFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seleccion_lugar, container, false);

        Button btnLocal = view.findViewById(R.id.btnLocal);
        Button btnDomicilio = view.findViewById(R.id.btnDomicilio);

        btnLocal.setOnClickListener(v -> {

            Fragment nextFragment = new SeleccionVehiculoFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, nextFragment)
                    .addToBackStack(null)
                    .commit();
        });

        btnDomicilio.setOnClickListener(v -> {
            // ✅ Ir al fragmento de selección de servicios (o domicilio)
            Fragment nextFragment = new SeleccionServiciosFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, nextFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}

