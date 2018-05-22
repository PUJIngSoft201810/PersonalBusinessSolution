package com.nise.jbookproject.Actividades;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.nise.jbookproject.R;

public class HistorialReservasT extends AppCompatActivity {

    private TextView mTextMessage;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_reservas_t);

        toolbar = getSupportActionBar();

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle(R.string.title_book);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_book:
                    //mTextMessage.setText(R.string.title_book);
                    toolbar.setTitle(R.string.title_book);
                    return true;
                case R.id.navigation_computer:
                    //mTextMessage.setText(R.string.title_computers);
                    toolbar.setTitle(R.string.title_computers);
                    return true;
                case R.id.navigation_console:
                    //mTextMessage.setText(R.string.title_console);
                    toolbar.setTitle(R.string.title_console);
                    return true;
                case R.id.navigation_room:
                    //mTextMessage.setText(R.string.title_room);
                    toolbar.setTitle(R.string.title_room);
                    return true;
                case R.id.navigation_television:
                    //mTextMessage.setText(R.string.title_television);
                    toolbar.setTitle(R.string.title_television);
                    return true;
            }
            return false;
        }
    };
}
