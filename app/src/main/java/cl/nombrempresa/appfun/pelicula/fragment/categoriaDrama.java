package cl.nombrempresa.appfun.pelicula.fragment;

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
import cl.nombrempresa.appfun.adapter.adapterListadoPelicula;
import cl.nombrempresa.appfun.clases.ObtenerIp;
import cl.nombrempresa.appfun.clases.RecyclerItemListadoPelicula;
import cl.nombrempresa.appfun.http.HttpManager;
import cl.nombrempresa.appfun.parser.listadoPeliculaParser;
import cl.nombrempresa.appfun.interfaces.EnviarRuta;
import cl.nombrempresa.appfun.pojo.ListadoPelicula;


/**
 * Created by jonathan on 13-12-16.
 */

public class categoriaDrama extends Fragment {

    List<ListadoPelicula> listadoPeliculaLists;
    adapterListadoPelicula adapter;
    RecyclerView recyclerView;
    EnviarRuta env;

    View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_categoriadrama, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerviewListadoDrama);


        // verificar conexion
        if(isOnline()){

            //env.enviarDesdeActivity();
            ObtenerRuta("/data/dataPelicula/dataDrama.json");


        }else{
            Toast.makeText(getActivity().getApplicationContext(),"Verificar Conexión CikturPlayer",Toast.LENGTH_LONG).show();
        } // fin validar conexion


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);// permite felxibilidad sobre recycler view

        recyclerView.addOnItemTouchListener(new RecyclerItemListadoPelicula(getActivity().getApplicationContext(), new OnItemClickListener()));

        return rootView;
    }


    public void ObtenerRuta(String data) {


        //puerta enlace
        ObtenerIp objeto = new ObtenerIp();
        String puertaEnlace = objeto.ObtenerIp(getActivity().getApplicationContext());

        MyTask task = new MyTask();
        task.execute("http://"+puertaEnlace+""+data);

    }


    // evento click de RecyclerView
    private class OnItemClickListener extends RecyclerItemListadoPelicula.SimpleOnitemClickListener{

        @Override
        public void onItemClick(View childview, int position) {
            ListadoPelicula objetopelicula;
            int largo = listadoPeliculaLists.size();

            if(largo > 0){
                for(int i = 0 ; i < largo ; i++)
                {
                    try {


                        if(i == position) {

                            objetopelicula = listadoPeliculaLists.get(i);
                            String nuevaRuta = objetopelicula.getListadoRutaVideo();

                            env.enviarDatos(nuevaRuta);
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

            listadoPeliculaLists = listadoPeliculaParser.parse(resul);
            cargarDatos();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            cargarDatos();

        }

    }

    private void cargarDatos() {

        adapter = new adapterListadoPelicula(getActivity().getApplicationContext(),listadoPeliculaLists);
        recyclerView.setAdapter(adapter);

    }


}

