package com.nise.jbookproject.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nise.jbookproject.R;


public class MenuReserva extends AppCompatActivity {
    Button buttonComputador, buttonConsola, buttonLibro, buttonSala, buttonTelevisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_reserva);
        buttonComputador = (Button) findViewById(R.id.computadorButton);
        buttonConsola = (Button) findViewById(R.id.consolaButton);
        buttonLibro = (Button) findViewById(R.id.libroButton);
        buttonSala = (Button) findViewById(R.id.salaButton);
        buttonTelevisor = (Button) findViewById(R.id.televisorButton);

        buttonComputador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuReserva.this, ReservarComputador.class));
            }
        });

        buttonConsola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuReserva.this, ReservarConsola.class));
            }
        });
        buttonLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuReserva.this, ReservarLibro.class));
            }
        });
        buttonSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuReserva.this, ReservarSala.class));
            }
        });
        buttonTelevisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuReserva.this, ReservarTelevisor.class));
            }
        });

    }
}


