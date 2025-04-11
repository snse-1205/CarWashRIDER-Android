package com.example.carwashdriver_android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carwashdriver_android.Adapters.AdapterHistorial;
import com.example.carwashdriver_android.Adapters.AdapterSemana;
import com.example.carwashdriver_android.Models.DetailsModel;
import com.example.carwashdriver_android.Models.ToDoModel;
import com.example.carwashdriver_android.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHistorial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHistorial extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentHistorial() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHistorial.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHistorial newInstance(String param1, String param2) {
        FragmentHistorial fragment = new FragmentHistorial();
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
        return inflater.inflate(R.layout.fragment_historial, container, false);
    }

    RecyclerView recyclerView;
    AdapterHistorial adapter;
    ArrayList<DetailsModel> listaTrabajos;

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleViewListHistorial);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaTrabajos = obtenerTrabajosDePrueba();

        adapter = new AdapterHistorial(getContext(), listaTrabajos);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<DetailsModel> obtenerTrabajosDePrueba() {
        ArrayList<DetailsModel> lista = new ArrayList<>();

        lista.add(new DetailsModel(101, "Lavado completo", "Dejar bien seco el interior", 1));
        lista.add(new DetailsModel(102, "Encerado", "Cuidado con los rayones", 6));
        lista.add(new DetailsModel(103, "Lavado de motor", "Evitar mojar componentes eléctricos", 1));
        lista.add(new DetailsModel(104, "Lavado básico", "Revisar alfombras traseras", 1));
        lista.add(new DetailsModel(105, "Desinfección interior", "Usar producto hipoalergénico", 6));

        return lista;
    }

}