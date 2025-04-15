package com.example.carwashdriver_android.Adapters;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashdriver_android.Models.DetailsModel;
import com.example.carwashdriver_android.R;

import java.util.List;

public class AdapterDetails extends RecyclerView.Adapter<AdapterDetails.ViewHolder> {

    private List<DetailsModel> details;
    private Context context;
    private boolean puedeInteractuar = false;

    public void setPuedeInteractuar(boolean puedeInteractuar) {
        this.puedeInteractuar = puedeInteractuar;
    }

    private OnDetalleActionListener listener;

    public AdapterDetails(List<DetailsModel> details, Context context, OnDetalleActionListener listener) {
        this.details = details;
        this.context = context;
        this.listener = listener;
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
        holder.codigoDetalle.setText("Servicio #" + detailsModel.getId());
        holder.notaAdmin.setText(detailsModel.getNotaAdministrador());
        holder.servicio.setText(detailsModel.getServicio());

        switch (detailsModel.getEstado()) {
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

        holder.opciones.setOnClickListener(v -> {
            if (!puedeInteractuar) {
                Toast.makeText(v.getContext(), "No se ha empezado el trabajo", LENGTH_SHORT).show();
                return;
            }
            holder.visibilidad = !holder.visibilidad;
            holder.secondary.setVisibility(holder.visibilidad ? View.GONE : View.VISIBLE);
        });

        holder.tomarNota.setOnClickListener(v -> abrirDialogoNota(detailsModel));

        holder.finalizar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMarcarTrabajo(detailsModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView codigoDetalle, servicio, notaAdmin, contadorMultimedia;
        ImageView estado;
        LinearLayout secondary;
        CardView opciones, tomarNota,finalizar;
        boolean visibilidad = true;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codigoDetalle = itemView.findViewById(R.id.textTitulo);
            servicio = itemView.findViewById(R.id.textServicio);
            notaAdmin = itemView.findViewById(R.id.textNotaAdmin);
            contadorMultimedia = itemView.findViewById(R.id.textContador);
            estado = itemView.findViewById(R.id.imageEstado);
            opciones = itemView.findViewById(R.id.buttonOpciones);
            secondary = itemView.findViewById(R.id.groupButtons);
            tomarNota = itemView.findViewById(R.id.buttonNota);
            finalizar = itemView.findViewById(R.id.buttonFinalizar);
        }
    }

    private void abrirDialogoNota(DetailsModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ingrese su nota");

        final EditText input = new EditText(context);
        input.setHint("Escriba su nota");
        if (model.getNota() != null && !model.getNota().isBlank()) {
            input.setText(model.getNota());
        }

        builder.setView(input);
        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            model.setNota(input.getText().toString());
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public interface OnDetalleActionListener {
        void onMarcarTrabajo(DetailsModel model);
    }
}
