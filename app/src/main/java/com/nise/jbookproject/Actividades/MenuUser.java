package com.nise.jbookproject.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nise.jbookproject.Modulos.FirebaseReferences;
import com.nise.jbookproject.R;

public class MenuUser extends AppCompatActivity implements View.OnClickListener {
    Button buttonConsulta, buttonReserva;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override


    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);
        buttonConsulta = (Button) findViewById(R.id.consultaButton);
        buttonReserva = (Button) findViewById(R.id.reservaButton);
        buttonConsulta.setOnClickListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        buttonReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reserva reserva = new Reserva(10452,1032428174,1154,"Portatil");
                //proyectoRef.child(FirebaseReferences.RESERVA_REFERENCE).push().setValue(reserva);
                startActivity(new Intent(MenuUser.this, MenuReserva.class));
                //reservaRef.child(FirebaseReferences.RESERVA_REFERENCE).push().setValue(reserva);
            }
        });
        buttonConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuUser.this, ConsultaReservas.class));
            }
        });
    }
    /*public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.consultaButton:
                startActivity(new Intent(this, ConsultaReservas.class));

                break;
           }
    }*/
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemClicked = item.getItemId();
        if(itemClicked == R.id.menuLogOut){
            mAuth.signOut();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if (itemClicked == R.id.menuSettings){
//Abrir actividad para configuración etc
            Intent intent = new Intent(this, ConsultaReservas.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
