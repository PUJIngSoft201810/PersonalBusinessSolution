package com.nise.jbookproject.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nise.jbookproject.Actividades.ReservarSala;
import com.nise.jbookproject.Adaptadores.AdapterDiaHora;
import com.nise.jbookproject.Adaptadores.AdapterSala;
import com.nise.jbookproject.Modulos.Reserva;
import com.nise.jbookproject.Modulos.Sala;
import com.nise.jbookproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Queue;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Dia1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dia1 extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SALA_ACTUAL = "param1";
    private static final String FECHA_ACTUAL = "param2";

    private Sala mParam1;
    private Date mParam2;

    private static final String TAG = "Dia1";
    DatabaseReference mDatabase;

    ArrayList<Reserva> horas;
    RecyclerView recyclerView;
    AdapterDiaHora adapterDiaHora;

    private TextView titulo;

    // TODO: poner adapterHora

    public Dia1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dia1.
     */
    // TODO: Rename and change types and number of parameters
    public static Dia1 newInstance(Sala param1, Date param2) {
        Dia1 fragment = new Dia1();
        Bundle args = new Bundle();
        args.putSerializable(SALA_ACTUAL, param1);
        args.putSerializable(FECHA_ACTUAL, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (Sala) getArguments().getSerializable(SALA_ACTUAL);
            mParam2 = (Date) getArguments().getSerializable(FECHA_ACTUAL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dia1, container, false);

        horas = new ArrayList<Reserva>();

        recyclerView = (RecyclerView) v.findViewById(R.id.dia1RecyclerHoras);
        // Establece la lista lineal y vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapterDiaHora = new AdapterDiaHora(horas);

        recyclerView.setAdapter(adapterDiaHora);

        titulo = v.findViewById(R.id.tituloDia1);
        titulo.setText("");
        //titulo.setText(mParam2.toString());
        llenarLista();
        configurarDB();
        return v;
    }

    private void configurarDB() {
        String idRecurso = this.mParam1.getId();
        Calendar fechaPrimera = Calendar.getInstance();
        fechaPrimera.setTime(mParam2);
        fechaPrimera.set(Calendar.HOUR_OF_DAY,0);
        fechaPrimera.set(Calendar.MINUTE,0);
        fechaPrimera.set(Calendar.SECOND,0);
        long milisFechaPrimera = fechaPrimera.getTimeInMillis();
        fechaPrimera.set(Calendar.HOUR_OF_DAY, 23);
        fechaPrimera.set(Calendar.MINUTE,59);
        fechaPrimera.set(Calendar.SECOND,59);
        long milisFechaSegunda = fechaPrimera.getTimeInMillis();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("proyecto").child("reservas").child("salas");
        Query reservasSala =
                mDatabase.equalTo(idRecurso).orderByChild("fecha_inicio/time");


        reservasSala.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                //Nueva reserva de sala agregada
                Reserva reserva = dataSnapshot.getValue(Reserva.class);
                horas.add(reserva);
                // Actualizo el adapter con los nuevos cambios en la lista
                adapterDiaHora.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                // Sala modificada
                Reserva reservaEditada =  dataSnapshot.getValue(Reserva.class);
                int i = getPosicionReserva(reservaEditada);
                horas.set(i, reservaEditada);
                // Actualizo el adapter con los nuevos cambios en la lista
                adapterDiaHora.notifyDataSetChanged();;
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                // Reserva eliminada
                Reserva reservaEliminada =  dataSnapshot.getValue(Reserva.class);
                horas.remove(reservaEliminada);
                // Actualizo el adapter con los nuevos cambios en la lista
                adapterDiaHora.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //No aplica
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postSalas:onCancelled", databaseError.toException());
                Toast.makeText(getContext(), "Failed to load reservas.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private int getPosicionReserva(Reserva reservaEditada) {
        int i = 0;
        int posicion = -1;
        boolean sinEncontrar = true;
        while (i < horas.size() && sinEncontrar) {
            if (reservaEditada.getIdReserva().equals(horas.get(i).getIdReserva())) {
                posicion = i;
                sinEncontrar = false;
            }
            i++;
        }
        return posicion;
    }

    private void llenarLista() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mParam2);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        for (int i = 0; i < 8; i++) {
            Reserva nuevaReserva = new Reserva("-1", "-1", mParam1.getId(),"sala",
                    false,calendar.getTime(), calendar.getTime());
            Calendar calendar1Copia = calendar;
            calendar1Copia.add(Calendar.HOUR_OF_DAY, 3);
            nuevaReserva.setFecha_fin(calendar.getTime());
            horas.add(nuevaReserva);
            calendar = calendar1Copia;
        }

        adapterDiaHora.notifyDataSetChanged();
    }
}
