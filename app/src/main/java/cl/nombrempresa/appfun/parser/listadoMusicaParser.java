package cl.nombrempresa.appfun.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cl.nombrempresa.appfun.pojo.ListadoMusica;
/*
 Creado 2016  Jonathan Andrés Vásquez Subiabre
 */

public class listadoMusicaParser {
    public static List<ListadoMusica> parse(String content){

        try {

            JSONArray jsonArray = new JSONArray(content);
            List<ListadoMusica> listadoMusicaList = new ArrayList<>();

            // recorrer json y almacenar en pojo
            for(int i=0; i<jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ListadoMusica listadoMusica = new ListadoMusica();
                listadoMusica.setListadoId(jsonObject.getInt("id"));
                listadoMusica.setListadoNombre(jsonObject.getString("nombre"));
                listadoMusica.setListadoImagen(jsonObject.getString("rutaImagen"));
                listadoMusica.setListadoRutaMusica(jsonObject.getString("rutaCanciones"));

                listadoMusicaList.add(listadoMusica);

            }

            return listadoMusicaList;

        } catch (JSONException e) {

            e.printStackTrace();
            // retorno en caso de error
            return null;
        }

    }

}
