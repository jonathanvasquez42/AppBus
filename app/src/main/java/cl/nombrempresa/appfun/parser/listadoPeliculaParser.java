package cl.nombrempresa.appfun.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cl.nombrempresa.appfun.pojo.ListadoPelicula;

/*
 Creado 2016  Jonathan Andrés Vásquez Subiabre
*/

public class listadoPeliculaParser {
    public static List<ListadoPelicula> parse(String content){

        try {

            JSONArray jsonArray = new JSONArray(content);
            List<ListadoPelicula> listadoPeliculaList = new ArrayList<>();

            // recorrer json y almacenar en pojo categoriaPelicula
            for(int i=0; i<jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ListadoPelicula listadoPelicula = new ListadoPelicula();
                listadoPelicula.setListadoId(jsonObject.getInt("id"));
                listadoPelicula.setListadoNombre(jsonObject.getString("nombre"));
                listadoPelicula.setListadoImagen(jsonObject.getString("rutaImagen"));
                listadoPelicula.setListadoRutaVideo(jsonObject.getString("rutaReproduccion"));

                listadoPeliculaList.add(listadoPelicula);

            }

            return listadoPeliculaList;

        } catch (JSONException e) {

            e.printStackTrace();
            // retorno en caso de error
            return null;
        }

    }

}
