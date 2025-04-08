package com.example.carwashdriver_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashdriver_android.Models.DetailsModel;
import com.example.carwashdriver_android.R;

import java.util.ArrayList;

public class AdapterDetails extends RecyclerView.Adapter<AdapterDetails.ViewHolder> {

    private ArrayList<DetailsModel> details;
    private Context context;

    public AdapterDetails(ArrayList<DetailsModel> details, Context context) {
        this.details = details;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterDetails.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDetails.ViewHolder holder, int position) {
        DetailsModel detailsModel = details.get(position);
        holder.codigoDetalle.setText("Servicio #"+detailsModel.getId());
        holder.notaAdmin.setText(detailsModel.getNotaAdministrador());
        holder.servicio.setText(detailsModel.getServicio());

        switch (detailsModel.getEstado()){
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

        holder.main.setOnClickListener(v -> {

            if(holder.visibilidad){
                holder.visibilidad=false;
                holder.secondary.setVisibility(View.VISIBLE);
            }else{
                holder.visibilidad=true;
                holder.secondary.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return details.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView codigoDetalle,servicio,notaAdmin,contadorMultimedia;
        ImageView estado;
        LinearLayout secondary;
        CardView main;
        boolean visibilidad=true;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codigoDetalle = itemView.findViewById(R.id.textTitulo);
            servicio = itemView.findViewById(R.id.textServicio);
            notaAdmin = itemView.findViewById(R.id.textNotaAdmin);
            contadorMultimedia = itemView.findViewById(R.id.textContador);
            estado = itemView.findViewById(R.id.imageEstado);
            main = itemView.findViewById(R.id.itemListCardItemButtons);
            secondary = itemView.findViewById(R.id.groupButtons);
        }
    }
}

