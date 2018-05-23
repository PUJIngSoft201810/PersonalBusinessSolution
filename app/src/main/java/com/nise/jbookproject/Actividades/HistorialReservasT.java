package com.nise.jbookproject.Actividades;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.nise.jbookproject.Fragmentos.BookFragment;
import com.nise.jbookproject.Fragmentos.ComputerFragment;
import com.nise.jbookproject.Fragmentos.ConsoleFragment;
import com.nise.jbookproject.Fragmentos.RoomFragment;
import com.nise.jbookproject.Fragmentos.TelevisionFragment;
import com.nise.jbookproject.R;

public class HistorialReservasT extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_reservas_t);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new BookFragment());
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment !=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()){
                case R.id.navigation_book:
                    fragment = new BookFragment();
                    break;
                case R.id.navigation_computer:
                    fragment = new ComputerFragment();
                    break;
                case R.id.navigation_console:
                    fragment = new ConsoleFragment();
                    break;
                case R.id.navigation_room:
                    fragment = new RoomFragment();
                    break;
                case R.id.navigation_television:
                    fragment = new TelevisionFragment();
                    break;

            }

            return loadFragment(fragment);
        }

    }
