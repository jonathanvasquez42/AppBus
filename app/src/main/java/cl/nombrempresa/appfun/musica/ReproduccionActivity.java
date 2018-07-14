package cl.nombrempresa.appfun.musica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import cl.nombrempresa.appfun.R;
import cl.nombrempresa.appfun.interfaces.EnviarRuta;
import cl.nombrempresa.appfun.musica.fragment.ListaReproductor;

public class ReproduccionActivity extends AppCompatActivity implements EnviarRuta{

    String ruta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproduccion);

        // habilitar volver atras
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

    }


    @Override
    public void enviarDatos(String data) {

    }

    @Override
    public void enviarSeleciconArtista(String data) {



    }

    @Override
    public void enviarDesdeActivity() {

        ruta = getIntent().getStringExtra("ruta");

        Log.e("enviando a fragment",""+ruta);
        ListaReproductor izq = (ListaReproductor) getSupportFragmentManager().findFragmentById(R.id.listamusica);
        izq.ObtenerRuta(ruta);


    }

    // volver atras ****
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
