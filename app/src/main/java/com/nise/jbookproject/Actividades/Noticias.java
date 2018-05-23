package com.nise.jbookproject.Actividades;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nise.jbookproject.R;

public class Noticias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Más información: \n" +
                        "Sección de Servicios Fundamentales\n" +
                        "Biblioteca General Alfonso Borrero Cabal, S.J. \n" +
                        "Tel. (1) 3208320 Ext. 2135 ¿ 215", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
