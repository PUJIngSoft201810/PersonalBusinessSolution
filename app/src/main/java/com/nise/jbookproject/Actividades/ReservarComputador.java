package com.nise.jbookproject.Actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nise.jbookproject.Modulos.AdapterComputador;
import com.nise.jbookproject.Modulos.Computador;
import com.nise.jbookproject.Modulos.FirebaseReferences;
import com.nise.jbookproject.Modulos.Reserva;
import com.nise.jbookproject.Modulos.TipoComputador;
import com.nise.jbookproject.R;

import java.util.ArrayList;
import java.util.List;

public class ReservarComputador extends AppCompatActivity {
    Button buttonReserva;
    Button buttonCrear;
    EditText textId;
    RecyclerView rv;
    List<Computador> computadores;
    AdapterComputador adapterComputador;
    int id = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_computador);
        buttonReserva = (Button) findViewById(R.id.reservarButton);
        buttonCrear = (Button) findViewById(R.id.crearComp);
        textId = (EditText) findViewById(R.id.idComputador);
        rv = (RecyclerView) findViewById(R.id.recyclerComputador);
        rv.setLayoutManager(new LinearLayoutManager(this));
        computadores = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        adapterComputador = new AdapterComputador(computadores);
        Log.i("ADAPTER", "Adapter creado");

        rv.setAdapter(adapterComputador);

        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        final DatabaseReference reservaRef = proyectoRef.child(FirebaseReferences.RESERVA_REFERENCE);
        final DatabaseReference recursosRef= proyectoRef.child(FirebaseReferences.RECURSOS_REFEREBCE);
        final DatabaseReference computadoresRef = recursosRef.child(FirebaseReferences.COMPUTADORES_REFERENCE);

        computadoresRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                computadores.removeAll(computadores);
                Log.i("ADAPTER", "DataSnapshot"+dataSnapshot.toString());
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()
                        ) {
                    Computador computador = snapshot.getValue(Computador.class);
                    Log.i("ADAPTER",computador.toString());
                    computadores.add(computador);
                }
                adapterComputador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonReserva.setOnClickListener(new View.OnClickListener() {
            /*if (TextUtils.isEmpty(textId)) {
                textId.setError("Required.");
            } else {
                textId.setError(null);
            }*/
            @Override
            public void onClick(View v) {
                for(int i = 0; i < computadores.size(); i++)
                {
                    Log.i("PRUEBA","ENTRO PARCE "+computadores.get(i).getId());
                    if(computadores.get(i).getId().equals("-LAYnEi8AjRSS218Fs3d"))
                    {
                        Reserva reserva = new Reserva("Prueba",1032428174,computadores.get(i).getId(),computadores.get(i).getDescripcion());
                        DatabaseReference miReserva = reservaRef.push();
                        reserva.setIdReserva(miReserva.getKey().toString());
                        miReserva.setValue(reserva);
                    }
                }
                //Computador computador = new Computador(101,"portail a15",false,"sotano 1", TipoComputador.PORTATIL);
                //computadoresRef.push().setValue(computador);
            }
        });
        buttonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Computador computador = new Computador("Prueba","portail a15",false,"sotano 1", TipoComputador.PORTATIL);
                //computadoresRef.push().setValue(computador);
                DatabaseReference miComputador = computadoresRef.push();
                computador.setId(miComputador.getKey().toString());
                miComputador.setValue(computador);
            }
        });
    }
    private boolean validateForm() {
        boolean valid = true;
        String email = textId.getText().toString();
        if (TextUtils.isEmpty(email)) {
            textId.setError("Required.");
            valid = false;
        } else {
            textId.setError(null);
        }
        return valid;
    }
}
