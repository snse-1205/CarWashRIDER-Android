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
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SeleccionVehiculoFragment())
                    .addToBackStack(null)
                    .commit();
        });

        btnDomicilio.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SeleccionVehiculoFragment())
                    .addToBackStack(null)
                    .commit();
        });


        return view;
    }
}

