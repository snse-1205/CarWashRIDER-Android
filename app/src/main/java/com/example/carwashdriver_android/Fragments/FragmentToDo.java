package com.example.carwashdriver_android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carwashdriver_android.Adapters.AdapterToDo;
import com.example.carwashdriver_android.Models.ToDoModel;
import com.example.carwashdriver_android.R;

import java.util.ArrayList;

public class FragmentToDo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentToDo() {
        // Required empty public constructor
    }

    public static FragmentToDo newInstance(String param1, String param2) {
        FragmentToDo fragment = new FragmentToDo();
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
        return inflater.inflate(R.layout.fragment_to_do, container, false);
    }

    RecyclerView recycleViewLista;
    AdapterToDo adapter;

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<ToDoModel> listaToDos = new ArrayList<>();

        listaToDos.add(new ToDoModel(1, 1, 1,  "2025-04-10", "09:00"));
        listaToDos.add(new ToDoModel(2, 2, 6,  "2025-04-11", "10:30"));
        listaToDos.add(new ToDoModel(3, 1, 3,  "2025-04-12", "11:15"));
        listaToDos.add(new ToDoModel(4, 2, 1,  "2025-04-13", "14:00"));
        listaToDos.add(new ToDoModel(5, 1, 6, "2025-04-14", "08:45"));
        listaToDos.add(new ToDoModel(6, 2, 2, "2025-04-15", "13:20"));
        listaToDos.add(new ToDoModel(7, 1, 1,  "2025-04-16", "15:10"));
        listaToDos.add(new ToDoModel(8, 2, 6,  "2025-04-17", "16:30"));
        listaToDos.add(new ToDoModel(9, 1, 4, "2025-04-18", "17:00"));
        listaToDos.add(new ToDoModel(10, 2, 1,  "2025-04-19", "12:00"));

        adapter = new AdapterToDo(getContext(),listaToDos);
        recycleViewLista =view.findViewById(R.id.recycleViewListToDo);
        recycleViewLista.setLayoutManager((new LinearLayoutManager(requireContext())));
        recycleViewLista.setAdapter(adapter);
    }
}