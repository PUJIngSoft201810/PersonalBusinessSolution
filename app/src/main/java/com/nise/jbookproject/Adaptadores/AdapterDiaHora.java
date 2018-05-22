package com.nise.jbookproject.Adaptadores;

import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nise.jbookproject.Modulos.Reserva;
import com.nise.jbookproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AdapterDiaHora extends RecyclerView.Adapter<AdapterDiaHora.DiaHorasViewHolder> {

    private ArrayList<Reserva> listaHoras;

    public AdapterDiaHora(ArrayList<Reserva> listaHoras) { this.listaHoras = listaHoras; }

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
        LinearLayout layoutHora;
        LinearLayout layoutDisponibilidad;

        public DiaHorasViewHolder(View itemView) {
            super(itemView);
            hora = itemView.findViewById(R.id.rowDiaHoraHora);
            disponibilidad = itemView.findViewById(R.id.rowDiaHoraDisponibilidad);
            layoutHora = itemView.findViewById(R.id.rowDiaHoraLyaout1);
            layoutDisponibilidad = itemView.findViewById(R.id.rowDiaHoraLayout2);
        }

        public void asignarDatos(Reserva reserva) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(reserva.getFecha_inicio());
            int horaInicio = calendar.get(Calendar.HOUR_OF_DAY);
            calendar.add(Calendar.HOUR_OF_DAY, 3);
            int horaFin = calendar.get(Calendar.HOUR_OF_DAY);
            hora.setText(horaInicio + " - " + horaFin);
            layoutHora.setBackgroundColor(Color.rgb(255,255,204));
            if(reserva.getActiva()) {
                disponibilidad.setText("No Disponible");
                //disponibilidad.setBackgroundColor(Color.rgb(255,102,102));
                layoutDisponibilidad.setBackgroundColor(Color.rgb(255,102,102));
            }else {
                disponibilidad.setText("Disponible");
                //disponibilidad.setBackgroundColor(Color.rgb(153,255,153));
                layoutDisponibilidad.setBackgroundColor(Color.rgb(153,255,153));
            }

        }
    }
}
