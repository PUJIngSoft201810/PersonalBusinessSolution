package com.nise.jbookproject.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nise.jbookproject.Adaptadores.AdapterSala;
import com.nise.jbookproject.Modulos.RecyclerTouchListener;
import com.nise.jbookproject.Modulos.Sala;
import com.nise.jbookproject.R;

import java.io.Serializable;
import java.util.ArrayList;

public class ReservarSala extends AppCompatActivity {

    private static final String TAG = "ReservarSala";
    DatabaseReference mDatabase;

    ArrayList<Sala> salas;
    RecyclerView recyclerView;
    AdapterSala adapterSala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_sala);

        salas = new ArrayList<Sala>();


        recyclerView = (RecyclerView) findViewById(R.id.recyclerSalas);
        // Establece la lista lineal y vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapterSala = new AdapterSala(salas);

        recyclerView.setAdapter(adapterSala);

        eventoClickRow();

        configurarDB();

    }

    private void eventoClickRow() {
        //Logica de que realizar en evento onClick, NOTA: En AdapterSala se implementa interfaz onclicklistener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //int posicionView = recyclerView.getChildAdapterPosition(view);
                Sala salaActual = salas.get(position);
                Intent intent = new Intent(ReservarSala.this, ReservarSalaDia.class);
                intent.putExtra("objeto", (Serializable) salaActual);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void configurarDB() {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("proyecto").child("recursos").child("salas");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                // Nueva sala agregada
                Sala sala = dataSnapshot.getValue(Sala.class);

                salas.add(sala);
                // Actualizo el adapter con los nuevos cambios en la lista
                adapterSala.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                // Sala modificada
                Sala salaEditada =  dataSnapshot.getValue(Sala.class);

                int i = getPosicionSala(salaEditada);
                salas.set(i, salaEditada);
                // Actualizo el adapter con los nuevos cambios en la lista
                adapterSala.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                // Sala eliminada
                Sala salaEliminada =  dataSnapshot.getValue(Sala.class);
                salas.remove(salaEliminada);
                // Actualizo el adapter con los nuevos cambios en la lista
                adapterSala.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                // No aplica
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postSalas:onCancelled", databaseError.toException());
                Toast.makeText(ReservarSala.this, "Failed to load salas.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mDatabase.addChildEventListener(childEventListener);
    }

    private int getPosicionSala(Sala salaEditada) {
        int i = 0;
        int posicion = -1;
        boolean sinEncontrar = true;
        while (i < salas.size() && sinEncontrar) {
            if (salaEditada.getId().equals(salas.get(i).getId())) {
                posicion = i;
                sinEncontrar = false;
            }
            i++;
        }
        return posicion;
    }

    public void addSala(View view) {
        Sala sala1 = new Sala("1","sala a", "Tablero digital y tablero físico", false, "Piso 2", 6);
        Sala sala2 = new Sala("2","sala z", "Televisor 40 pulgadas", false, "Sótano 1", 8);
        DatabaseReference p1 = mDatabase.push();
        sala1.setId(p1.getKey().toString());
        p1.setValue(sala1);
        DatabaseReference p2 = mDatabase.push();
        sala2.setId(p2.getKey().toString());
        p2.setValue(sala2);
    }
}
