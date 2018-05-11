package com.nise.jbookproject.Modulos;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.nise.jbookproject.R;

import java.util.List;

public class AdapterHistorialReservas extends RecyclerView.Adapter<AdapterHistorialReservas.ReservasViewHolder>{

    List<Reserva> reservas;

    public AdapterHistorialReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    @NonNull
    @Override
    public ReservasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_reserva,parent,false);
        ReservasViewHolder holder = new ReservasViewHolder(v);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReservasViewHolder holder, int position) {
        //LO QUE SE QUIERE HACER
        Reserva reserva = reservas.get(position);
        holder.textViewIdReserva.setText("Id Reserva: " + reserva.getIdReserva());
        holder.textViewIdRecurso.setText("Id Recurso: " + reserva.getIdRecurso());
        holder.textViewIdUsuario.setText("Id Usuario: " + reserva.getIdUsuario());
        holder.textViewFechaInicio.setText("Fecha Inicio: " + reserva.getFecha_inicio());
        holder.textViewFechaFin.setText("Fecha Fin: " + reserva.getFecha_fin());
        holder.textViewRecurso.setText("Recurso: " + reserva.getRecurso());
        holder.textViewEstado.setText("Activa: " + reserva.getActiva());
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    public static class ReservasViewHolder extends RecyclerView.ViewHolder{

        TextView textViewIdReserva, textViewIdUsuario, textViewIdRecurso, textViewRecurso, textViewFechaInicio, textViewFechaFin, textViewEstado;
        public ReservasViewHolder(View itemView) {
            super(itemView);
            textViewRecurso = itemView.findViewById(R.id.textview_Recurso);
            textViewIdReserva = itemView.findViewById(R.id.textview_IdReserva);
            textViewIdRecurso = itemView.findViewById(R.id.textview_IdRecurso);
            textViewIdUsuario = itemView.findViewById(R.id.textview_IdUsuario);
            textViewFechaInicio = itemView.findViewById(R.id.textview_FechaInicio);
            textViewFechaFin = itemView.findViewById(R.id.textview_FechaFin);
            textViewEstado = itemView.findViewById(R.id.textview_Estado);

            //  textViewIdUsuario = itemView.findViewById(R.id.textview_idusuario);
        }
    }
}
