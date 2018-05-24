package com.nise.jbookproject.Fragmentos;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nise.jbookproject.Adaptadores.AdapterDiaHora;
import com.nise.jbookproject.Modulos.RecyclerTouchListener;
import com.nise.jbookproject.Modulos.Reserva;
import com.nise.jbookproject.Modulos.Sala;
import com.nise.jbookproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Dia5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dia5 extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SALA_ACTUAL = "param1";
    private static final String FECHA_ACTUAL = "param2";

    private Sala mParam1;
    private Date mParam2;

    private static final String TAG = "Dia5";
    DatabaseReference mDatabase;

    private AlertDialog.Builder alertBuilder;

    ArrayList<Reserva> horas;
    RecyclerView recyclerView;
    AdapterDiaHora adapterDiaHora;

    private TextView titulo;

    public Dia5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dia5.
     */
    // TODO: Rename and change types and number of parameters
    public static Dia5 newInstance(Sala param1, Date param2) {
        Dia5 fragment = new Dia5();
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
        View v = inflater.inflate(R.layout.fragment_dia5, container, false);

        horas = new ArrayList<Reserva>();

        recyclerView = (RecyclerView) v.findViewById(R.id.dia5RecyclerHoras);
        // Establece la lista lineal y vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        adapterDiaHora = new AdapterDiaHora(horas);

        recyclerView.setAdapter(adapterDiaHora);

        titulo = v.findViewById(R.id.tituloDia5);
        titulo.setText("");
        //titulo.setText(mParam2.toString());
        configurarAlertDialog();
        llenarLista();
        eventoClickRow();
        configurarDB();
        return v;
    }

    private void configurarDB() {
        String idRecurso = this.mParam1.getId();
        Calendar fechaPrimera = Calendar.getInstance();
        fechaPrimera.setTime(mParam2);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mDatabaseQuery = mDatabase.child("proyecto").child("reservas").child("salas");
        Query reservasSala =
                mDatabaseQuery.orderByChild("idRecurso")
                        .equalTo(idRecurso);

        reservasSala.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                //Nueva reserva de sala agregada
                Reserva reserva = dataSnapshot.getValue(Reserva.class);
                Calendar manejoFecha = Calendar.getInstance();
                manejoFecha.setTime(reserva.getFecha_inicio());
                if (fechaMisma(reserva.getFecha_inicio(), mParam2)) {
                    int horaReserva = manejoFecha.get(Calendar.HOUR_OF_DAY);
                    int iNuevo = getPosByHour(horaReserva);
                    horas.set(iNuevo, reserva);
                    // Actualizo el adapter con los nuevos cambios en la lista
                    adapterDiaHora.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                //Nueva reserva de sala agregada
                Reserva reserva = dataSnapshot.getValue(Reserva.class);
                Calendar manejoFecha = Calendar.getInstance();
                manejoFecha.setTime(reserva.getFecha_inicio());
                if (fechaMisma(reserva.getFecha_inicio(), mParam2)) {
                    int horaReserva = manejoFecha.get(Calendar.HOUR_OF_DAY);
                    int iNuevo = getPosByHour(horaReserva);
                    horas.set(iNuevo, reserva);
                    // Actualizo el adapter con los nuevos cambios en la lista
                    adapterDiaHora.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
                // Reserva eliminada
                Reserva reservaEliminada = dataSnapshot.getValue(Reserva.class);
                Calendar manejoFecha = Calendar.getInstance();
                manejoFecha.setTime(reservaEliminada.getFecha_inicio());
                if (fechaMisma(reservaEliminada.getFecha_inicio(), mParam2)) {
                    int horaReserva = manejoFecha.get(Calendar.HOUR_OF_DAY);
                    int iNuevo = getPosByHour(horaReserva);
                    //Se pone no disponible
                    reservaEliminada.setActiva(false);
                    // Actualizo el adapter con los nuevos cambios en la lista
                    horas.set(iNuevo, reservaEliminada);
                    adapterDiaHora.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //No aplica
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postSalas:onCancelled", databaseError.toException());
            }
        });

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

    private int getPosByHour(int hora) {
        int resultado = hora*7/22;
        return resultado;
    }

    private boolean fechaMisma(Date fecha1, Date fecha2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(fecha1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(fecha2);
        int ano1 = calendar1.get(Calendar.YEAR);
        int mes1 = calendar1.get(Calendar.MONTH);
        int dia1 = calendar1.get(Calendar.DAY_OF_MONTH);
        int ano2 = calendar2.get(Calendar.YEAR);
        int mes2 = calendar2.get(Calendar.MONTH);
        int dia2 = calendar2.get(Calendar.DAY_OF_MONTH);
        return ano1 == ano2 && mes1 == mes2 && dia1 == dia2;
    }

    private void eventoClickRow() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Reserva reservaSelect = horas.get(position);
                DatabaseReference p1;
                DatabaseReference p2;
                if (reservaSelect.getActiva()) {
                    Toast.makeText(getContext(), "El horario seleccionado no se encuentra disponible",
                            Toast.LENGTH_SHORT).show();
                }else {
                    reservaSelect.setActiva(true);
                    reservaSelect.setIdUsuario(FirebaseAuth.getInstance().getUid());
                    reservaSelect.setIdRecurso(mParam1.getId());
                    reservaSelect.setRecurso("sala");
                    p1 = mDatabase.child("proyecto").child("reservas").
                            child("salas").push();
                    reservaSelect.setIdReserva(p1.getKey().toString());
                    p1.setValue(reservaSelect);
                    AlertDialog nuevoAlertDialog = alertBuilder.create();
                    nuevoAlertDialog.setMessage("Sala " + mParam1.getId().substring(mParam1.getId().length() - 3) + " Asignada.");
                    nuevoAlertDialog.show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void configurarAlertDialog(){
        alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setTitle("Exito Reserva");

        alertBuilder.setMessage("Se ha reservado la sala")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }
}
