package com.nise.jbookproject.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.nise.jbookproject.Fragmentos.ComputadorFr;
import com.nise.jbookproject.Fragmentos.ConsolaFr;
import com.nise.jbookproject.Fragmentos.LibroFr;
import com.nise.jbookproject.Fragmentos.RegComputadorFr;
import com.nise.jbookproject.Fragmentos.RegConsolaFr;
import com.nise.jbookproject.Fragmentos.RegLibroFr;
import com.nise.jbookproject.Fragmentos.RegSalaFr;
import com.nise.jbookproject.Fragmentos.RegTelevisionFr;
import com.nise.jbookproject.Fragmentos.SalaFr;
import com.nise.jbookproject.Fragmentos.TelevisionFr;
import com.nise.jbookproject.Modulos.BottomNavigationBehavior;
import com.nise.jbookproject.R;

public class ConsultarReservasActivasNav extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_reservas_activas_nav);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());


        loadFragment(new RegLibroFr());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_book:
                    loadFragment(new RegLibroFr());
                    return true;
                case R.id.navigation_computer:
                    loadFragment(new RegComputadorFr());
                    return true;
                case R.id.navigation_console:
                    loadFragment(new RegConsolaFr());
                    return true;
                case R.id.navigation_room:
                    loadFragment(new RegSalaFr());
                    return true;
                case R.id.navigation_television:
                    loadFragment(new RegTelevisionFr());
                    return true;
                /*case R.id.navigation_back:
                    startActivity(new Intent(getApplicationContext(), MenuUser.class));
                    return true;*/
            }
            return false;
        }
    };



    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
