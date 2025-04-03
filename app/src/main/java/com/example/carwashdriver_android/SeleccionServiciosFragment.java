package com.example.carwashdriver_android; // 👈 asegúrate que este sea el correcto según tu estructura

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class SeleccionServiciosFragment extends Fragment {
    public SeleccionServiciosFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_seleccion_servicios, container, false);
    }
}