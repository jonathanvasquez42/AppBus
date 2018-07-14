package cl.nombrempresa.appfun.musica.fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import cl.nombrempresa.appfun.R;
import cl.nombrempresa.appfun.adapter.adapterPistasMusica;
import cl.nombrempresa.appfun.clases.ObtenerIp;
import cl.nombrempresa.appfun.clases.RecyclerItemListadoMusica;
import cl.nombrempresa.appfun.http.HttpManager;
import cl.nombrempresa.appfun.interfaces.EnviarRuta;
import cl.nombrempresa.appfun.parser.pistasMusicaParser;
import cl.nombrempresa.appfun.pojo.PistasMusica;


/**
 * Created by jonathan on 14-12-16.
 */

public class ListaReproductor extends Fragment implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener{

    View rootView;
    Integer posicionReproduccion = 0;
    List<PistasMusica> pistasMusicaList;
    adapterPistasMusica adapter;
    RecyclerView recyclerView;
    EnviarRuta env;
    MediaPlayer mediaPlayer;
    SurfaceHolder surfaceHolder;
    ImageButton btnatras, btnpause, btnadelante;
    boolean pause;
    TextView duracion, nombreCancion;

    Drawable first;
    Drawable second;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_listareproduccion, container, false);

        // campo de textop
        duracion = (TextView) rootView.findViewById(R.id.txtduracion);
        nombreCancion = (TextView) rootView.findViewById(R.id.txtnombreCancion);
        //botones
        btnatras = (ImageButton)  rootView.findViewById(R.id.atras);
        btnpause = (ImageButton) rootView.findViewById(R.id.pause);
        btnadelante = (ImageButton)  rootView.findViewById(R.id.adelante);

        first = getResources().getDrawable(
                android.R.drawable.ic_media_play);
         second = getResources().getDrawable(
                android.R.drawable.ic_media_pause);


        btnadelante.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int pos = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                mediaPlayer.seekTo(pos + (duration-pos)/10);
            }
        });

        btnpause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try{
                if (!mediaPlayer.isPlaying()) { //si no suena al play
                    mediaPlayer.start();
                } else if (mediaPlayer.isPlaying()) { //si suena la paro
                    mediaPlayer.pause();

                }

                    if (btnpause.getBackground().equals(first)) {
                        btnpause.setBackgroundDrawable(second);
                    } else {

                        btnpause.setBackgroundDrawable(first);
                    }
                }catch (Exception e){

                    Log.e("error pause play","");
                }

            }
        });

        btnatras.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int pos = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                mediaPlayer.seekTo(pos - (duration-pos)/10);


            }
        });


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerviewPistasMusica);


        // verificar conexion
        if (isOnline()) {

            env.enviarDesdeActivity();

        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Verificar Conexión CikturPlayer", Toast.LENGTH_LONG).show();
        } // fin validar conexion

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);// permite felxibilidad sobre recycler view

        recyclerView.addOnItemTouchListener(new RecyclerItemListadoMusica(getActivity().getApplicationContext(), new OnItemClickListener()));

        return rootView;
    }


    public void ObtenerRuta(String data) {

        //puerta enlace
        ObtenerIp objeto = new ObtenerIp();
        String puertaEnlace = objeto.ObtenerIp(getActivity().getApplicationContext());
        ListaReproductor.MyTask task = new ListaReproductor.MyTask();
        task.execute("http://" + puertaEnlace + "" + data);

    }


    // evento click de RecyclerView
    private class OnItemClickListener extends RecyclerItemListadoMusica.SimpleOnitemClickListener {

        @Override
        public void onItemClick(View childview, int position) {
            PistasMusica objetomusica;
            int largo = pistasMusicaList.size();

            //childview.setBackgroundColor(Color.parseColor("#FF4081"));



            btnpause.setBackgroundDrawable(second);
            if (largo > 0) {
                for (int i = 0; i < largo; i++) {

                    try {

                        if (i == position) {

                            posicionReproduccion = 0;
                            ObtenerIp objeto = new ObtenerIp();
                            String puertaEnlace = objeto.ObtenerIp(getActivity().getApplicationContext());

                            objetomusica = pistasMusicaList.get(i);
                            String nuevaRuta = "http://"+puertaEnlace+objetomusica.getListadoRutaVideo();
                            String nombreCancionSeleccionada = objetomusica.getListadoNombre();
                            nombreCancion.setText(nombreCancionSeleccionada);
                            posicionReproduccion = i;

                            playVideo(nuevaRuta);
                        }else{

                        }

                    } catch (Exception e) {
                        Log.e("Error al enviar listado", "" + e);
                    }

                }

            } else {


                Toast.makeText(getActivity().getApplicationContext(), "Lista sin Datos", Toast.LENGTH_LONG).show();

            }
        }
    }


    public void playVideo(String ruta) {
        try {

            // destruimos media en caso de que exista algun archivo en reproduccion
            destruir();

            pause = false;
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(ruta);
            mediaPlayer.setDisplay(surfaceHolder);
            mediaPlayer.prepare();
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);



            final int HOUR = 60*60*1000;
            final int MINUTE = 60*1000;
            final int SECOND = 1000;

            int durationInMillis = mediaPlayer.getDuration();
            int durationHour = durationInMillis/HOUR;
            int durationMint = (durationInMillis%HOUR)/MINUTE;
            int durationSec = (durationInMillis%MINUTE)/SECOND;
            duracion.setText(String.format("%02d:%02d:%02d", durationHour,durationMint,durationSec));

        } catch (Exception e) {
            Log.e("ERROR: ",e.getMessage());
        }
    }



    public void destruir() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override public void onStart() {
        super.onStart();
        destruir();
    }

    @Override public void onPause() {
        super.onPause();
        destruir();
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

        mediaPlayer.start();

    }


    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        PistasMusica objetomusica;
        int largo = pistasMusicaList.size();

        if (largo > 0) {

            btnpause.setBackgroundDrawable(second);


            for (int i = 0; i < largo; i++) {
                    try {

                        if (i == posicionReproduccion) {

                            ObtenerIp objeto = new ObtenerIp();
                            String puertaEnlace = objeto.ObtenerIp(getActivity().getApplicationContext());

                            objetomusica = pistasMusicaList.get(i+1);

                            String nuevaRuta = "http://" + puertaEnlace + objetomusica.getListadoRutaVideo();
                            String nombreCancionSeleccionada = objetomusica.getListadoNombre();
                            nombreCancion.setText(nombreCancionSeleccionada);

                            posicionReproduccion = i;

                            playVideo(nuevaRuta);
                        }

                    } catch (Exception e) {
                        Log.e("Error al enviar listado", "" + e);
                        nombreCancion.setText(null);
                        duracion.setText(null);
                    }

                }

                posicionReproduccion = posicionReproduccion + 1;

        }
    }





    public void onAttach(Activity activity) {

        super.onAttach(activity);
        try {
            env = (EnviarRuta) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("necesitas implementar");
        }

    }


    // verificar conexion del dispositivo
    public boolean isOnline() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

            return true;
        } else {

            return false;
        }
    }



    //
    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            String content = HttpManager.GetData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String resul) {
            super.onPostExecute(resul);


            if (resul == null) {
                Toast.makeText(getActivity().getApplicationContext(), "Verifique Conexión a Red CikturPlayer", Toast.LENGTH_LONG).show();
                return;
            }

            pistasMusicaList = pistasMusicaParser.parse(resul);
            cargarDatos();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            cargarDatos();

        }

    }

    private void cargarDatos() {

        adapter = new adapterPistasMusica(getActivity().getApplicationContext(), pistasMusicaList);
        recyclerView.setAdapter(adapter);

    }


}
