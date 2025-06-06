package com.example.carwashdriver_android.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashdriver_android.Activities.DetallesTrabajo;
import com.example.carwashdriver_android.Models.ToDoModel;
import com.example.carwashdriver_android.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class AdapterToDo extends RecyclerView.Adapter<AdapterToDo.ViewHolder> {
    Context context;
    List<ToDoModel> toDoModel;

    public AdapterToDo(Context context, List<ToDoModel> arrayList){
        this.context = context;
        this.toDoModel = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_to_do, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoModel toDo = toDoModel.get(position);
        holder.titulo.setText("Trabajo codigo: "+toDo.getId());
        holder.hora.setText(toDo.getHoraTrabajo());

        switch (toDo.getModalidad()){
            case 1:
                holder.icono.setImageResource(R.drawable.icon_arrow_delivery);
                break;
            case 2:
                holder.icono.setImageResource(R.drawable.icon_store);
                break;
        }
        switch (toDo.getEstado()) {
            case 3:
                holder.estado.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.amarillo_manteca));
                break;
            case 4:
                holder.estado.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.menta_pastel));
                break;
            default:
                holder.estado.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorErrorLight));
                break;
        }

        holder.cardItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetallesTrabajo.class);
            intent.putExtra("idCotiazcion",toDo.getId());
            intent.putExtra("estadoCotizacion",toDo.getEstado());
            context.startActivity(intent);
            //((Activity) context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return toDoModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icono, estado;
        TextView hora, titulo;
        MaterialCardView cardItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.itemListTitulo);
            hora = itemView.findViewById(R.id.itemListHora);
            estado = itemView.findViewById(R.id.itemListEstado);
            icono = itemView.findViewById(R.id.itemListIcono);
            cardItem = itemView.findViewById(R.id.itemListCardItem);
        }
    }
}
