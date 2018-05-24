package com.nise.jbookproject.Modulos;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nise.jbookproject.R;

import java.util.List;

public class AdapterLibro extends RecyclerView.Adapter<AdapterLibro.LibrosViewHolder>{
    List<Libro> libros;

    public AdapterLibro(List<Libro> libros) {
        this.libros = libros;
    }

    @NonNull
    @Override
    public AdapterLibro.LibrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_libro,parent,false);
        AdapterLibro.LibrosViewHolder holder = new AdapterLibro.LibrosViewHolder(v);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLibro.LibrosViewHolder holder, int position) {
        //LO QUE SE QUIERE HACER

        Libro libro= libros.get(position);
        holder.textViewIdLibro.setText("Id: " + libro.getId());
        holder.textViewDescripcionLibro.setText("Descripcion: " + libro.getDescripcion());
        holder.textViewTituloLibro.setText("Titulo: " + libro.getTitulo());
        holder.textViewAutorLibro.setText("Autor: " + libro.getAutor());
        holder.textViewIsbnLibro.setText("ISBN: " + libro.getIsbn());
        holder.textViewUbicacionLibro.setText("Ubicacion: " + libro.getUbicacion());
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    public static class LibrosViewHolder extends RecyclerView.ViewHolder{

        TextView textViewIdLibro, textViewTituloLibro, textViewDescripcionLibro, textViewAutorLibro, textViewIsbnLibro, textViewUbicacionLibro;
        public LibrosViewHolder(View itemView) {
            super(itemView);

            textViewIdLibro = itemView.findViewById(R.id.textview_IdLibro);
            textViewDescripcionLibro = itemView.findViewById(R.id.textview_DescripcionLibro);
            textViewTituloLibro= itemView.findViewById(R.id.textview_TituloLibro);
            textViewAutorLibro = itemView.findViewById(R.id.textview_AutorLibro);
            textViewIsbnLibro = itemView.findViewById(R.id.textview_IsbnLibro);
            textViewUbicacionLibro = itemView.findViewById(R.id.textview_UbicacionLibro);
        }
    }
}


