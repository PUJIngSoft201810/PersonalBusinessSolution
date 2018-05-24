package com.nise.jbookproject.Actividades;


import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nise.jbookproject.Modulos.AdapterLibro;
import com.nise.jbookproject.Modulos.FirebaseReferences;
import com.nise.jbookproject.Modulos.Libro;
import com.nise.jbookproject.Modulos.RecyclerTouchListener;
import com.nise.jbookproject.Modulos.Reserva;
import com.nise.jbookproject.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservarLibro extends AppCompatActivity implements AdapterLibro.LibrosAdapterListener {
    Button buttonCrear;
    private static final String TAG = ReservarLibro.class.getSimpleName();
    RecyclerView rv;
    List<Libro> libros;
    AdapterLibro adapterLibro;
    SearchView searchView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference librosResRef;
    DatabaseReference librosRecRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar_libro);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        //buttonReserva = (Button) findViewById(R.id.reservarButton);
        buttonCrear = (Button) findViewById(R.id.crearLib);
        rv = (RecyclerView) findViewById(R.id.recyclerLibro);
        libros = new ArrayList<>();

        rv.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        adapterLibro = new AdapterLibro(this, libros, this);

        Log.i("ADAPTER", "Adapter creado");

        // white background notification bar
        whiteNotificationBar(rv);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapterLibro);

        final DatabaseReference proyectoRef = database.getReference(FirebaseReferences.PROYECTO_REFERENCE);
        final DatabaseReference reservaRef = proyectoRef.child(FirebaseReferences.RESERVA_REFERENCE);
        final DatabaseReference recursosRef = proyectoRef.child(FirebaseReferences.RECURSOS_REFERENCE);
        librosRecRef = recursosRef.child(FirebaseReferences.LIBROS_REFERENCE);
        librosResRef = reservaRef.child(FirebaseReferences.LIBROS_REFERENCE);

        librosRecRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                libros.removeAll(libros);
                Log.i("ADAPTER", "DataSnapshot" + dataSnapshot.toString());
                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()
                        ) {
                    Libro libro = snapshot.getValue(Libro.class);
                    Log.i("ADAPTER", libro.toString());
                    if (!libro.isReservado()) {
                        libros.add(libro);
                    }
                }
                adapterLibro.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        buttonCrear.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                DatabaseReference miLibro = librosRecRef.push();
                Libro libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 1", "Antoine de Saint Exupery", "El principito","9781231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 1", "Gabriel Garcia Marquez", "Cien Años de soledad","9784231244563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 1", "Ana Frank", "El diario de ana frank","9781231274563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 1", " Diversos personajes", "Biblia","9781271734563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 1", "William Shakespeare", "Romeo y Julieta","9781931234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 1", "Patrick Süskind", "El perfume","9784231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 2", "Miguel de Cervantes Saavedr", "Don quijote de la mancha","9781631234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 2", "Oscar Wilde", "El retrato de dorian gray","9781231284563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 2", "Bram Stoker", "Dracula","9711237234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 2", "Victor Hugo", "Los miserables","1781231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 2", "Gabriel Garcia Marquez", "El amor en los tiempos del colera","2781231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 2", " Franz Kafka", "Metamorfosis","3781231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 2", "Dante Alighieri", "La divina comedia","4781231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 2", "Homero", "La odisea","5781231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 2", "Julio Verne", "La vuelta al mundo en 80 dias","6781231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 2", "Alexandre Dumas", "El conde de montecristo","7781231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 2", "Gaston Leroux", "El fantasma de la opera","8781231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 3", "William Shakespeare", "Hamlet","9981231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 3", "Homero", "La iliada","9101231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 3", "Fiodor Dostoievski", "Crimen y castigo","9181231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 3", "Jose saramago", "Ensayo sobre la seguera","9281231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 3", "William Golding", "El señor de las moscas","9381231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 3", "Leon Tolsoi", "Ana Karenina","9781231234564");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 3", "Charles Dickens", "Oliver Twist","9751231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 3", "Herman Melville", "Moby Dick","9681231234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 4", "William Shakespeare", "Macbeth","9781278234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 4", "Johann Wolfgang", "Fausto","9789231034563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 4", "William Shakespeare", "Otelo","97810310234563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 4", " Fiodor Dostoievski", "Los hermanos karamazov","9780231230563");
                miLibro.setValue(libro);
                miLibro = librosRecRef.push();
                libro = new Libro(miLibro.getKey().toString(),"libro",false,"piso 4", "Victor Hugo", "Nuestra señora de paris","9781437234563");
                miLibro.setValue(libro);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reservar_libro, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapterLibro.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapterLibro.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    public void onLibroSelected(Libro libro) {
        Toast.makeText(getApplicationContext(), "Libro reservado: " + libro.getTitulo() + ", " + libro.getAutor(), Toast.LENGTH_LONG).show();
        libro.setReservado(true);
        librosRecRef.child(libro.getId()).setValue(libro);

        Date fecha_inicio, fecha_fin;
        fecha_inicio = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha_inicio);
        cal.add(Calendar.DAY_OF_YEAR, 15);
        fecha_fin = cal.getTime();

        mAuth = FirebaseAuth.getInstance();
        String idUser = mAuth.getUid();
        Log.i("USER ","Usuario :"+idUser);

        Reserva reserva = new Reserva("Prueba",idUser,libro.getId(),libro.getDescripcion(),true,fecha_inicio,fecha_fin);
        DatabaseReference miReserva = librosResRef.push();
        reserva.setIdReserva(miReserva.getKey().toString());
        miReserva.setValue(reserva);
        adapterLibro.notifyDataSetChanged();
    }
}
