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
import com.nise.jbookproject.Modulos.Computador;
import com.nise.jbookproject.Modulos.FirebaseReferences;
import com.nise.jbookproject.Modulos.RecyclerTouchListener;
import com.nise.jbookproject.Modulos.Reserva;
import com.nise.jbookproject.Modulos.TipoComputador;
import com.nise.jbookproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservarComputador extends AppCompatActivity {
    //Button buttonReserva;
    Button buttonCrear;
    //EditText textId;
    RecyclerView rv;
    List<Computador> computadores;
    AdapterComputador adapterComputador;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_computador);
        //buttonReserva = (Button) findViewById(R.id.reservarButton);
        buttonCrear = (Button) findViewById(R.id.crearComp);
        ///textId = (EditText) findViewById(R.id.idComputador);
        rv = (RecyclerView) findViewById(R.id.recyclerComputador);
        rv.setLayoutManager(new LinearLayoutManager(this));
        computadores = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        adapterComputador = new AdapterComputador(computadores);
        Log.i("ADAPTER", "Adapter creado");

        rv.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapterComputador);


        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        final DatabaseReference reservaRef = proyectoRef.child(FirebaseReferences.RESERVA_REFERENCE);
        final DatabaseReference recursosRef= proyectoRef.child(FirebaseReferences.RECURSOS_REFERENCE);
        final DatabaseReference computadoresRecRef = recursosRef.child(FirebaseReferences.COMPUTADORES_REFERENCE);
        final DatabaseReference computadoresResRef = reservaRef.child(FirebaseReferences.COMPUTADORES_REFERENCE);

        rv.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Computador computador = computadores.get(position);
                Toast.makeText(getApplicationContext(), computador.getId() + " reservado", Toast.LENGTH_SHORT).show();
                computador.setReservado(true);
                computadoresRecRef.child(computador.getId()).setValue(computador);

                Date fecha_inicio, fecha_fin = null;
                fecha_inicio = Calendar.getInstance().getTime();

                //Editar
                mAuth = FirebaseAuth.getInstance();
                String idUser = mAuth.getUid();
                Log.i("USER ","Usuario :"+idUser);
                //Editar

                Reserva reserva = new Reserva("Prueba",idUser,computadores.get(position).getId(),computadores.get(position).getDescripcion(),true,fecha_inicio,fecha_fin);
                DatabaseReference miReserva = computadoresResRef.push();
                reserva.setIdReserva(miReserva.getKey().toString());
                miReserva.setValue(reserva);

                adapterComputador.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        computadoresRecRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                computadores.removeAll(computadores);
                Log.i("ADAPTER", "DataSnapshot"+dataSnapshot.toString());
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()
                        ) {
                    Computador computador = snapshot.getValue(Computador.class);
                    Log.i("ADAPTER",computador.toString());
                    if(!computador.isReservado())
                    {
                        computadores.add(computador);
                    }
                }
                adapterComputador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference miComputador = computadoresRecRef.push();
                Computador computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"piso 2", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"piso 2", TipoComputador.MESA);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"piso 2", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"piso 2", TipoComputador.MESA);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"piso 2", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"piso 2", TipoComputador.MESA);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"piso 2", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"piso 2", TipoComputador.MESA);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"piso 2", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"sotano 1", TipoComputador.MESA);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"sotano 1", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"sotano 1", TipoComputador.MESA);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"sotano 1", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"sotano 1", TipoComputador.MESA);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"sotano 1", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"sotano 1", TipoComputador.MESA);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"sotano 1", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"sotano 2", TipoComputador.MESA);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"sotano 2", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"sotano 2", TipoComputador.MESA);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"sotano 2", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"sotano 2", TipoComputador.MESA);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"sotano 2", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"sotano 2", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"sotano 2", TipoComputador.MESA);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"sotano 2", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"sotano 2", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"piso 3", TipoComputador.MESA);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"Portátil LENOVO - 320 - Intel Core I3-6006U - 17.3 Pulgadas - Disco Duro 2TB - Gris",false,"piso 3", TipoComputador.PORTATIL);
                miComputador.setValue(computador);
                miComputador = computadoresRecRef.push();
                computador = new Computador(miComputador.getKey().toString(),"PC All in One HP - 24-G218 - Intel Core i3 - 23.8 Pulgadas - Disco Duro 1Tb - Negro",false,"piso 3", TipoComputador.MESA);
                miComputador.setValue(computador);


            }
        });
    }
}
