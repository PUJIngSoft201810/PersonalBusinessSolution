package com.nise.jbookproject.Actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nise.jbookproject.Modulos.AdapterHistorialReservas;
import com.nise.jbookproject.Modulos.FirebaseReferences;
import com.nise.jbookproject.Modulos.Reserva;
import com.nise.jbookproject.R;


import java.util.ArrayList;
import java.util.List;

public class HistorialReservas extends AppCompatActivity {

    RecyclerView rv;
    List<Reserva> reservas;

    AdapterHistorialReservas adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_reservas);

        rv = (RecyclerView) findViewById(R.id.recycler);
        //rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        reservas = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapter = new AdapterHistorialReservas(reservas);

        rv.setAdapter(adapter);
        Log.i("ADAPTER", "Adapter creado");
        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        final DatabaseReference reservaRef = proyectoRef.child(FirebaseReferences.RESERVA_REFERENCE);
        final DatabaseReference recursosRef= proyectoRef.child(FirebaseReferences.RECURSOS_REFERENCE);
        final DatabaseReference computadoresResRef = reservaRef.child(FirebaseReferences.COMPUTADORES_REFERENCE);
        final DatabaseReference librosResRef = reservaRef.child(FirebaseReferences.LIBROS_REFERENCE);
        final DatabaseReference consolasResRef = reservaRef.child(FirebaseReferences.CONSOLAS_REFERENCE);
        final DatabaseReference televisoresResRef = reservaRef.child(FirebaseReferences.TELEVISORES_REFERENCE);
        Log.i("ADAPTER", "Parent "+ reservaRef.toString());

        computadoresResRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("ADAPTER", "DataSnapshot"+dataSnapshot.toString());
                reservas.removeAll(reservas);
                Log.i("ADAPTER", "Se removieron las reservas");
                int i = 0;
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()
                        ) {
                    Reserva reserva = snapshot.getValue(Reserva.class);
                    Log.i("ADAPTER","Reserva"+ reserva.getRecurso());
                    reservas.add(reserva);
                    i++;
                }
                adapter.notifyDataSetChanged();
                Log.i("ADAPTER", "Se agregaron las reservas y se notifico" + i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        librosResRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("ADAPTER", "DataSnapshot"+dataSnapshot.toString());
                //reservas.removeAll(reservas);
                Log.i("ADAPTER", "Se removieron las reservas");
                int i = 0;
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()
                        ) {
                    Reserva reserva = snapshot.getValue(Reserva.class);
                    Log.i("ADAPTER","Reserva"+ reserva.getRecurso());
                    reservas.add(reserva);
                    i++;
                }
                adapter.notifyDataSetChanged();
                Log.i("ADAPTER", "Se agregaron las reservas y se notifico" + i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        consolasResRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("ADAPTER", "DataSnapshot"+dataSnapshot.toString());
                //reservas.removeAll(reservas);
                Log.i("ADAPTER", "Se removieron las reservas");
                int i = 0;
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()
                        ) {
                    Reserva reserva = snapshot.getValue(Reserva.class);
                    Log.i("ADAPTER","Reserva"+ reserva.getRecurso());
                    reservas.add(reserva);
                    i++;
                }
                adapter.notifyDataSetChanged();
                Log.i("ADAPTER", "Se agregaron las reservas y se notifico" + i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        televisoresResRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("ADAPTER", "DataSnapshot"+dataSnapshot.toString());
                Log.i("ADAPTER", "Se removieron las reservas");
                int i = 0;
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()
                        ) {
                    Reserva reserva = snapshot.getValue(Reserva.class);
                    Log.i("ADAPTER","Reserva"+ reserva.getRecurso());
                    reservas.add(reserva);
                    i++;
                }
                adapter.notifyDataSetChanged();
                Log.i("ADAPTER", "Se agregaron las reservas y se notifico" + i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
