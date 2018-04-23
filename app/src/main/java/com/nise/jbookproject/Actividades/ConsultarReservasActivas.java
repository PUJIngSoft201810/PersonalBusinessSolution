package com.nise.jbookproject.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nise.jbookproject.Modulos.AdapterHistorialReservas;
import com.nise.jbookproject.Modulos.FirebaseReferences;
import com.nise.jbookproject.Modulos.RecyclerTouchListener;
import com.nise.jbookproject.Modulos.Reserva;
import com.nise.jbookproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultarReservasActivas extends AppCompatActivity {

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
        //rv.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        reservas = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapter = new AdapterHistorialReservas(reservas);

        rv.setAdapter(adapter);
        Log.i("ADAPTER", "Adapter creado");
        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        final DatabaseReference reservasRef = proyectoRef.child(FirebaseReferences.RESERVA_REFERENCE);
        final DatabaseReference recursosRef= proyectoRef.child(FirebaseReferences.RECURSOS_REFERENCE);
        final DatabaseReference computadoresRef = recursosRef.child(FirebaseReferences.COMPUTADORES_REFERENCE);

        Log.i("ADAPTER", "Parent "+ reservasRef.toString());

        rv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Reserva reserva = reservas.get(position);
                reserva.setActiva(false);

                DatabaseReference miCompu = computadoresRef.child(reserva.getIdRecurso());
                Map<String, Object> hopperUpdatesCompu = new HashMap<>();
                hopperUpdatesCompu.put("reservado", false);
                miCompu.updateChildren(hopperUpdatesCompu);

                DatabaseReference miReserva = reservasRef.child(reserva.getIdReserva());
                Map<String, Object> hopperUpdatesRes = new HashMap<>();
                hopperUpdatesRes.put("activa", false);
                miReserva.updateChildren(hopperUpdatesRes);

                Toast.makeText(getApplicationContext(), reserva.getIdReserva() + " recurso regresado", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        reservasRef.addValueEventListener(new ValueEventListener() {
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
                    if(reserva.getActiva())
                    {
                        reservas.add(reserva);
                        i++;
                    }
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
