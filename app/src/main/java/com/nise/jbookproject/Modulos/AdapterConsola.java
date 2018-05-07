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

public class AdapterConsola extends RecyclerView.Adapter<AdapterConsola.ConsolasViewHolder>{
    List<Consola> consolas;

    public AdapterConsola(List<Consola> consolas) {
        this.consolas = consolas;
    }

    @NonNull
    @Override
    public AdapterConsola.ConsolasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_consola,parent,false);
        AdapterConsola.ConsolasViewHolder holder = new AdapterConsola.ConsolasViewHolder(v);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterConsola.ConsolasViewHolder holder, int position) {
        //LO QUE SE QUIERE HACER

        Consola consola = consolas.get(position);
        holder.textViewIdConsola.setText(("Id: " + consola.getId()));
        holder.textViewDescripcionConsola.setText("Descripcion: " + consola.getDescripcion());
        holder.textViewUbicacionConsola.setText("Ubicacion: " + consola.getUbicacion());
        holder.textViewControlesConsola.setText("No. Controles: " + consola.getControles());
    }

    @Override
    public int getItemCount() {
        return consolas.size();
    }

    public static class ConsolasViewHolder extends RecyclerView.ViewHolder{

        TextView textViewIdConsola, textViewDescripcionConsola, textViewUbicacionConsola, textViewControlesConsola;
        public ConsolasViewHolder(View itemView) {
            super(itemView);

            textViewIdConsola = itemView.findViewById(R.id.textview_IdConsola);
            textViewDescripcionConsola = itemView.findViewById(R.id.textview_DescripcionConsola);
            textViewUbicacionConsola = itemView.findViewById(R.id.textview_UbicacionConsola);
            textViewControlesConsola = itemView.findViewById(R.id.textview_ControlesConsola);
        }
    }
}