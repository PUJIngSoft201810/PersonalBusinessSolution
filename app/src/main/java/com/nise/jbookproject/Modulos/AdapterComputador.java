package com.nise.jbookproject.Modulos;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.nise.jbookproject.R;

import java.util.List;

public class AdapterComputador extends RecyclerView.Adapter<AdapterComputador.ComputadoresViewHolder>{
    List<Computador> computadores;

    public AdapterComputador(List<Computador> computadores) {
        this.computadores = computadores;
    }

    @NonNull
    @Override
    public AdapterComputador.ComputadoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_computador,parent,false);
        AdapterComputador.ComputadoresViewHolder holder = new AdapterComputador.ComputadoresViewHolder(v);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterComputador.ComputadoresViewHolder holder, int position) {
        //LO QUE SE QUIERE HACER

        Computador computador = computadores.get(position);
        holder.textViewIdComputador.setText(("Id computador: " + computador.getId()));
        holder.textViewDescripcionComputador.setText("Descripcion: " + computador.getDescripcion());
        holder.textViewUbicacionComputador.setText("Ubicacion: " + computador.getUbicacion());
    }

    @Override
    public int getItemCount() {
        return computadores.size();
    }

    public static class ComputadoresViewHolder extends RecyclerView.ViewHolder{

        TextView textViewIdComputador, textViewDescripcionComputador, textViewUbicacionComputador;
        public ComputadoresViewHolder(View itemView) {
            super(itemView);

            textViewIdComputador = itemView.findViewById(R.id.textview_IdComputador);
            textViewDescripcionComputador = itemView.findViewById(R.id.textview_DescripcionComputador);
            textViewUbicacionComputador= itemView.findViewById(R.id.textview_UbicacionComputador);
        }
    }
}

