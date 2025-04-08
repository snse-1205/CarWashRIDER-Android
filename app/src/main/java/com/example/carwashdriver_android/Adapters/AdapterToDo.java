package com.example.carwashdriver_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashdriver_android.Models.ToDoModel;
import com.example.carwashdriver_android.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class AdapterToDo extends RecyclerView.Adapter<AdapterToDo.ViewHolder> {
    Context context;
    ArrayList<ToDoModel> toDoModel;

    public AdapterToDo(Context context, ArrayList<ToDoModel> arrayList){
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
        holder.color.setText(toDo.getColor());
        holder.hora.setText(toDo.getHoraTrabajo());
        holder.placa.setText(toDo.getPlaca());
        holder.marca.setText(toDo.getMarca());

        switch (toDo.getModalidad()){
            case 1:
                holder.icono.setImageResource(R.drawable.icon_arrow_delivery);
                break;
            case 2:
                holder.icono.setImageResource(R.drawable.icon_store);
                break;
        }
        switch (toDo.getEstado()) {
            case 1:
                holder.estado.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.amarillo_manteca));
                break;
            case 6:
                holder.estado.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.menta_pastel));
                break;
            default:
                holder.estado.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorErrorLight));
                break;
        }
    }

    /*
    * estados: 1:Aceptado, 2:Rechazado, 3:Pendiente, 4:Finalizado, 5:Faltante, 6:En proceso
    * */

    @Override
    public int getItemCount() {
        return toDoModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icono, estado;
        TextView hora, placa, color, marca, titulo;
        MaterialCardView cardItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.itemListTitulo);
            marca = itemView.findViewById(R.id.itemListMarca);
            color = itemView.findViewById(R.id.itemListColor);
            placa = itemView.findViewById(R.id.itemListPlaca);
            hora = itemView.findViewById(R.id.itemListHora);
            estado = itemView.findViewById(R.id.itemListEstado);
            icono = itemView.findViewById(R.id.itemListIcono);
            cardItem = itemView.findViewById(R.id.itemListCardItem);
        }
    }
}
