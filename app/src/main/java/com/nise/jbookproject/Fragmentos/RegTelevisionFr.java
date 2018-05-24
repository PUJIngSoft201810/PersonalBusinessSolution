package com.nise.jbookproject.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class RegTelevisionFr extends Fragment {
    RecyclerView rvComp, rvLib, rvCons, rvTel, rvSal;
    List<Reserva> reservasComp, reservasLib, reservasCons, reservasTel, reservasSal;

    AdapterHistorialReservas adapterComp, adapterLib, adapterCons, adapterTel, adapterSal;

    FirebaseAuth mAuth;
    boolean esFuncionario = true;

    public RegTelevisionFr() {
        // Required empty public constructor
    }

    public static RegTelevisionFr newInstance(String param1, String param2) {
        RegTelevisionFr fragment = new RegTelevisionFr();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_reg_television, container, false);

        mAuth = FirebaseAuth.getInstance();
        //TODO COMPROBAR QUE EL USUARIO SEA FUNCIONARIO O ACADEMICO

        rvTel = (RecyclerView) v.findViewById(R.id.recyclerRegTel);
        //rv.setHasFixedSize(true);
        rvTel.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTel.setItemAnimator(new DefaultItemAnimator());
        rvTel.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        reservasTel= new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapterTel = new AdapterHistorialReservas(reservasTel);


        rvTel.setAdapter(adapterTel);

        Log.i("ADAPTER", "Adapter creadoOOOOO");
        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        final DatabaseReference reservasRef = proyectoRef.child(FirebaseReferences.RESERVA_REFERENCE);
        final DatabaseReference recursosRef= proyectoRef.child(FirebaseReferences.RECURSOS_REFERENCE);
        final DatabaseReference televisoresResRef = reservasRef.child(FirebaseReferences.TELEVISORES_REFERENCE);
        final DatabaseReference televisoresRecRef = recursosRef.child(FirebaseReferences.TELEVISORES_REFERENCE);

        rvTel.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rvTel, new RecyclerTouchListener.ClickListener() {
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

                Toast.makeText(getContext(), reserva.getIdReserva() + " recurso regresado", Toast.LENGTH_SHORT).show();
                adapterTel.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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
        return v;
    }
}
