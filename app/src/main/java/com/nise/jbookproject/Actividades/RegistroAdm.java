package com.nise.jbookproject.Actividades;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.nise.jbookproject.R;

public class RegistroAdm extends AppCompatActivity implements View.OnClickListener{
    Button buttonRegister;
    EditText editTextEmail, editTextPass, editTextName, editTextLastName,editTextIdent;
    Spinner spinnType;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_registro_adm);
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        buttonRegister = (Button) findViewById(R.id.register);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPass = (EditText) findViewById(R.id.password);
        editTextName = (EditText) findViewById(R.id.name);
        editTextLastName = (EditText) findViewById(R.id.LastName);
        editTextIdent=(EditText) findViewById(R.id.ident);
        spinnType=(Spinner) findViewById(R.id.SpinnerType) ;
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
                        Toast.makeText(RegistroAdm.this,"Fallo: Usuario existente", Toast.LENGTH_SHORT).show();
                        editTextEmail.setText("");
                        editTextPass.setText("");
                        editTextName.setText("");
                        editTextLastName.setText("");
                        editTextIdent.setText("");
                    }
                }
            });
            String tipo= String.valueOf(spinnType.getSelectedItem());

            final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
            final DatabaseReference usuarioRef = proyectoRef.child(FirebaseReferences.USUARIO_REFERENCE);
            final DatabaseReference funcionarioRef = usuarioRef.child(FirebaseReferences.FUNCIONARIO_REFERENCE);
            final DatabaseReference adminRef = usuarioRef.child(FirebaseReferences.ADMNIISTRADOR_REFERENCE);
            String idUser = mAuth.getUid();

            if(tipo.equals("Funcionario")){

                Funcionario fun= new Funcionario(idUser,email,name,lastName,identificacion);
                DatabaseReference miUsuario = funcionarioRef.child(idUser);
                fun.setIdref(miUsuario.getKey().toString());
                miUsuario.setValue(fun);
                Log.i("USER ","funcionario in if");
            }else if(tipo.equals("Administrador")){
                Administrador adm= new Administrador(idUser,email,name,lastName,identificacion);
                DatabaseReference miUsuario = adminRef.child(idUser);
                adm.setIdref(miUsuario.getKey().toString());
                miUsuario.setValue(adm);
                Log.i("USER ","admin in if");
            }
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
        String name = editTextName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Required.");
            valid = false;
        } else {
            editTextName.setError(null);
        }
        String lastName = editTextLastName.getText().toString();
        if (TextUtils.isEmpty(lastName)) {
            editTextLastName.setError("Required.");
            valid = false;
        } else {
            editTextLastName.setError(null);
        }
        String identificacion = editTextIdent.getText().toString();
        if (TextUtils.isEmpty(identificacion)) {
            editTextIdent.setError("Required.");
            valid = false;
        } else {
            editTextIdent.setError(null);
        }

        return valid;
    }
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.register:
                registrar();
                startActivity(new Intent(RegistroAdm.this, MenuUser.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}




