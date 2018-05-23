package com.nise.jbookproject.Actividades;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nise.jbookproject.Modulos.Academico;
import com.nise.jbookproject.Modulos.Administrador;
import com.nise.jbookproject.Modulos.Eacademico;
import com.nise.jbookproject.Modulos.FirebaseReferences;
import com.nise.jbookproject.Modulos.Funcionario;
import com.nise.jbookproject.Modulos.Reserva;
import com.nise.jbookproject.Modulos.Usuario;
import com.nise.jbookproject.R;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    Button  buttonRegister;
    EditText editTextEmail, editTextPass, editTextName, editTextLastName,editTextIdent;
    Spinner spinnAcad;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_registro);
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
       database = FirebaseDatabase.getInstance();

        buttonRegister = (Button) findViewById(R.id.register);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPass = (EditText) findViewById(R.id.password);
        editTextName = (EditText) findViewById(R.id.name);
        editTextLastName = (EditText) findViewById(R.id.LastName);
        editTextIdent=(EditText) findViewById(R.id.ident);
        spinnAcad=(Spinner) findViewById(R.id.SpinnerAcad) ;
        buttonRegister.setOnClickListener(this);


    }
    private void registrar() {
        if(validateForm()){
            String email = editTextEmail.getText().toString();
            String password = editTextPass.getText().toString();
            String name = editTextName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            String identificacion=editTextIdent.getText().toString();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("SESION","signInWithEmail:onComplete:" + task.isSuccessful());
                    if(task.isSuccessful()){
                        Log.i("SESION", "usuario creado correctamente");
                    }
                    else{
                        Log.w("SESION","signInWithEmail:failed", task.getException());
                        Toast.makeText(Registro.this,"Fallo: Usuario existente", Toast.LENGTH_SHORT).show();
                        editTextEmail.setText("");
                        editTextPass.setText("");
                        editTextName.setText("");
                        editTextLastName.setText("");
                        editTextIdent.setText("");
                    }
                }
            });
            String tipoAcad= String.valueOf(spinnAcad.getSelectedItem());

            final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
            final DatabaseReference usuarioRef = proyectoRef.child(FirebaseReferences.USUARIO_REFERENCE);
            final DatabaseReference academicoRef = usuarioRef.child(FirebaseReferences.ACADEMICO_REFERENCE);

            String idUser = mAuth.getUid();

                Eacademico estadoAcad=Eacademico.ESTUDIANTE;
                if(tipoAcad.equals("Estudiante"))
                    estadoAcad=Eacademico.ESTUDIANTE;
                else if(tipoAcad.equals("Profesor"))
                    estadoAcad=Eacademico.PROFESOR;
                else if(tipoAcad.equals("Empleado"))
                    estadoAcad=Eacademico.EMPLEADO;
                Academico us =new Academico(idUser, email, name, lastName, identificacion, estadoAcad);
                DatabaseReference miUsuario = academicoRef.push();
                us.setidref(miUsuario.getKey().toString());
                miUsuario.setValue(us);
                Log.i("USER ","academico in if");

        }
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = editTextEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Required.");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }
        String password = editTextPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editTextPass.setError("Required.");
            valid = false;
        } else {
            editTextPass.setError(null);
        }
        String name = editTextPass.getText().toString();
        if (TextUtils.isEmpty(name)) {
            editTextPass.setError("Required.");
            valid = false;
        } else {
            editTextPass.setError(null);
        }
        String lastName = editTextPass.getText().toString();
        if (TextUtils.isEmpty(lastName)) {
            editTextPass.setError("Required.");
            valid = false;
        } else {
            editTextPass.setError(null);
        }
        String identificacion = editTextPass.getText().toString();
        if (TextUtils.isEmpty(identificacion)) {
            editTextPass.setError("Required.");
            valid = false;
        } else {
            editTextPass.setError(null);
        }

        return valid;
    }


    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.register:
                registrar();
                startActivity(new Intent(Registro.this, MenuUser.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


}

