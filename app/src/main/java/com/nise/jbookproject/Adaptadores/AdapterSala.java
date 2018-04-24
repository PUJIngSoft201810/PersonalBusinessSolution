package com.nise.jbookproject.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nise.jbookproject.Modulos.Sala;
import com.nise.jbookproject.R;

import java.util.ArrayList;

/**
 * Created by andres on 23/04/18.
 */

public class AdapterSala
        extends RecyclerView.Adapter<AdapterSala.SalasViewHolder>
        implements View.OnClickListener {

    private ArrayList<Sala> listaSalas;
    private View.OnClickListener listener;

    public AdapterSala(ArrayList<Sala> listaSalas) {
        this.listaSalas = listaSalas;
    }

    @NonNull
    @Override
    public SalasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla la vista con el layout para cada row de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_sala,null,false);
        // Hace que view pueda escuchar el evento de seleccion
        view.setOnClickListener(this);
        return new SalasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalasViewHolder holder, int position) {
        // Enlace entre la accion que se va a realizar por cada item de la lista
        holder.asignarDatos(listaSalas.get(position));
    }

    @Override
    public int getItemCount() {
        // Tamaño de la lista
        return listaSalas.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null) {
            listener.onClick(v);
        }
    }

    public class SalasViewHolder extends RecyclerView.ViewHolder {

        TextView nombreSala;
        TextView ubicacionSala;
        TextView descripcionSala;
        TextView capacidadSala;

        public SalasViewHolder(View itemView) {
            super(itemView);
            nombreSala = (TextView) itemView.findViewById(R.id.rowSalaNombre);
            ubicacionSala = (TextView) itemView.findViewById(R.id.rowSalaUbicacion);
            descripcionSala = (TextView) itemView.findViewById(R.id.rowSalaDescripcion);
            capacidadSala = (TextView) itemView.findViewById(R.id.rowSalaCapacidad);
        }

        public void asignarDatos(Sala sala) {
            //Asigna los datoa a los campos de la vista
            nombreSala.setText(sala.getNombre());
            ubicacionSala.setText(sala.getUbicacion());
            descripcionSala.setText(sala.getDescripcion());
            capacidadSala.setText(sala.getCapacidad()+" Personas");
        }
    }
}
