package com.example.carwashdriver_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carwashdriver_android.Models.UsuarioModel;
import com.example.carwashdriver_android.Retrofit.ApiService;
import com.example.carwashdriver_android.Retrofit.ClientManager;
import com.example.carwashdriver_android.Retrofit.RetrofitClient;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtCorreo, txtPassword;
    TextView txtRegistro;
    ClientManager clientManager;
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.buttoningresar);

        btnLogin.setOnClickListener(v -> Login());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Login(){
        if(txtCorreo.getText().toString().trim().isEmpty() || txtPassword.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios",Toast.LENGTH_SHORT).show();

        }else{
            String correo = txtCorreo.getText().toString();
            String clave = txtPassword.getText().toString();
            HashMap<String,String> body = new HashMap<>();
            body.put("correo",correo);
            body.put("contrasena",clave);
            Call<UsuarioModel> call = apiService.login(
                    body
            );
            call.enqueue(new Callback<UsuarioModel>() {
                @Override
                public void onResponse(Call<UsuarioModel> call, Response<UsuarioModel> response) {
                    if(response.isSuccessful()){
                        UsuarioModel usuarioModel =response.body();
                        clientManager.saveClientData(
                                usuarioModel.getId(),
                                usuarioModel.getUsername(),
                                usuarioModel.getToken());
                        Log.d("Retrofit", "Inicio de secion exitoso");
                        //Intent intent =new Intent(getApplicationContext(),Registro.class);
                        //startActivity(intent);
                    }else{
                        Log.e("Retrofit", "Fallo en la solicitud: " + response.message());
                    }
                }
                @Override
                public void onFailure(Call<UsuarioModel> call, Throwable t) {
                    Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
                }
            });
        }

    }
}