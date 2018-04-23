package com.nise.jbookproject.Actividades;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nise.jbookproject.Modulos.AdapterComputador;
import com.nise.jbookproject.Modulos.AdapterLibro;
import com.nise.jbookproject.Modulos.Computador;
import com.nise.jbookproject.Modulos.FirebaseReferences;
import com.nise.jbookproject.Modulos.Libro;
import com.nise.jbookproject.Modulos.RecyclerTouchListener;
import com.nise.jbookproject.Modulos.Reserva;
import com.nise.jbookproject.Modulos.TipoComputador;
import com.nise.jbookproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservarLibro extends AppCompatActivity {
    //Button buttonReserva;
    Button buttonCrear;
    //EditText textId;
    RecyclerView rv;
    List<Libro> libros;
    AdapterLibro adapterLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_libro);
        //buttonReserva = (Button) findViewById(R.id.reservarButton);
        buttonCrear = (Button) findViewById(R.id.crearLib);
        rv = (RecyclerView) findViewById(R.id.recyclerLibro);
        rv.setLayoutManager(new LinearLayoutManager(this));
        libros = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        adapterLibro = new AdapterLibro(libros);
        Log.i("ADAPTER", "Adapter creado");

        rv.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapterLibro);


        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        final DatabaseReference reservaRef = proyectoRef.child(FirebaseReferences.RESERVA_REFERENCE);
        final DatabaseReference recursosRef= proyectoRef.child(FirebaseReferences.RECURSOS_REFERENCE);
        final DatabaseReference librosRef = recursosRef.child(FirebaseReferences.LIBROS_REFERENCE);

        rv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Libro libro = libros.get(position);
                Toast.makeText(getApplicationContext(), libro.getId() + " reservado", Toast.LENGTH_SHORT).show();
                libro.setReservado(true);
                librosRef.child(libro.getId()).setValue(libro);

                Date fecha_inicio, fecha_fin;
                fecha_inicio = Calendar.getInstance().getTime();
                Calendar cal = Calendar.getInstance();
                cal.setTime(fecha_inicio);
                cal.add(Calendar.DAY_OF_YEAR, 15);
                fecha_fin = cal.getTime();

                Reserva reserva = new Reserva("Prueba","1032428174",libros.get(position).getId(),libros.get(position).getDescripcion(),true,fecha_inicio,fecha_fin);
                DatabaseReference miReserva = reservaRef.push();
                reserva.setIdReserva(miReserva.getKey().toString());
                miReserva.setValue(reserva);

                adapterLibro.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        librosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                libros.removeAll(libros);
                Log.i("ADAPTER", "DataSnapshot"+dataSnapshot.toString());
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()
                        ) {
                    Libro libro = snapshot.getValue(Libro.class);
                    Log.i("ADAPTER",libro.toString());
                    if(!libro.isReservado())
                    {
                        libros.add(libro);
                    }
                }
                adapterLibro.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Libro libro = new Libro("Prueba","libro",false,"sotano 1", " Gabriel Garcia Marquez", "100 Años de soledad","Lib101");
                DatabaseReference miLibro = librosRef.push();
                libro.setId(miLibro.getKey().toString());
                miLibro.setValue(libro);
            }
        });
    }
}
