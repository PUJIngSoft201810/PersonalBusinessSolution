package com.nise.jbookproject.Modulos;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nise.jbookproject.R;

import java.util.List;

public class AdapterReserva extends RecyclerView.Adapter<AdapterReserva.ReservasViewHolder>{

    List<Reserva> reservas;

    public AdapterReserva(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    @NonNull
    @Override
    public ReservasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowreserva_recycler,parent,false);
        ReservasViewHolder holder = new ReservasViewHolder(v);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReservasViewHolder holder, int position) {
        //LO QUE SE QUIERE HACER
        Reserva reserva = reservas.get(position);
        holder.textViewRecurso.setText("Recurso: "+reserva.getRecurso() + "\n   Id usuario: " + reserva.getIdUsuario()+ "\n");
        //holder.textViewRecurso.setText(reserva.getRecurso());
        //holder.textViewIdUsuario.setText(reserva.getIdUsuario()+"");
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    public static class ReservasViewHolder extends RecyclerView.ViewHolder{

        TextView textViewRecurso, textViewIdUsuario;
        public ReservasViewHolder(View itemView) {
            super(itemView);
            textViewRecurso = itemView.findViewById(R.id.textview_recurso);
            //  textViewIdUsuario = itemView.findViewById(R.id.textview_idusuario);
        }
    }
}
