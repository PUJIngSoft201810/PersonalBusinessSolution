package com.nise.jbookproject.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nise.jbookproject.Modulos.Administrador;
import com.nise.jbookproject.Modulos.FirebaseReferences;
import com.nise.jbookproject.R;


public class MenuUser extends AppCompatActivity implements View.OnClickListener {
    Button buttonConsulta, buttonReserva, buttonRegresar, buttonCerrar,buttonRegistrar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database ;

    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);
        buttonConsulta = (Button) findViewById(R.id.consultaButton);
        buttonReserva = (Button) findViewById(R.id.reservaButton);
        buttonRegresar = (Button) findViewById(R.id.regresarButton);
        buttonCerrar = (Button) findViewById(R.id.cerrarButton);
        buttonRegistrar =(Button) findViewById(R.id.registrarButton) ;
        buttonConsulta.setOnClickListener(this);
        if (isAdmin())
            buttonRegistrar.setActivated(true);
        else
            buttonRegistrar.setActivated(false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        buttonReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuUser.this, MenuReserva.class));
            }
        });
        buttonConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuUser.this, HistorialReservasT.class));
            }
        });
        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuUser.this, ConsultarReservasActivas.class));
            }
        });
        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuUser.this, RegistroAdm.class));
            }
        });
        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MenuUser.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private boolean isAdmin() {

        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        final DatabaseReference usuarioRef = proyectoRef.child(FirebaseReferences.USUARIO_REFERENCE);
        final DatabaseReference adminRef = usuarioRef.child(FirebaseReferences.ADMNIISTRADOR_REFERENCE);
        final String idUser = mAuth.getUid();
        final boolean[] isAdmin = {false};
        Query ELAdmin =
                adminRef.orderByChild("idU");

// Attach a listener to read the data at our posts reference
        ELAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int cont=0;
                for (DataSnapshot snap:dataSnapshot.getChildren()){
                    Administrador adm = dataSnapshot.getValue(Administrador.class);
                    System.out.println(adm.toString());
                    if(adm.getId().equals(idUser)){
                        isAdmin[0] |=true;
                    }
                    cont++;
                }
                if(cont==0)
                    isAdmin[0] =true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return isAdmin[0];
    }

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
            Intent intent = new Intent(this, HistorialReservas.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
