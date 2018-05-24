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

import com.google.firebase.auth.FirebaseAuth;
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

    RecyclerView rvComp, rvLib, rvCons, rvTel, rvSal;
    List<Reserva> reservasComp, reservasLib, reservasCons, reservasTel, reservasSal;

    AdapterHistorialReservas adapterComp, adapterLib, adapterCons, adapterTel, adapterSal;

    private FirebaseAuth mAuth;
    private boolean esFuncionario = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas_activas);

        mAuth = FirebaseAuth.getInstance();
        //TODO COMPROBAR QUE EL USUARIO SEA FUNCIONARIO O ACADEMICO

        rvComp = (RecyclerView) findViewById(R.id.recyclerComp);
        rvLib = (RecyclerView) findViewById(R.id.recyclerLib);
        rvCons = (RecyclerView) findViewById(R.id.recyclerCons);
        rvTel = (RecyclerView) findViewById(R.id.recyclerTel);
        rvSal = (RecyclerView) findViewById(R.id.recyclerSal);
        //rv.setHasFixedSize(true);
        rvComp.setLayoutManager(new LinearLayoutManager(this));
        rvComp.setItemAnimator(new DefaultItemAnimator());
        rvComp.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        rvLib.setLayoutManager(new LinearLayoutManager(this));
        rvLib.setItemAnimator(new DefaultItemAnimator());
        rvLib.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        rvCons.setLayoutManager(new LinearLayoutManager(this));
        rvCons.setItemAnimator(new DefaultItemAnimator());
        rvCons.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        rvTel.setLayoutManager(new LinearLayoutManager(this));
        rvTel.setItemAnimator(new DefaultItemAnimator());
        rvTel.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        rvSal.setLayoutManager(new LinearLayoutManager(this));
        rvSal.setItemAnimator(new DefaultItemAnimator());
        rvSal.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        reservasComp = new ArrayList<>();
        reservasLib = new ArrayList<>();
        reservasCons = new ArrayList<>();
        reservasTel = new ArrayList<>();
        reservasSal = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapterComp = new AdapterHistorialReservas(reservasComp);
        adapterLib = new AdapterHistorialReservas(reservasLib);
        adapterCons = new AdapterHistorialReservas(reservasCons);
        adapterTel = new AdapterHistorialReservas(reservasTel);
        adapterSal = new AdapterHistorialReservas(reservasSal);


        rvComp.setAdapter(adapterComp);
        rvLib.setAdapter(adapterLib);
        rvCons.setAdapter(adapterCons);
        rvTel.setAdapter(adapterTel);
        rvSal.setAdapter(adapterSal);

        Log.i("ADAPTER", "Adapter creado");
        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        final DatabaseReference reservasRef = proyectoRef.child(FirebaseReferences.RESERVA_REFERENCE);
        final DatabaseReference recursosRef= proyectoRef.child(FirebaseReferences.RECURSOS_REFERENCE);
        final DatabaseReference computadoresRecRef = recursosRef.child(FirebaseReferences.COMPUTADORES_REFERENCE);
        final DatabaseReference computadoresResRef = reservasRef.child(FirebaseReferences.COMPUTADORES_REFERENCE);
        final DatabaseReference librosResRef = reservasRef.child(FirebaseReferences.LIBROS_REFERENCE);
        final DatabaseReference librosRecRef = recursosRef.child(FirebaseReferences.LIBROS_REFERENCE);
        final DatabaseReference consolasResRef = reservasRef.child(FirebaseReferences.CONSOLAS_REFERENCE);
        final DatabaseReference consolasRecRef = recursosRef.child(FirebaseReferences.CONSOLAS_REFERENCE);
        final DatabaseReference televisoresResRef = reservasRef.child(FirebaseReferences.TELEVISORES_REFERENCE);
        final DatabaseReference televisoresRecRef = recursosRef.child(FirebaseReferences.TELEVISORES_REFERENCE);
        final DatabaseReference salasResRef = reservasRef.child(FirebaseReferences.SALAS_REFERENCE);
        final DatabaseReference salasRecRef = recursosRef.child(FirebaseReferences.SALAS_REFERENCE);

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

        rvCons.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvCons, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Reserva reserva = reservasCons.get(position);
                reserva.setActiva(false);

                DatabaseReference miRecurso = consolasRecRef.child(reserva.getIdRecurso());
                Map<String, Object> hopperUpdatesRecu = new HashMap<>();
                hopperUpdatesRecu.put("reservado", false);
                miRecurso.updateChildren(hopperUpdatesRecu);

                DatabaseReference miReserva = consolasResRef.child(reserva.getIdReserva());
                Map<String, Object> hopperUpdatesRes = new HashMap<>();
                hopperUpdatesRes.put("activa", false);
                miReserva.updateChildren(hopperUpdatesRes);

                Date fecha_fin = Calendar.getInstance().getTime();
                Map<String, Object> hopperUpdatesR = new HashMap<>();
                hopperUpdatesR.put("fecha_fin", fecha_fin);
                miReserva.updateChildren(hopperUpdatesR);

                Toast.makeText(getApplicationContext(), reserva.getIdReserva() + " recurso regresado", Toast.LENGTH_SHORT).show();
                adapterCons.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        rvTel.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvTel, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Reserva reserva = reservasTel.get(position);
                reserva.setActiva(false);

                DatabaseReference miRecurso = televisoresRecRef.child(reserva.getIdRecurso());
                Map<String, Object> hopperUpdatesRecu = new HashMap<>();
                hopperUpdatesRecu.put("reservado", false);
                miRecurso.updateChildren(hopperUpdatesRecu);

                DatabaseReference miReserva = televisoresResRef.child(reserva.getIdReserva());
                Map<String, Object> hopperUpdatesRes = new HashMap<>();
                hopperUpdatesRes.put("activa", false);
                miReserva.updateChildren(hopperUpdatesRes);

                Date fecha_fin = Calendar.getInstance().getTime();
                Map<String, Object> hopperUpdatesR = new HashMap<>();
                hopperUpdatesR.put("fecha_fin", fecha_fin);
                miReserva.updateChildren(hopperUpdatesR);

                Toast.makeText(getApplicationContext(), reserva.getIdReserva() + " recurso regresado", Toast.LENGTH_SHORT).show();
                adapterTel.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        rvSal.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvSal, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Reserva reserva = reservasSal.get(position);
                reserva.setActiva(false);
                DatabaseReference miRecurso = salasRecRef.child(reserva.getIdRecurso());
                Map<String, Object> hopperUpdatesRecu = new HashMap<>();
                hopperUpdatesRecu.put("reservado", false);
                miRecurso.updateChildren(hopperUpdatesRecu);

                DatabaseReference miReserva = salasResRef.child(reserva.getIdReserva());
                Map<String, Object> hopperUpdatesRes = new HashMap<>();
                hopperUpdatesRes.put("activa", false);
                miReserva.updateChildren(hopperUpdatesRes);

                /*Date fecha_fin = Calendar.getInstance().getTime();
                Map<String, Object> hopperUpdatesR = new HashMap<>();
                hopperUpdatesR.put("fecha_fin", fecha_fin);
                miReserva.updateChildren(hopperUpdatesR);
                */

                Toast.makeText(getApplicationContext(), reserva.getIdReserva() + " recurso regresado", Toast.LENGTH_SHORT).show();
                adapterTel.notifyDataSetChanged();
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
                        if(esFuncionario)
                        {
                            Log.i("RESERVAS", "Entro " + i);
                            reservasComp.add(reserva);
                            i++;
                        }
                        else
                        {
                            if(reserva.getIdUsuario().compareTo(mAuth.getUid()) == 0)
                            {
                                Log.i("RESERVAS", "Entro " + i);
                                reservasComp.add(reserva);
                                i++;
                            }
                        }
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
                        if(esFuncionario)
                        {
                            Log.i("RESERVAS", "Entro " + i);
                            reservasLib.add(reserva);
                            i++;
                        }
                        else
                        {
                            if(reserva.getIdUsuario().compareTo(mAuth.getUid()) == 0)
                            {
                                Log.i("RESERVAS", "Entro " + i);
                                reservasLib.add(reserva);
                                i++;
                            }
                        }
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

        consolasResRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("ADAPTER", "DataSnapshot"+dataSnapshot.toString());
                reservasCons.removeAll(reservasCons);
                Log.i("ADAPTER", "Se removieron las reservas");
                int i = 0;
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()
                        ) {
                    Reserva reserva = snapshot.getValue(Reserva.class);
                    if(reserva.getActiva())
                    {
                        if(esFuncionario)
                        {
                            Log.i("RESERVAS", "Entro " + i);
                            reservasCons.add(reserva);
                            i++;
                        }
                        else
                        {
                            if(reserva.getIdUsuario().compareTo(mAuth.getUid()) == 0)
                            {
                                Log.i("RESERVAS", "Entro " + i);
                                reservasCons.add(reserva);
                                i++;
                            }
                        }
                    }
                    Log.i("ADAPTER","Reserva"+ reserva.getRecurso());
                }
                adapterCons.notifyDataSetChanged();
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
                reservasTel.removeAll(reservasTel);
                Log.i("ADAPTER", "Se removieron las reservas");
                int i = 0;
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()
                        ) {
                    Reserva reserva = snapshot.getValue(Reserva.class);
                    if(reserva.getActiva())
                    {
                        if(esFuncionario)
                        {
                            Log.i("RESERVAS", "Entro " + i);
                            reservasTel.add(reserva);
                            i++;
                        }
                        else
                        {
                            if(reserva.getIdUsuario().compareTo(mAuth.getUid()) == 0)
                            {
                                Log.i("RESERVAS", "Entro " + i);
                                reservasTel.add(reserva);
                                i++;
                            }
                        }
                    }
                    Log.i("ADAPTER","Reserva"+ reserva.getRecurso());
                }
                adapterTel.notifyDataSetChanged();
                Log.i("ADAPTER", "Se agregaron las reservas y se notifico" + i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        salasResRef.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("ADAPTER", "DataSnapshot"+dataSnapshot.toString());
                reservasSal.removeAll(reservasSal);
                Log.i("ADAPTER", "Se removieron las reservas");
                int i = 0;
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()
                        ) {
                    Reserva reserva = snapshot.getValue(Reserva.class);
                    if(reserva.getActiva())
                    {
                        if(esFuncionario)
                        {
                            Log.i("RESERVAS", "Entro " + i);
                            reservasSal.add(reserva);
                            i++;
                        }
                        else
                        {
                            if(reserva.getIdUsuario().compareTo(mAuth.getUid()) == 0)
                            {
                                Log.i("RESERVAS", "Entro " + i);
                                reservasSal.add(reserva);
                                i++;
                            }
                        }
                    }
                    Log.i("ADAPTER","Reserva"+ reserva.getRecurso());
                }
                adapterSal.notifyDataSetChanged();
                Log.i("ADAPTER", "Se agregaron las reservas y se notifico" + i);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
