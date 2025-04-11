package com.example.carwashdriver_android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carwashdriver_android.Adapters.AdapterSemana;
import com.example.carwashdriver_android.Adapters.AdapterToDo;
import com.example.carwashdriver_android.Models.ToDoModel;
import com.example.carwashdriver_android.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSemana#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSemana extends Fragment {

    RecyclerView recyclerView;
    AdapterSemana adapter;
    ArrayList<ToDoModel> listaTrabajos;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public FragmentSemana() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSemana.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSemana newInstance(String param1, String param2) {
        FragmentSemana fragment = new FragmentSemana();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_semana, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleViewListSemanal);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaTrabajos = obtenerTrabajosDePrueba();

        Collections.sort(listaTrabajos, Comparator.comparing(ToDoModel::getFechaCita));

        adapter = new AdapterSemana(getContext(), listaTrabajos);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<ToDoModel> obtenerTrabajosDePrueba() {
        ArrayList<ToDoModel> lista = new ArrayList<>();

        lista.add(new ToDoModel(101, 2, 1, "Lunes 8 de Abril","08:00" ));
        lista.add(new ToDoModel(102, 1, 2,"Lunes 8 de Abril" , "10:30"));
        lista.add(new ToDoModel(103, 1, 1, "Martes 9 de Abril", "09:00"));
        lista.add(new ToDoModel(104, 1, 2, "Miércoles 10 de Abril", "11:00"));
        lista.add(new ToDoModel(105, 1, 1, "Jueves 11 de Abril", "15:00"));

        return lista;
    }
}