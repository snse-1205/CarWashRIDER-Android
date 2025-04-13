package com.example.carwashdriver_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.carwashdriver_android.Activities.PantallaPrincipal;
import com.example.carwashdriver_android.Models.UsuarioModel;
import com.example.carwashdriver_android.Retrofit.ApiService;
import com.example.carwashdriver_android.Retrofit.ClientManager;
import com.example.carwashdriver_android.Retrofit.RetrofitClient;
import com.example.carwashdriver_android.Retrofit.SocketManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtCorreo, txtPassword;
    ClientManager clientManager;
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        clientManager = new ClientManager(this);

        txtCorreo = findViewById(R.id.editTextText);
        txtPassword = findViewById(R.id.editTextTextPassword);


        btnLogin = findViewById(R.id.buttoningresar);

        btnLogin.setOnClickListener(v -> Login());
        
        verificarLogin();
        obtenerToken();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void obtenerToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    System.out.println("Fetching FCM registration token failed");
                    return;                }
                // Get new FCM registration token
                String token = task.getResult();
                Log.d("TokenNoti",token);}});
    }

    private void verificarLogin() {
        String token= "Bearer " + clientManager.getClientToken();
        Call <Void> call = apiService.verifiarSesion(token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.e("Retrofit", "HOLIS :D : " + response.message());
                    if (SocketManager.getSocket() == null || !SocketManager.getSocket().connected()) {
                        SocketManager.initSocket();
                        SocketManager.connect();
                        SocketManager.setListeners();
                    }

                    Intent intent =new Intent(getApplicationContext(), PantallaPrincipal.class);
                    startActivity(intent);
                    finish();
                }else{
                    Log.e("Retrofit", "Fallo en la solicitud: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }
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
                        Log.d("UsuarioModel", usuarioModel.toString());

                        clientManager.saveClientData(
                                usuarioModel.getId(),
                                usuarioModel.getUsername(),
                                usuarioModel.getToken());

                        SocketManager.initSocket();
                        SocketManager.connect();
                        SocketManager.setListeners();
                        SocketManager.notifyUserConnected(usuarioModel.getUsername());

                        Log.d("Retrofit", "Inicio de secion exitoso");
                        Intent intent =new Intent(getApplicationContext(), PantallaPrincipal.class);
                        startActivity(intent);
                        finish();
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