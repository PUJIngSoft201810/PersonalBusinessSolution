package com.nise.jbookproject.Adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nise.jbookproject.Actividades.ReservarSala;
import com.nise.jbookproject.Modulos.Sala;
import com.nise.jbookproject.R;

import java.util.ArrayList;

/**
 * Created by andres on 23/04/18.
 */

public class AdapterSala
        extends RecyclerView.Adapter<AdapterSala.SalasViewHolder> {

    private ArrayList<Sala> listaSalas;

    public AdapterSala(ArrayList<Sala> listaSalas) {
        this.listaSalas = listaSalas;
    }

    @NonNull
    @Override
    public SalasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla la vista con el layout para cada row de la lista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_sala,null,false);
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
