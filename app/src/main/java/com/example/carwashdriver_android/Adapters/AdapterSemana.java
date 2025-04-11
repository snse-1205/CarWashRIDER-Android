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

public class AdapterSemana extends RecyclerView.Adapter<AdapterSemana.ViewHolder> {

    Context context;
    ArrayList<ToDoModel> toDoModel;

    public AdapterSemana(Context context, ArrayList<ToDoModel> arrayList) {
        this.context = context;
        this.toDoModel = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_semana, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoModel toDo = toDoModel.get(position);

        holder.titulo.setText("Trabajo código: " + toDo.getId());
        holder.hora.setText(toDo.getHoraTrabajo());

        // Mostrar fecha si es la primera o cambió respecto a la anterior
        String currentDate = toDo.getFechaCita();
        String previousDate = (position > 0) ? toDoModel.get(position - 1).getFechaCita() : null;

        if (position == 0 || !currentDate.equals(previousDate)) {
            holder.fecha.setVisibility(View.VISIBLE);
            holder.fecha.setText(currentDate);
        } else {
            holder.fecha.setVisibility(View.GONE);
        }

        // Icono según modalidad
        switch (toDo.getModalidad()) {
            case 1:
                holder.icono.setImageResource(R.drawable.icon_arrow_delivery);
                break;
            case 2:
                holder.icono.setImageResource(R.drawable.icon_store);
                break;
        }

        // Color según estado
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

        // Evento al hacer click
        holder.cardItem.setOnClickListener(v -> {
            // Lógica para abrir detalles, si querés agregarlo más adelante
        });
    }

    @Override
    public int getItemCount() {
        return toDoModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icono, estado;
        TextView hora, titulo, fecha;
        MaterialCardView cardItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.itemListTitulo);
            hora = itemView.findViewById(R.id.itemListHora);
            estado = itemView.findViewById(R.id.itemListEstado);
            icono = itemView.findViewById(R.id.itemListIcono);
            fecha = itemView.findViewById(R.id.itemListFecha); // Tiene que estar en el layout
            cardItem = itemView.findViewById(R.id.itemListCardItem);
        }
    }
}
