package com.nise.jbookproject.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nise.jbookproject.Modulos.Computador;
import com.nise.jbookproject.Modulos.Consola;
import com.nise.jbookproject.Modulos.FirebaseReferences;
import com.nise.jbookproject.Modulos.Libro;
import com.nise.jbookproject.Modulos.Sala;
import com.nise.jbookproject.Modulos.Televisor;
import com.nise.jbookproject.Modulos.TipoComputador;
import com.nise.jbookproject.R;

import java.util.concurrent.locks.LockSupport;

public class CreacionRecursos extends AppCompatActivity implements View.OnClickListener {
    Button buttonCrear;
    EditText Descripcion, NomSala, Autor, Titulo, Isbn;
    Spinner spinnType, SpinnerPC;
    RelativeLayout NumeroControles, CapacidadSala,layUbicacion;
    NumberPicker NumControles, NumCapacidad,piso;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    String[] Category = {"Select", "Computador", "Consola", "Sala", "Libro", "Televisor"};
    final String[] values= {"Piso 5","Piso 4","Piso 3","Piso 2","Piso 1","Piso 0","Sótano 1","Sótano 2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_recursos);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        spinnType = (Spinner) findViewById(R.id.SpinnerType);

        Descripcion = (EditText) findViewById(R.id.Descripcion);
        layUbicacion = (RelativeLayout) findViewById(R.id.Ubicacion);
        piso = (NumberPicker) findViewById(R.id.piso);
        piso.setMaxValue(values.length-1);
        piso.setDisplayedValues(values);
        piso.setWrapSelectorWheel(true);
        SpinnerPC = (Spinner) findViewById(R.id.SpinnerPC);

        NumeroControles = (RelativeLayout) findViewById(R.id.NumeroControles);
        NumControles = (NumberPicker) findViewById(R.id.NumControles);


        NomSala = (EditText) findViewById(R.id.NomSala);
        CapacidadSala = (RelativeLayout) findViewById(R.id.CapacidadSala);
        NumCapacidad = (NumberPicker) findViewById(R.id.NumCapacidad);

        Autor = (EditText) findViewById(R.id.Autor);
        Titulo = (EditText) findViewById(R.id.Titulo);
        Isbn = (EditText) findViewById(R.id.Isbn);

        NumControles.setMinValue(1);
        NumControles.setMaxValue(4);
        NumControles.setWrapSelectorWheel(true);
        NumCapacidad.setMinValue(3);
        NumCapacidad.setMaxValue(10);
        NumCapacidad.setWrapSelectorWheel(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreacionRecursos.this, android.R.layout.simple_spinner_item, Category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnType.setAdapter(adapter);
        spinnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                String Text = parent.getSelectedItem().toString();
                if (Text.equals("Select")) {
                    SpinnerPC.setVisibility(View.GONE);
                    NumeroControles.setVisibility(View.GONE);
                    NomSala.setVisibility(View.GONE);
                    NumCapacidad.setVisibility(View.GONE);
                    Autor.setVisibility(View.GONE);
                    Titulo.setVisibility(View.GONE);
                    Isbn.setVisibility(View.GONE);
                }
                if (Text.equals("Computador")) {
                    SpinnerPC.setVisibility(View.VISIBLE);
                    NumeroControles.setVisibility(View.GONE);
                    NomSala.setVisibility(View.GONE);
                    NumCapacidad.setVisibility(View.GONE);
                    Autor.setVisibility(View.GONE);
                    Titulo.setVisibility(View.GONE);
                    Isbn.setVisibility(View.GONE);
                } else if (Text.equals("Consola")) {
                    SpinnerPC.setVisibility(View.GONE);
                    NumeroControles.setVisibility(View.VISIBLE);
                    NomSala.setVisibility(View.GONE);
                    NumCapacidad.setVisibility(View.GONE);
                    Autor.setVisibility(View.GONE);
                    Titulo.setVisibility(View.GONE);
                    Isbn.setVisibility(View.GONE);
                } else if (Text.equals("Sala")) {
                    SpinnerPC.setVisibility(View.GONE);
                    NumeroControles.setVisibility(View.GONE);
                    NomSala.setVisibility(View.VISIBLE);
                    NumCapacidad.setVisibility(View.VISIBLE);
                    Autor.setVisibility(View.GONE);
                    Titulo.setVisibility(View.GONE);
                    Isbn.setVisibility(View.GONE);
                } else if (Text.equals("Libro")) {
                    SpinnerPC.setVisibility(View.GONE);
                    NumeroControles.setVisibility(View.GONE);
                    NomSala.setVisibility(View.GONE);
                    NumCapacidad.setVisibility(View.GONE);
                    Autor.setVisibility(View.VISIBLE);
                    Titulo.setVisibility(View.VISIBLE);
                    Isbn.setVisibility(View.VISIBLE);
                } else if (Text.equals("Televisor")) {
                    SpinnerPC.setVisibility(View.GONE);
                    NumeroControles.setVisibility(View.GONE);
                    NomSala.setVisibility(View.GONE);
                    NumCapacidad.setVisibility(View.GONE);
                    Autor.setVisibility(View.GONE);
                    Titulo.setVisibility(View.GONE);
                    Isbn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonCrear = (Button) findViewById(R.id.Crear);
        buttonCrear.setOnClickListener(this);
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.Crear:
                if(CrearRecurso())
                    startActivity(new Intent(CreacionRecursos.this, MenuUser.class));
                break;
        }
    }

    private boolean CrearRecurso() {
        if (validateForm()) {
            String sTipoRecurso = String.valueOf(spinnType.getSelectedItem());
            String sDescripcion = Descripcion.getText().toString();
            String Location = values[piso.getValue()];
            String TipoPC = String.valueOf(SpinnerPC.getSelectedItem());
            int NumControl = NumControles.getValue();
            String sNomSala = NomSala.getText().toString();
            int CapacidadSala = NumCapacidad.getValue();
            String sAutor = Autor.getText().toString();
            String sTitulo = Titulo.getText().toString();
            String sIsbn = Isbn.getText().toString();
            final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
            final DatabaseReference recursosRef = proyectoRef.child(FirebaseReferences.RECURSOS_REFERENCE);
            final DatabaseReference computadoresRecRef = recursosRef.child(FirebaseReferences.COMPUTADORES_REFERENCE);
            final DatabaseReference consolaRecRef = recursosRef.child(FirebaseReferences.CONSOLAS_REFERENCE);
            final DatabaseReference salaRecRef = recursosRef.child(FirebaseReferences.SALAS_REFERENCE);
            final DatabaseReference LibroRecRef = recursosRef.child(FirebaseReferences.LIBROS_REFERENCE);
            final DatabaseReference TeleRecRef = recursosRef.child(FirebaseReferences.TELEVISORES_REFERENCE);


            if (sTipoRecurso.equals("Computador")) {
                TipoComputador tiPC = TipoComputador.MESA;
                if (TipoPC.equals("Portatil"))
                    tiPC = TipoComputador.PORTATIL;
                Computador computador = new Computador("Prueba", sDescripcion, false, Location, tiPC);
                DatabaseReference miComputador = computadoresRecRef.push();
                computador.setId(miComputador.getKey().toString());
                miComputador.setValue(computador);
            } else if (sTipoRecurso.equals("Consola")) {
                Consola consola = new Consola("Prueba", sDescripcion, false, Location, NumControl);
                DatabaseReference miConsola = consolaRecRef.push();
                consola.setId(miConsola.getKey().toString());
                miConsola.setValue(consola);
            } else if (sTipoRecurso.equals("Sala")) {
                Sala sala = new Sala("Prueba", sNomSala, sDescripcion, false, Location, CapacidadSala);
                DatabaseReference misala = salaRecRef.push();
                sala.setId(misala.getKey().toString());
                misala.setValue(sala);
            } else if (sTipoRecurso.equals("Libro")) {
                Libro libro = new Libro("Prueba", sDescripcion, false, Location, sAutor, sTitulo, sIsbn);
                DatabaseReference miLibro = LibroRecRef.push();
                libro.setId(miLibro.getKey().toString());
                miLibro.setValue(libro);
            } else if (sTipoRecurso.equals("Televisor")) {
                Televisor televisor = new Televisor("Prueba", sDescripcion, false, Location);
                DatabaseReference miTelevisor = TeleRecRef.push();
                televisor.setId(miTelevisor.getKey().toString());
                miTelevisor.setValue(televisor);

            }
            Toast.makeText(CreacionRecursos.this, "Se ha creado un nuevo recurso "+sTipoRecurso, Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private boolean validateForm() {
        boolean valid = true;
        String TipoRecurso = String.valueOf(spinnType.getSelectedItem());
        if(TipoRecurso.equals("Select")){
            Toast.makeText(CreacionRecursos.this, "Selecione un tipo de recurso", Toast.LENGTH_SHORT).show();
            return false;
        }

        String sDescripcion = Descripcion.getText().toString();
        if (TextUtils.isEmpty(sDescripcion)) {
            Descripcion.setError("Required.");
            valid = false;
        } else {
            Descripcion.setError(null);
        }
        if(TipoRecurso.equals("Sala")){
            String sNomSala = NomSala.getText().toString();
            if (TextUtils.isEmpty(sNomSala)) {
                NomSala.setError("Required.");
                valid = false;
            } else {
                NomSala.setError(null);
            }

        }if(TipoRecurso.equals("Libro")){
            String sAutor = Autor.getText().toString();
            String sTitulo = Titulo.getText().toString();
            String sIsbn = Isbn.getText().toString();
            if (TextUtils.isEmpty(sAutor)) {
                Autor.setError("Required.");
                valid = false;
            } else {
                Autor.setError(null);
            }if (TextUtils.isEmpty(sTitulo)) {
                Titulo.setError("Required.");
                valid = false;
            } else {
                Titulo.setError(null);
            }if (TextUtils.isEmpty(sIsbn)) {
                Isbn.setError("Required.");
                valid = false;
            } else {
                Isbn.setError(null);
            }
        }
        return valid;
    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}