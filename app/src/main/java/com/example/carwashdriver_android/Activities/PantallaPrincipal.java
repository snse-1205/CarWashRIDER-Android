package com.example.carwashdriver_android.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.carwashdriver_android.Config.SocketManager;
import com.example.carwashdriver_android.Fragments.FragmentHistorial;
import com.example.carwashdriver_android.Fragments.FragmentSemana;
import com.example.carwashdriver_android.Fragments.FragmentToDo;
import com.example.carwashdriver_android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PantallaPrincipal extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private int currentSelectedItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_principal);

        // Configurar Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == currentSelectedItemId) {
                Log.d("SOCKET", "Se ignoró el clic porque ya está en ese fragmento");
                return false;
            }

            currentSelectedItemId = itemId;

            // Llamamos al fragmento correspondiente de acuerdo con el itemId
            if (itemId == R.id.nav_todo) {
                Log.d("SOCKET", "Se preciono el boton del navbar");
                abrirFragment(new FragmentToDo());
            } else if (itemId == R.id.nav_historial) {
                abrirFragment(new FragmentHistorial());
            } else if (itemId == R.id.nav_semana) {
                abrirFragment(new FragmentSemana());
            }

            return true;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
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

    @Override
    protected void onResume() {
        super.onResume();

        // Verificar si el socket está conectado, si no, reconectar
        if (SocketManager.getSocket() == null || !SocketManager.getSocket().connected()) {
            SocketManager.initSocket();
            SocketManager.connect();
        }
    }
}