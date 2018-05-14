package com.nise.jbookproject.Actividades;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.nise.jbookproject.Fragmentos.Dia1;
import com.nise.jbookproject.Fragmentos.Dia2;
import com.nise.jbookproject.Fragmentos.Dia3;
import com.nise.jbookproject.Fragmentos.Dia4;
import com.nise.jbookproject.Fragmentos.Dia5;
import com.nise.jbookproject.Fragmentos.Dia6;
import com.nise.jbookproject.Modulos.Sala;
import com.nise.jbookproject.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReservarSalaDia extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_sala_dia);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), getIntent());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        //Enlaza tabLayout con mViewPager
        tabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reservar_sala_dia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
        /*
    /**
     * A placeholder fragment containing a simple view.

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         *
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_reservar_sala_dia, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    } */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        Intent intent;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.intent = null;
        }

        public SectionsPagerAdapter(FragmentManager fm, Intent intent) {
            super(fm);
            this.intent = intent;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            Date today = Calendar.getInstance().getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            Sala salaActual = (Sala) intent.getSerializableExtra("objeto");
            switch (position) {
                case 0:
                    return Dia1.newInstance( salaActual, calendar.getTime());
                case 1:
                    calendar.add(Calendar.DAY_OF_YEAR,1);
                    return Dia2.newInstance(salaActual, calendar.getTime());
                case 2:
                    calendar.add(Calendar.DAY_OF_YEAR,2);
                    return Dia3.newInstance(salaActual, calendar.getTime());
                case 3:
                    calendar.add(Calendar.DAY_OF_YEAR,3);
                    return Dia4.newInstance(salaActual, calendar.getTime());
                case 4:
                    calendar.add(Calendar.DAY_OF_YEAR,4);
                    return Dia5.newInstance(salaActual, calendar.getTime());
                case 5:
                    calendar.add(Calendar.DAY_OF_YEAR,5);
                    return Dia6.newInstance(salaActual, calendar.getTime());
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 6 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            Date today = Calendar.getInstance().getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);

            String dia;
            switch (position) {
                case 0:
                    dia = new SimpleDateFormat("EE", new Locale("es","ES")).format(calendar.getTime());
                    return dia;
                case 1:
                    calendar.add(Calendar.DAY_OF_YEAR,1);
                    dia = new SimpleDateFormat("EE", new Locale("es","ES")).format(calendar.getTime());
                    return dia;
                case 2:
                    calendar.add(Calendar.DAY_OF_YEAR,2);
                    dia = new SimpleDateFormat("EE", new Locale("es","ES")).format(calendar.getTime());
                    return dia;
                case 3:
                    calendar.add(Calendar.DAY_OF_YEAR,3);
                    dia = new SimpleDateFormat("EE", new Locale("es","ES")).format(calendar.getTime());
                    return dia;
                case 4:
                    calendar.add(Calendar.DAY_OF_YEAR,4);
                    dia = new SimpleDateFormat("EE", new Locale("es","ES")).format(calendar.getTime());
                    return dia;
                case 5:
                    calendar.add(Calendar.DAY_OF_YEAR,5);
                    dia = new SimpleDateFormat("EE", new Locale("es","ES")).format(calendar.getTime());
                    return dia;
            }
            return null;
        }
    }
}
