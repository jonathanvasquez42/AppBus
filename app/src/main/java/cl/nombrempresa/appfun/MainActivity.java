package cl.nombrempresa.appfun;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cl.nombrempresa.appfun.InfoEmpresa.AcercaDe.AcercadeActivity;
import cl.nombrempresa.appfun.InfoEmpresa.videoSeguridad.VideoSeguridadActivity;
import cl.nombrempresa.appfun.musica.CategoriaMusicaFragment;
import cl.nombrempresa.appfun.musica.ReproduccionActivity;
import cl.nombrempresa.appfun.musica.fragment.ListaReproductor;
import cl.nombrempresa.appfun.pelicula.BlankFragment;
import cl.nombrempresa.appfun.pelicula.ReproduccionPeliculaActivity;
import cl.nombrempresa.appfun.interfaces.EnviarRuta;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , EnviarRuta ,CategoriaMusicaFragment.OnFragmentInteractionListener, BlankFragment.OnFragmentInteractionListener{


    String ruta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Fragment fragment = null;
        Boolean FragmentSeleccionado = false;

        fragment =  new BlankFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
        FragmentSeleccionado = true;


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void enviarDatos(String data) {

        Intent intent = new Intent(MainActivity.this, ReproduccionPeliculaActivity.class);// se llama nuevo activity
        intent.putExtra("rutaVideo",data); // obtenemos ruta y concatenamos con ruta pelicula
        startActivity(intent); // se inicia activity
    }

    @Override
    public void enviarDesdeActivity() {


    }

    @Override
    public void enviarSeleciconArtista(String data) {

        Log.e("enviando data ",""+data);
        Intent intent = new Intent(MainActivity.this, ReproduccionActivity.class);// se llama nuevo activity
        intent.putExtra("ruta",data); // obtenemos ruta y concatenamos con ruta pelicula
        startActivity(intent); // se inicia activity
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         Fragment fragment = null;
         Boolean FragmentSeleccionado = false;

        if (id == R.id.nav_pelicula) {

            fragment =  new BlankFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
            FragmentSeleccionado = true;

        } else if (id == R.id.nav_musica) {

            fragment =  new CategoriaMusicaFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
            FragmentSeleccionado = true;

        } else if (id == R.id.nav_libro) {

        } else if (id == R.id.nav_videoseguridad) {

            // se envia ruta a nuevo activity
            Intent intent = new Intent(MainActivity.this, VideoSeguridadActivity.class);// se llama nuevo activity
            startActivity(intent); // se inicia activity

        } else if (id == R.id.nav_encuesta) {

        } else if (id == R.id.nav_nosotros) {

            // se envia ruta a nuevo activity
            Intent intent = new Intent(MainActivity.this, AcercadeActivity.class);// se llama nuevo activity
            startActivity(intent); // se inicia activity
        }


        if(FragmentSeleccionado){

            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
         }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
