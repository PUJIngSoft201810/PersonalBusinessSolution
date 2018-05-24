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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_consola);
        buttonCrear = (Button) findViewById(R.id.crearCons);
        rv = (RecyclerView) findViewById(R.id.recyclerConsola);
        rv.setLayoutManager(new LinearLayoutManager(this));
        consolas = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        adapterConsola = new AdapterConsola(consolas);
        Log.i("ADAPTER", "Adapter creado");

        rv.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapterConsola);


        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        final DatabaseReference reservaRef = proyectoRef.child(FirebaseReferences.RESERVA_REFERENCE);
        final DatabaseReference recursosRef= proyectoRef.child(FirebaseReferences.RECURSOS_REFERENCE);
        final DatabaseReference consolasRecRef = recursosRef.child(FirebaseReferences.CONSOLAS_REFERENCE);
        final DatabaseReference consolasResRef = reservaRef.child(FirebaseReferences.CONSOLAS_REFERENCE);

        rv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Consola consola = consolas.get(position);
                Toast.makeText(getApplicationContext(), consola.getId() + " reservado", Toast.LENGTH_SHORT).show();
                consola.setReservado(true);
                consolasRecRef.child(consola.getId()).setValue(consola);

                Date fecha_inicio, fecha_fin = null;
                fecha_inicio = Calendar.getInstance().getTime();

                //Editar
                mAuth = FirebaseAuth.getInstance();
                String idUser = mAuth.getUid();
                Log.i("USER ","Usuario :"+idUser);
                //Editar

                Reserva reserva = new Reserva("Prueba",idUser,consolas.get(position).getId(),consolas.get(position).getDescripcion(),true,fecha_inicio,fecha_fin);
                DatabaseReference miReserva = consolasResRef.push();
                reserva.setIdReserva(miReserva.getKey().toString());
                miReserva.setValue(reserva);

                adapterConsola.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        consolasRecRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                consolas.removeAll(consolas);
                Log.i("ADAPTER", "DataSnapshot"+dataSnapshot.toString());
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()
                        ) {
                    Consola consola = snapshot.getValue(Consola.class);
                    Log.i("ADAPTER",consola.toString());
                    if(!consola.isReservado())
                    {
                        consolas.add(consola);
                    }
                }
                adapterConsola.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference miConsola = consolasRecRef.push();
                Consola consola = new Consola(miConsola.getKey().toString(),"Xbox One Plus",false,"sotano 1",2);
                miConsola.setValue(consola);
                miConsola = consolasRecRef.push();
                consola = new Consola(miConsola.getKey().toString(),"Xbox One Plus",false,"sotano 1",3);
                miConsola.setValue(consola);
                miConsola = consolasRecRef.push();
                consola = new Consola(miConsola.getKey().toString(),"Xbox 360 Elite",false,"sotano 1",2);
                miConsola.setValue(consola);
                miConsola = consolasRecRef.push();
                consola = new Consola(miConsola.getKey().toString(),"Xbox 360 Slim",false,"sotano 1",3);
                miConsola.setValue(consola);
                miConsola = consolasRecRef.push();
                consola = new Consola(miConsola.getKey().toString(),"Xbox 360 Slim pro",false,"sotano 1",2);
                miConsola.setValue(consola);
            }
        });
    }
}
