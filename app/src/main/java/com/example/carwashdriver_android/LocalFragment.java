package com.example.carwashdriver_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class LocalFragment extends Fragment {
    public LocalFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local, container, false);

        Button btnContinuar = view.findViewById(R.id.btnContinuarLocal);
        Button btnCancelar = view.findViewById(R.id.btnCancelarLocal);

        btnContinuar.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SeleccionVehiculoFragment())
                    .addToBackStack(null)
                    .commit();
        });

        btnCancelar.setOnClickListener(v ->
                requireActivity().getOnBackPressedDispatcher().onBackPressed()
        );

        return view;
    }
}
