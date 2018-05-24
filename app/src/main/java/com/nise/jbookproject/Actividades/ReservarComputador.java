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
import java.util.Queue;

public class ReservarComputador extends AppCompatActivity {
    //Button buttonReserva;
    Button buttonCrear;
    //EditText textId;
    //RecyclerView rv;
    List<Computador> computadores;
    //AdapterComputador adapterComputador;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ArrayList<Computador> compMesa;
    private ArrayList<Computador> compPortatil;
    private TextView contCompMesa;
    private TextView contCompPortatil;
    private AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_computador);

        contCompMesa = findViewById(R.id.textCantMesa);
        contCompPortatil = findViewById(R.id.textCantPortatil);

        contCompMesa.setText("0");
        contCompPortatil.setText("0");

        computadores = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        configurarAlertDialog();
        configDB();

        //contCompPortatil.setText(compPortatil.size()+"");
        //contCompPortatil.setText(compMesa.size()+"");

    }

    public void reservarComputadorPortatil(View view) {
        DatabaseReference computadoresRecRef = FirebaseDatabase.getInstance().getReference().
                                    child("proyecto").child("recursos").child("computadores");
        DatabaseReference computadoresResRef = FirebaseDatabase.getInstance().getReference().
                                    child("proyecto").child("reservas").child("computadores");
        Computador computador = obtenerCompPortatilDisponible();
        //Toast.makeText(getApplicationContext(), computador.getId() + " reservado", Toast.LENGTH_SHORT).show();
        computador.setReservado(true);
        computadoresRecRef.child(computador.getId()).setValue(computador);

        Date fecha_inicio, fecha_fin = null;
        fecha_inicio = Calendar.getInstance().getTime();

        //Editar
        mAuth = FirebaseAuth.getInstance();
        String idUser = mAuth.getUid();
        Log.i("USER ","Usuario :"+idUser);
        //Editar

        Reserva reserva = new Reserva("Prueba", idUser, computador.getId(), computador.getDescripcion(),true,fecha_inicio,fecha_fin);
        DatabaseReference miReserva = computadoresResRef.push();
        reserva.setIdReserva(miReserva.getKey().toString());
        miReserva.setValue(reserva);
        AlertDialog nuevoAlertDialog = alertBuilder.create();
        nuevoAlertDialog.setMessage("Portátil " + computador.getId().substring(computador.getId().length() - 3) + " reservado.");
        nuevoAlertDialog.show();
    }

    public void reservarComputadorMesa(View view) {
        DatabaseReference computadoresRecRef = FirebaseDatabase.getInstance().getReference().
                child("proyecto").child("recursos").child("computadores");
        DatabaseReference computadoresResRef = FirebaseDatabase.getInstance().getReference().
                child("proyecto").child("reservas").child("computadores");
        Computador computador = obtenerCompMesaDisponible();
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

        Reserva reserva = new Reserva("Prueba", idUser, computador.getId(), computador.getDescripcion(),true,fecha_inicio,fecha_fin);
        DatabaseReference miReserva = computadoresResRef.push();
        reserva.setIdReserva(miReserva.getKey().toString());
        miReserva.setValue(reserva);

        AlertDialog nuevoAlertDialog = alertBuilder.create();
        nuevoAlertDialog.setMessage("Comp. Mesa " + computador.getId().substring(computador.getId().length() - 3) + " reservado.");
        nuevoAlertDialog.show();
    }

    public void configDB() {
        compMesa = new ArrayList<Computador>();
        compPortatil = new ArrayList<Computador>();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase = mDatabase.child("proyecto").child("recursos").child("computadores");
        //Query queryPortatiles = mDatabase.orderByChild("reservado").equalTo(false);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("ReservarComp", "onChildAdded:" + dataSnapshot.getKey());
                Computador computador = dataSnapshot.getValue(Computador.class);
                if (!computador.isReservado()) {
                    if (computador.getTipo() == TipoComputador.MESA) {
                        compMesa.add(computador);
                        contCompMesa.setText(compMesa.size() + "");
                    } else {
                        compPortatil.add(computador);
                        contCompPortatil.setText(compPortatil.size() + "");
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("ReservarComp", "onChildChanged:" + dataSnapshot.getKey());
                Computador computador =  dataSnapshot.getValue(Computador.class);
                if (computador.isReservado()) {
                    int i = getPosicionComp(computador);
                    if (computador.getTipo() == TipoComputador.MESA) {
                        compMesa.remove(i);
                        contCompMesa.setText(compMesa.size() + "");
                    }else {
                        compPortatil.remove(i);
                        contCompPortatil.setText(compPortatil.size() + "");
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("ReservarComp", "onChildRemoved:" + dataSnapshot.getKey());
                Computador computador =  dataSnapshot.getValue(Computador.class);
                if (!computador.isReservado()) {
                    if (computador.getTipo() == TipoComputador.MESA) {
                        compMesa.remove(computador);
                        contCompMesa.setText(compMesa.size() + "");
                    } else {
                        compPortatil.remove(computador);
                        contCompPortatil.setText(compPortatil.size() + "");
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //No aplica
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ReservarComp", "postSalas:onCancelled", databaseError.toException());
                Toast.makeText(ReservarComputador.this, "Failed to load computadores.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getPosicionComp(Computador compEditada) {
        int i = 0;
        int posicion = -1;
        ArrayList<Computador> listaActual;
        if (compEditada.getTipo() == TipoComputador.MESA) {
            listaActual = compMesa;
        }else {
            listaActual = compPortatil;
        }
        boolean sinEncontrar = true;
        while (i < listaActual.size() && sinEncontrar) {
            if (compEditada.getId().equals(listaActual.get(i).getId())) {
                posicion = i;
                sinEncontrar = false;
            }
            i++;
        }
        return posicion;
    }

    private Computador obtenerCompMesaDisponible() {
        Computador compAsignado;
        if (compMesa.size() <= 0) {
            compAsignado = null;
        }else {
            compAsignado = compMesa.get(0);
            //compMesa.remove(0);
            //contCompMesa.setText(compMesa.size()+"");
        }

        return compAsignado;
    }

    private Computador obtenerCompPortatilDisponible() {
        Computador compAsignado;
        if (compPortatil.size() <= 0) {
            compAsignado = null;
        }else {
            compAsignado = compPortatil.get(0);
            //compPortatil.remove(0);
            //contCompPortatil.setText(compPortatil.size()+"");
        }
        return compAsignado;
    }

    private void configurarAlertDialog(){
        alertBuilder = new AlertDialog.Builder(ReservarComputador.this);
        alertBuilder.setTitle("Exito Reserva");

        alertBuilder.setMessage("Se ha reservado el computador")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
    }
}
