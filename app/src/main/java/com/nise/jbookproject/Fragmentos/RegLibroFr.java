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

public class RegLibroFr extends Fragment {
    RecyclerView rvComp, rvLib, rvCons, rvTel, rvSal;
    List<Reserva> reservasComp, reservasLib, reservasCons, reservasTel, reservasSal;

    AdapterHistorialReservas adapterComp, adapterLib, adapterCons, adapterTel, adapterSal;

    FirebaseAuth mAuth;
    boolean esFuncionario = true;
    public RegLibroFr() {
        // Required empty public constructor
    }

    public static RegLibroFr newInstance(String param1, String param2) {
        RegLibroFr fragment = new RegLibroFr();
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

        View v = inflater.inflate(R.layout.fragment_reg_libro, container, false);

        mAuth = FirebaseAuth.getInstance();
        //TODO COMPROBAR QUE EL USUARIO SEA FUNCIONARIO O ACADEMICO

        rvLib = (RecyclerView) v.findViewById(R.id.recyclerRegLib);
        //rv.setHasFixedSize(true);
        rvLib.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLib.setItemAnimator(new DefaultItemAnimator());
        rvLib.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        reservasLib = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapterLib = new AdapterHistorialReservas(reservasLib);


        rvLib.setAdapter(adapterLib);

        Log.i("ADAPTER", "Adapter creadoOOOOO");
        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        final DatabaseReference reservasRef = proyectoRef.child(FirebaseReferences.RESERVA_REFERENCE);
        final DatabaseReference recursosRef= proyectoRef.child(FirebaseReferences.RECURSOS_REFERENCE);
        final DatabaseReference librosResRef = reservasRef.child(FirebaseReferences.LIBROS_REFERENCE);
        final DatabaseReference librosRecRef = recursosRef.child(FirebaseReferences.LIBROS_REFERENCE);

        rvLib.addOnItemTouchListener(new RecyclerTouchListener(getContext(), rvLib, new RecyclerTouchListener.ClickListener() {
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

                Toast.makeText(getContext(), reserva.getIdReserva() + " recurso regresado", Toast.LENGTH_SHORT).show();
                adapterLib.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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
        return v;
    }

}
