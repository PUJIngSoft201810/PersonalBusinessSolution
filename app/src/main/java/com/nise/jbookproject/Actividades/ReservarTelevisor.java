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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nise.jbookproject.Modulos.AdapterConsola;
import com.nise.jbookproject.Modulos.AdapterTelevisor;
import com.nise.jbookproject.Modulos.Consola;
import com.nise.jbookproject.Modulos.FirebaseReferences;
import com.nise.jbookproject.Modulos.RecyclerTouchListener;
import com.nise.jbookproject.Modulos.Reserva;
import com.nise.jbookproject.Modulos.Televisor;
import com.nise.jbookproject.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservarTelevisor extends AppCompatActivity {
    Button buttonCrear;
    RecyclerView rv;
    List<Televisor> televisores;
    AdapterTelevisor adapterTelevisor;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ArrayList<Televisor> listTelevisores;
    private TextView cantidadTelevisores;
    private AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_televisor);

        //televisores = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        cantidadTelevisores = findViewById(R.id.textCantTele);
        cantidadTelevisores.setText("0");

        configurarAlertDialog();
        configDB();



    }

    public void configDB() {
        listTelevisores = new ArrayList<Televisor>();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase = mDatabase.child("proyecto").child("recursos").child("televisores");
        //Query queryConsolas = mDatabase.orderByChild("reservado").equalTo(false);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("ReservarTelevisor", "onChildAdded:" + dataSnapshot.getKey());
                Televisor televisor = dataSnapshot.getValue(Televisor.class);
                if (!televisor.isReservado()) {
                    listTelevisores.add(televisor);
                    cantidadTelevisores.setText(listTelevisores.size()+"");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("ReservarTelevisor", "onChildChanged:" + dataSnapshot.getKey());
                Televisor televisor = dataSnapshot.getValue(Televisor.class);
                if (televisor.isReservado()) {
                    int i = getPosicionTelevisor(televisor);
                    listTelevisores.remove(i);
                    cantidadTelevisores.setText(listTelevisores.size()+"");
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("ReservarTelevisor", "onChildRemoved:" + dataSnapshot.getKey());
                Televisor televisor = dataSnapshot.getValue(Televisor.class);
                if (!televisor.isReservado()) {
                    listTelevisores.remove(televisor);
                    cantidadTelevisores.setText(listTelevisores.size() + "");
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //No aplica
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ReservarTelevisor", "postTelevisor:onCancelled", databaseError.toException());
                Toast.makeText(ReservarTelevisor.this, "Failed to load Televisores.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mDatabase.addChildEventListener(childEventListener);
    }

    private Televisor obtenerTelevisorDisponible() {
        Televisor televisorAsignado;
        if (listTelevisores.size() <= 0) {
            televisorAsignado = null;
        }else {
            televisorAsignado = listTelevisores.get(0);
            //listConsolas.remove(0);
            //cantidadConsolas.setText(listConsolas.size()+"");
        }

        return televisorAsignado;
    }

    private int getPosicionTelevisor(Televisor televisorEditado) {
        int i = 0;
        int posicion = -1;
        ArrayList<Televisor> listaActual = listTelevisores;

        boolean sinEncontrar = true;
        while (i < listaActual.size() && sinEncontrar) {
            if (televisorEditado.getId().equals(listaActual.get(i).getId())) {
                posicion = i;
                sinEncontrar = false;
            }
            i++;
        }
        return posicion;
    }

    private void configurarAlertDialog(){
        alertBuilder = new AlertDialog.Builder(ReservarTelevisor.this);
        alertBuilder.setTitle("Exito Reserva");

        alertBuilder.setMessage("Se ha reservado la televisor")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
    }

    public void reservarTelevisor(View view) {

        DatabaseReference televisoresRecRef = FirebaseDatabase.getInstance().getReference().
                child("proyecto").child("recursos").child("televisores");
        DatabaseReference televisoresResRef = FirebaseDatabase.getInstance().getReference().
                child("proyecto").child("reservas").child("televisores");

        Televisor televisor = obtenerTelevisorDisponible();
        //Toast.makeText(getApplicationContext(), televisor.getId() + " reservado", Toast.LENGTH_SHORT).show();
        televisor.setReservado(true);
        televisoresRecRef.child(televisor.getId()).setValue(televisor);

        Date fecha_inicio, fecha_fin = null;
        fecha_inicio = Calendar.getInstance().getTime();

        //Editar
        mAuth = FirebaseAuth.getInstance();
        String idUser = mAuth.getUid();
        Log.i("USER ","Usuario :"+idUser);
        //Editar

        Reserva reserva = new Reserva("Prueba",idUser,televisor.getId(),televisor.getDescripcion(),true,fecha_inicio,fecha_fin);
        DatabaseReference miReserva = televisoresResRef.push();
        reserva.setIdReserva(miReserva.getKey().toString());
        miReserva.setValue(reserva);

        AlertDialog nuevoAlertDialog = alertBuilder.create();
        nuevoAlertDialog.setMessage("Televisor " + televisor.getId().substring(televisor.getId().length() - 3) + " Asignado.");
        nuevoAlertDialog.show();
    }
}

