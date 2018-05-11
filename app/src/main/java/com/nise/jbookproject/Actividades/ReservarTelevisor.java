package com.nise.jbookproject.Actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_televisor);
        buttonCrear = (Button) findViewById(R.id.crearTelevisor);
        rv = (RecyclerView) findViewById(R.id.recyclerTelevisor);
        rv.setLayoutManager(new LinearLayoutManager(this));
        televisores = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        adapterTelevisor = new AdapterTelevisor(televisores);
        Log.i("ADAPTER", "Adapter creado");

        rv.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapterTelevisor);


        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        final DatabaseReference reservaRef = proyectoRef.child(FirebaseReferences.RESERVA_REFERENCE);
        final DatabaseReference recursosRef= proyectoRef.child(FirebaseReferences.RECURSOS_REFERENCE);
        final DatabaseReference televisoresRecRef = recursosRef.child(FirebaseReferences.TELEVISORES_REFERENCE);
        final DatabaseReference televisoresResRef = reservaRef.child(FirebaseReferences.TELEVISORES_REFERENCE);

        rv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Televisor televisor = televisores.get(position);
                Toast.makeText(getApplicationContext(), televisor.getId() + " reservado", Toast.LENGTH_SHORT).show();
                televisor.setReservado(true);
                televisoresRecRef.child(televisor.getId()).setValue(televisor);

                Date fecha_inicio, fecha_fin = null;
                fecha_inicio = Calendar.getInstance().getTime();

                //Editar
                mAuth = FirebaseAuth.getInstance();
                String idUser = mAuth.getUid();
                Log.i("USER ","Usuario :"+idUser);
                //Editar

                Reserva reserva = new Reserva("Prueba",idUser,televisores.get(position).getId(),televisores.get(position).getDescripcion(),true,fecha_inicio,fecha_fin);
                DatabaseReference miReserva = televisoresResRef.push();
                reserva.setIdReserva(miReserva.getKey().toString());
                miReserva.setValue(reserva);

                adapterTelevisor.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        televisoresRecRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                televisores.removeAll(televisores);
                Log.i("ADAPTER", "DataSnapshot"+dataSnapshot.toString());
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()
                        ) {
                    Televisor televisor = snapshot.getValue(Televisor.class);
                    Log.i("ADAPTER",televisor.toString());
                    if(!televisor.isReservado())
                    {
                        televisores.add(televisor);
                    }
                }
                adapterTelevisor.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Televisor televisor = new Televisor("Prueba","Televisor Samsung 201",false,"sotano 1");
                DatabaseReference miTelevisor = televisoresRecRef.push();
                televisor.setId(miTelevisor.getKey().toString());
                miTelevisor.setValue(televisor);
            }
        });
    }
}

