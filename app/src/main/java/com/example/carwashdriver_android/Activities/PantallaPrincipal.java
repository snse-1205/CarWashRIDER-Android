package com.example.carwashdriver_android.Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.carwashdriver_android.Fragments.FragmentHistorial;
import com.example.carwashdriver_android.Fragments.FragmentSemana;
import com.example.carwashdriver_android.Fragments.FragmentToDo;
import com.example.carwashdriver_android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PantallaPrincipal extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_principal);

        // Configurar Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_todo) {
                abrirFragment(new FragmentToDo());
            } else if (item.getItemId() == R.id.nav_historial) {
                abrirFragment(new FragmentHistorial());
            } else if (item.getItemId() == R.id.nav_semana) {
                abrirFragment(new FragmentSemana());
            }
            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void abrirFragment(Fragment fragment, int action) {
        Bundle args = new Bundle();
        args.putInt("ACTION", action);
        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedorFragments, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void abrirFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedorFragments, fragment)
                .addToBackStack(null)
                .commit();
    }
}