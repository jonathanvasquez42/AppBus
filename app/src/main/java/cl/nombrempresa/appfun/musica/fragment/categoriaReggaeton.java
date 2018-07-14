package cl.nombrempresa.appfun.musica.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import cl.nombrempresa.appfun.R;
import cl.nombrempresa.appfun.adapter.adapterListadoMusica;
import cl.nombrempresa.appfun.clases.ObtenerIp;
import cl.nombrempresa.appfun.clases.RecyclerItemListadoMusica;
import cl.nombrempresa.appfun.http.HttpManager;
import cl.nombrempresa.appfun.parser.listadoMusicaParser;
import cl.nombrempresa.appfun.interfaces.EnviarRuta;
import cl.nombrempresa.appfun.pojo.ListadoMusica;

/**
 * Created by jonathan on 14-12-16.
 */

public class categoriaReggaeton extends Fragment {
    List<ListadoMusica> listadoMusicaLists;
    adapterListadoMusica adapter;
    RecyclerView recyclerView;
    EnviarRuta env;

    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState) {
        View rootView;

        rootView = inflater.inflate(R.layout.fragment_musicareggaeton, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerviewListadoMusicaReggaeton);


        // verificar conexion
        if(isOnline()){


            ObtenerRuta("/data/dataMusica/dataArtista/dataReggaeton.json");


        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Verificar Conexión CikturPlayer",Toast.LENGTH_LONG).show();
        } // fin validar conexion


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);// permite felxibilidad sobre recycler view

        recyclerView.addOnItemTouchListener(new RecyclerItemListadoMusica(getActivity().getApplicationContext(), new OnItemClickListener()));

        return rootView;
    }


    public void ObtenerRuta(String data) {


        //puerta enlace
        ObtenerIp objeto = new ObtenerIp();
        String puertaEnlace = objeto.ObtenerIp(getActivity().getApplicationContext());

        categoriaReggaeton.MyTask task = new categoriaReggaeton.MyTask();
        task.execute("http://"+puertaEnlace+""+data);

    }


    // evento click de RecyclerView
    private class OnItemClickListener extends RecyclerItemListadoMusica.SimpleOnitemClickListener{

        @Override
        public void onItemClick(View childview, int position) {
            ListadoMusica objetomusica;
            int largo = listadoMusicaLists.size();

            if(largo > 0){
                for(int i = 0 ; i < largo ; i++)
                {
                    try {


                        if(i == position) {

                            objetomusica = listadoMusicaLists.get(i);
                            String nuevaRuta = objetomusica.getListadoRutaMusica();
                            env.enviarSeleciconArtista(nuevaRuta);
                        }

                    }catch (Exception e){
                        Log.e("Error al enviar listado",""+e);
                    }

                }

            }else{


                Toast.makeText(getActivity().getApplicationContext(),"Lista sin Datos",Toast.LENGTH_LONG).show();

            }
        }
    }


    public void onAttach(Activity activity){

        super.onAttach(activity);
        try {
            env = (EnviarRuta) activity;
        }catch (ClassCastException e){
            throw new ClassCastException("necesitas implementar");
        }

    }


    // verificar conexion del dispositivo
    public boolean isOnline(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){

            return true;
        }else{

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


            if(resul == null){
                Toast.makeText(getActivity().getApplicationContext(),"Verifique Conexión a Red CikturPlayer",Toast.LENGTH_LONG).show();
                return;
            }

            listadoMusicaLists = listadoMusicaParser.parse(resul);
            cargarDatos();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            cargarDatos();

        }

    }

    private void cargarDatos() {

        adapter = new adapterListadoMusica(getActivity().getApplicationContext(),listadoMusicaLists);
        recyclerView.setAdapter(adapter);

    }


}