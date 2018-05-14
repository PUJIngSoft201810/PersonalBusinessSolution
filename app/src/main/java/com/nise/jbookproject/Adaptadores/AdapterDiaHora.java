package com.nise.jbookproject.Adaptadores;

import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nise.jbookproject.R;

import java.util.ArrayList;
import java.util.Date;

public class AdapterDiaHora extends RecyclerView.Adapter<AdapterDiaHora.DiaHorasViewHolder> {

    private ArrayList<Date> listaHoras;

    public AdapterDiaHora(ArrayList<Date> listaHoras) { this.listaHoras = listaHoras; }

    @NonNull
    @Override
    public AdapterDiaHora.DiaHorasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_dia_hora,null,false);
        return new DiaHorasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDiaHora.DiaHorasViewHolder holder, int position) {
        holder.asignarDatos(listaHoras.get(position));
    }

    @Override
    public int getItemCount() {
        return listaHoras.size();
    }

    public class DiaHorasViewHolder extends RecyclerView.ViewHolder {

        TextView hora;
        TextView disponibilidad;

        public DiaHorasViewHolder(View itemView) {
            super(itemView);
            hora = itemView.findViewById(R.id.rowDiaHoraHora);
            disponibilidad = itemView.findViewById(R.id.rowDiaHoraDisponibilidad);
        }

        public void asignarDatos(Date fecha) {

        }
    }
}
