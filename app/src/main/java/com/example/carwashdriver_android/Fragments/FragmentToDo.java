package com.example.carwashdriver_android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carwashdriver_android.Adapters.AdapterToDo;
import com.example.carwashdriver_android.Config.SocketManager;
import com.example.carwashdriver_android.Models.ToDoModel;
import com.example.carwashdriver_android.R;
import com.example.carwashdriver_android.Retrofit.ApiService;
import com.example.carwashdriver_android.Config.ClientManager;
import com.example.carwashdriver_android.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentToDo extends Fragment {

    public FragmentToDo() {
        // Required empty public constructor
    }

    private final Emitter.Listener trabajosListener = args -> {
        requireActivity().runOnUiThread(() -> {
            Log.d("SOCKET", "Actualización recibida, recargando trabajos...");
            llenarDatos();
        });
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do, container, false);
    }

    ClientManager clientManager;
    private final ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    List<ToDoModel> listaToDos = new ArrayList<>();
    RecyclerView recycleViewLista;
    AdapterToDo adapter;
    private boolean isListenerRegistered = false;


    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        clientManager = new ClientManager(getContext());
        recycleViewLista =view.findViewById(R.id.recycleViewListToDo);

        llenarDatos();

        if (!isListenerRegistered) {
            SocketManager.escucharEvento("Trabajos", trabajosListener);
            isListenerRegistered = true;
        }

    }

    private void llenarDatos() {
        String token= "Bearer " + clientManager.getClientToken();
        Call<List<ToDoModel>> call = apiService.obtenerTrabajosPendientes(token);
        call.enqueue(new Callback<List<ToDoModel>>() {
            @Override
            public void onResponse(Call<List<ToDoModel>> call, Response<List<ToDoModel>> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        listaToDos.clear();
                        listaToDos.addAll(response.body());
                        adapter = new AdapterToDo(getContext(),listaToDos);
                        recycleViewLista.setLayoutManager((new LinearLayoutManager(requireContext())));
                        recycleViewLista.setAdapter(adapter);
                    }
                }else{
                    Log.e("Retrofit", "Respuesta vacía del servidor");
                }
            }
            public void onFailure(Call<List<ToDoModel>> call, Throwable t) {
                Log.e("Retrofit", "Fallo en la solicitud: " + t.getMessage());
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SocketManager.dejarDeEscucharEvento("Trabajos");
        isListenerRegistered = false;  // <- esto es clave
    }

}