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

public class AdapterHistorial extends RecyclerView.Adapter<AdapterHistorial.ViewHolder> {
    private ArrayList<DetailsModel> details;
    private Context context;


    public AdapterHistorial(Context context, ArrayList<DetailsModel> details) {
        this.details = details;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterHistorial.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_historial, parent, false);
        return new AdapterHistorial.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistorial.ViewHolder holder, int position) {
        DetailsModel detailsModel = details.get(position);
        holder.codigoDetalle.setText("Servicio #"+detailsModel.getId());
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

    }

    @Override
    public int getItemCount() {
        return details.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView codigoDetalle,servicio;
        ImageView estado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codigoDetalle = itemView.findViewById(R.id.textTituloHistorial);
            servicio = itemView.findViewById(R.id.textServicioHistorial);
            estado = itemView.findViewById(R.id.itemListEstadoHistorial);
        }
    }
}
