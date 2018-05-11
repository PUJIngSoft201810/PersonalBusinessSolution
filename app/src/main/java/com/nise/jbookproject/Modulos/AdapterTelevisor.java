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

public class AdapterTelevisor extends RecyclerView.Adapter<AdapterTelevisor.TelevisoresViewHolder>{
    List<Televisor> televisores;

    public AdapterTelevisor(List<Televisor> televisores) {
        this.televisores = televisores;
    }

    @NonNull
    @Override
    public AdapterTelevisor.TelevisoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_televisor,parent,false);
        AdapterTelevisor.TelevisoresViewHolder holder = new AdapterTelevisor.TelevisoresViewHolder(v);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTelevisor.TelevisoresViewHolder holder, int position) {
        //LO QUE SE QUIERE HACER

        Televisor televisor = televisores.get(position);
        holder.textViewIdTelevisor.setText(("Id: " + televisor.getId()));
        holder.textViewDescripcionTelevisor.setText("Descripcion: " + televisor.getDescripcion());
        holder.textViewUbicacionTelevisor.setText("Ubicacion: " + televisor.getUbicacion());
    }

    @Override
    public int getItemCount() {
        return televisores.size();
    }

    public static class TelevisoresViewHolder extends RecyclerView.ViewHolder{

        TextView textViewIdTelevisor, textViewDescripcionTelevisor, textViewUbicacionTelevisor;
        public TelevisoresViewHolder(View itemView) {
            super(itemView);

            textViewIdTelevisor = itemView.findViewById(R.id.textview_IdTelevisor);
            textViewDescripcionTelevisor = itemView.findViewById(R.id.textview_DescripcionTelevisor);
            textViewUbicacionTelevisor = itemView.findViewById(R.id.textview_UbicacionTelevisor);
        }
    }
}