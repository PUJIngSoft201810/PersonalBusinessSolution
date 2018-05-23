package com.nise.jbookproject.Actividades;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nise.jbookproject.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonLogIn, buttonRegister;
    EditText editTextEmail, editTextPass;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogIn = (Button) findViewById(R.id.signin);
        buttonRegister = (Button) findViewById(R.id.register);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPass = (EditText) findViewById(R.id.password);
        buttonRegister.setOnClickListener(this);
        buttonLogIn.setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    // User is signed in
                    Log.d("SESION", "onAuthStateChanged:signed_in:" + user.getEmail());
                    startActivity(new Intent(MainActivity.this, MenuUser.class));
                    //Log.i("SESION","sesion iniciada con email: "+user.getEmail());
                }
                else
                {
                    Log.d("SESION","sesion cerrada");
                }
            }
        };
    }

    private void registrar(){
        Log.d("CREATION","sE HA PRESIONADO REGISTRAR");
        startActivity(new Intent(MainActivity.this, Registro.class));
    }
    private void iniciarSesion(){
        if(validateForm()){
            String email = editTextEmail.getText().toString();
            String password = editTextPass.getText().toString();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("SESION","signInWithEmail:onComplete:" + task.isSuccessful());
                    if (!task.isSuccessful()) {
                        Log.w("SESION","signInWithEmail:failed", task.getException());
                        Toast.makeText(MainActivity.this, "Usuario o contraseña invalido", Toast.LENGTH_SHORT).show();
                        editTextEmail.setText("");
                        editTextPass.setText("");
                    }
                }
            });
        }
    }
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.signin:
                iniciarSesion();
                break;
            case R.id.register:
                registrar();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
        return valid;
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
        }
        return super.onOptionsItemSelected(item);
    }
}



