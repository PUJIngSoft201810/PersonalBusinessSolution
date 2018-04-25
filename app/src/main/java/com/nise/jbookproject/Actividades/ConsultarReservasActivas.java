package com.nise.jbookproject.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultarReservasActivas extends AppCompatActivity {

    RecyclerView rvComp, rvLib;
    List<Reserva> reservasComp, reservasLib;

    AdapterHistorialReservas adapterComp, adapterLib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_activas);

        rvComp = (RecyclerView) findViewById(R.id.recyclerComp);
        rvLib = (RecyclerView) findViewById(R.id.recyclerLib);
        //rv.setHasFixedSize(true);
        rvComp.setLayoutManager(new LinearLayoutManager(this));
        rvComp.setItemAnimator(new DefaultItemAnimator());
        rvComp.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        rvLib.setLayoutManager(new LinearLayoutManager(this));
        rvLib.setItemAnimator(new DefaultItemAnimator());
        rvLib.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        reservasComp = new ArrayList<>();
        reservasLib = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapterComp = new AdapterHistorialReservas(reservasComp);
        adapterLib = new AdapterHistorialReservas(reservasLib);
        rvComp.setAdapter(adapterComp);
        rvLib.setAdapter(adapterLib);

        Log.i("ADAPTER", "Adapter creado");
        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        final DatabaseReference reservasRef = proyectoRef.child(FirebaseReferences.RESERVA_REFERENCE);
        final DatabaseReference recursosRef= proyectoRef.child(FirebaseReferences.RECURSOS_REFERENCE);
        final DatabaseReference computadoresRecRef = recursosRef.child(FirebaseReferences.COMPUTADORES_REFERENCE);
        final DatabaseReference computadoresResRef = reservasRef.child(FirebaseReferences.COMPUTADORES_REFERENCE);
        final DatabaseReference librosResRef = reservasRef.child(FirebaseReferences.LIBROS_REFERENCE);
        final DatabaseReference librosRecRef = recursosRef.child(FirebaseReferences.LIBROS_REFERENCE);

        Log.i("ADAPTER", "Parent "+ reservasRef.toString());

        rvComp.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvComp, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Reserva reserva = reservasComp.get(position);
                reserva.setActiva(false);

                DatabaseReference miRecurso = computadoresRecRef.child(reserva.getIdRecurso());
                Map<String, Object> hopperUpdatesRecu = new HashMap<>();
                hopperUpdatesRecu.put("reservado", false);
                miRecurso.updateChildren(hopperUpdatesRecu);

                DatabaseReference miReserva = computadoresResRef.child(reserva.getIdReserva());
                Map<String, Object> hopperUpdatesRes = new HashMap<>();
                hopperUpdatesRes.put("activa", false);
                miReserva.updateChildren(hopperUpdatesRes);

                Date fecha_fin = Calendar.getInstance().getTime();
                Map<String, Object> hopperUpdatesR = new HashMap<>();
                hopperUpdatesR.put("fecha_fin", fecha_fin);
                miReserva.updateChildren(hopperUpdatesR);

                Toast.makeText(getApplicationContext(), reserva.getIdReserva() + " recurso regresado", Toast.LENGTH_SHORT).show();
                adapterComp.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        rvLib.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvComp, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Reserva reserva = reservasLib.get(position);
                reserva.setActiva(false);

                DatabaseReference miRecurso = librosRecRef.child(reserva.getIdRecurso());
                Map<String, Object> hopperUpdatesRecu = new HashMap<>();
                hopperUpdatesRecu.put("reservado", false);
                miRecurso.updateChildren(hopperUpdatesRecu);

                DatabaseReference miReserva = librosResRef.child(reserva.getIdReserva());
                Map<String, Object> hopperUpdatesRes = new HashMap<>();
                hopperUpdatesRes.put("activa", false);
                miReserva.updateChildren(hopperUpdatesRes);

                Date fecha_fin = Calendar.getInstance().getTime();
                Map<String, Object> hopperUpdatesR = new HashMap<>();
                hopperUpdatesR.put("fecha_fin", fecha_fin);
                miReserva.updateChildren(hopperUpdatesR);

                Toast.makeText(getApplicationContext(), reserva.getIdReserva() + " recurso regresado", Toast.LENGTH_SHORT).show();
                adapterLib.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        computadoresResRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("ADAPTER", "DataSnapshot"+dataSnapshot.toString());
                reservasComp.removeAll(reservasComp);
                Log.i("ADAPTER", "Se removieron las reservas");
                int i = 0;
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()
                        ) {
                    Reserva reserva = snapshot.getValue(Reserva.class);
                    Log.i("ADAPTER","Reserva"+ reserva.getRecurso());
                    if(reserva.getActiva())
                    {
                        reservasComp.add(reserva);
                        i++;
                    }
                }
                adapterComp.notifyDataSetChanged();
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
                reservasLib.removeAll(reservasLib);
                Log.i("ADAPTER", "Se removieron las reservas");
                int i = 0;
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()
                        ) {
                    Reserva reserva = snapshot.getValue(Reserva.class);
                    if(reserva.getActiva())
                    {
                        reservasLib.add(reserva);
                        i++;
                    }
                    Log.i("ADAPTER","Reserva"+ reserva.getRecurso());
                }
                adapterLib.notifyDataSetChanged();
                Log.i("ADAPTER", "Se agregaron las reservas y se notifico" + i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
