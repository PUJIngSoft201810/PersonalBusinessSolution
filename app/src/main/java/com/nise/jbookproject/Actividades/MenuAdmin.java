package com.nise.jbookproject.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nise.jbookproject.Modulos.FirebaseReferences;
import com.nise.jbookproject.R;

public class MenuAdmin extends AppCompatActivity {
    Button buttonCrearRecurso, buttonCrearUsuario, buttonCerrar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        buttonCrearRecurso = (Button) findViewById(R.id.crearRecursoButton);
        buttonCrearUsuario = (Button) findViewById(R.id.crearUsuariobutton);
        buttonCerrar = (Button) findViewById(R.id.cerrarButton);


        buttonCrearRecurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuAdmin.this, CrearRecurso.class));
            }
        });
        buttonCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuAdmin.this, CrearUsuario.class));
            }
        });
        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MenuAdmin.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
