package com.nise.jbookproject.Actividades;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nise.jbookproject.Modulos.AdapterComputador;
import com.nise.jbookproject.Modulos.AdapterConsola;
import com.nise.jbookproject.Modulos.Computador;
import com.nise.jbookproject.Modulos.Consola;
import com.nise.jbookproject.Modulos.FirebaseReferences;
import com.nise.jbookproject.Modulos.RecyclerTouchListener;
import com.nise.jbookproject.Modulos.Reserva;
import com.nise.jbookproject.Modulos.TipoComputador;
import com.nise.jbookproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservarConsola extends AppCompatActivity {
    //Button buttonReserva;
    Button buttonCrear;
    //EditText textId;
    RecyclerView rv;
    List<Consola> consolas;
    AdapterConsola adapterConsola;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    ArrayList<Consola> listConsolas;
    TextView cantidadConsolas;
    private AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_consola);
        //buttonCrear = (Button) findViewById(R.id.crearCons);

        //consolas = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        cantidadConsolas = findViewById(R.id.textCantConsolas);
        cantidadConsolas.setText("0");

        configurarAlertDialog();
        configDB();

    }

    public void configDB() {
        listConsolas = new ArrayList<Consola>();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase = mDatabase.child("proyecto").child("recursos").child("consolas");
        //Query queryConsolas = mDatabase.orderByChild("reservado").equalTo(false);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("ReservarConsola", "onChildAdded:" + dataSnapshot.getKey());
                Consola consola = dataSnapshot.getValue(Consola.class);
                if (!consola.isReservado()) {
                    listConsolas.add(consola);
                    cantidadConsolas.setText(listConsolas.size()+"");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("ReservarConsola", "onChildChanged:" + dataSnapshot.getKey());
                Consola consola =  dataSnapshot.getValue(Consola.class);
                if (consola.isReservado()) {
                    int i = getPosicionConsola(consola);
                    listConsolas.remove(i);
                    cantidadConsolas.setText(listConsolas.size()+"");
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("ReservarConsola", "onChildRemoved:" + dataSnapshot.getKey());
                Consola consola =  dataSnapshot.getValue(Consola.class);
                if (!consola.isReservado()) {
                    listConsolas.remove(consola);
                    cantidadConsolas.setText(listConsolas.size() + "");
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //No aplica
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ReservarConsolas", "postConsolas:onCancelled", databaseError.toException());
                Toast.makeText(ReservarConsola.this, "Failed to load consolas.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mDatabase.addChildEventListener(childEventListener);
    }

    public void reservarConsola(View view) {

        DatabaseReference consolasRecRef = FirebaseDatabase.getInstance().getReference().
                child("proyecto").child("recursos").child("consolas");
        DatabaseReference consolasResRef = FirebaseDatabase.getInstance().getReference().
                child("proyecto").child("reservas").child("consolas");

        Consola consola = obtenerConsolaDisponible();
        Toast.makeText(getApplicationContext(), consola.getId() + " reservado", Toast.LENGTH_SHORT).show();
        consola.setReservado(true);
        consolasRecRef.child(consola.getId()).setValue(consola);

        Date fecha_inicio, fecha_fin = null;
        fecha_inicio = Calendar.getInstance().getTime();

        //Editar
        mAuth = FirebaseAuth.getInstance();
        String idUser = mAuth.getUid();
        Log.i("Reservar Consola","Consola reservada por "+idUser);
        //Editar

        Reserva reserva = new Reserva("Prueba",idUser,consola.getId(),consola.getDescripcion(),true,fecha_inicio,fecha_fin);
        DatabaseReference miReserva = consolasResRef.push();
        reserva.setIdReserva(miReserva.getKey().toString());
        miReserva.setValue(reserva);

        AlertDialog nuevoAlertDialog = alertBuilder.create();
        nuevoAlertDialog.setMessage("Consola " + consola.getId().substring(consola.getId().length() - 3) + " Asignada.");
        nuevoAlertDialog.show();
    }

    private Consola obtenerConsolaDisponible() {
        Consola consolaAsignada;
        if (listConsolas.size() <= 0) {
            consolaAsignada = null;
        }else {
            consolaAsignada = listConsolas.get(0);
            //listConsolas.remove(0);
            //cantidadConsolas.setText(listConsolas.size()+"");
        }

        return consolaAsignada;
    }

    private int getPosicionConsola(Consola consolaEditada) {
        int i = 0;
        int posicion = -1;
        ArrayList<Consola> listaActual = listConsolas;

        boolean sinEncontrar = true;
        while (i < listaActual.size() && sinEncontrar) {
            if (consolaEditada.getId().equals(listaActual.get(i).getId())) {
                posicion = i;
                sinEncontrar = false;
            }
            i++;
        }
        return posicion;
    }

    private void configurarAlertDialog(){
        alertBuilder = new AlertDialog.Builder(ReservarConsola.this);
        alertBuilder.setTitle("Exito Reserva");

        alertBuilder.setMessage("Se ha reservado la consola")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }
}
