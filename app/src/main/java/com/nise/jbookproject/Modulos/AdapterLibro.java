package com.nise.jbookproject.Modulos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nise.jbookproject.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterLibro extends RecyclerView.Adapter<AdapterLibro.MyViewHolder> implements Filterable {
    private Context context;
    List<Libro> libros;
    List<Libro> librosFiltered;
    private LibrosAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewIdLibro, textViewTituloLibro, textViewDescripcionLibro, textViewAutorLibro, textViewIsbnLibro, textViewUbicacionLibro;

        public MyViewHolder(View view) {
            super(view);
            textViewIdLibro = itemView.findViewById(R.id.textview_IdLibro);
            textViewDescripcionLibro = itemView.findViewById(R.id.textview_DescripcionLibro);
            textViewTituloLibro = itemView.findViewById(R.id.textview_TituloLibro);
            textViewAutorLibro = itemView.findViewById(R.id.textview_AutorLibro);
            textViewIsbnLibro = itemView.findViewById(R.id.textview_IsbnLibro);
            textViewUbicacionLibro = itemView.findViewById(R.id.textview_UbicacionLibro);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onLibroSelected(librosFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public AdapterLibro(Context context, List<Libro> libros, LibrosAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.libros = libros;
        this.librosFiltered = libros;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_recycler_libro, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Libro libro = librosFiltered.get(position);
        holder.textViewIdLibro.setText(libro.getId());
        holder.textViewDescripcionLibro.setText(libro.getDescripcion());
        holder.textViewTituloLibro.setText(libro.getTitulo());
        holder.textViewAutorLibro.setText(libro.getAutor());
        holder.textViewIsbnLibro.setText(libro.getIsbn());
        holder.textViewUbicacionLibro.setText(libro.getUbicacion());
    }

    @Override
    public int getItemCount() {
        return librosFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    librosFiltered = libros;
                } else {
                    List<Libro> filteredList = new ArrayList<>();
                    for (Libro row : libros) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitulo().toLowerCase().contains(charString.toLowerCase()) || row.getAutor().toLowerCase().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    librosFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = librosFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                librosFiltered = (ArrayList<Libro>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface LibrosAdapterListener {
        void onLibroSelected(Libro libro);
    }
}
