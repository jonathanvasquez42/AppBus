package cl.nombrempresa.appfun.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cl.nombrempresa.appfun.pojo.PistasMusica;


/**
 * Created by jonathan on 03-12-16.
 */

public class pistasMusicaParser {
    public static List<PistasMusica> parse(String content){

        try {

            JSONArray jsonArray = new JSONArray(content);
            List<PistasMusica> pistasMusicaList = new ArrayList<>();

            // recorrer json y almacenar en pojo
            for(int i=0; i<jsonArray.length(); i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PistasMusica pistasMusica = new PistasMusica();
                pistasMusica.setListadoId(jsonObject.getInt("id"));
                pistasMusica.setListadoNombre(jsonObject.getString("nombre"));
                pistasMusica.setListadoImagen(jsonObject.getString("rutaImagen"));
                pistasMusica.setListadoRutaVideo(jsonObject.getString("rutaCanciones"));

                pistasMusicaList.add(pistasMusica);

            }

            return pistasMusicaList;

        } catch (JSONException e) {

            e.printStackTrace();
            // retorno en caso de error
            return null;
        }

    }

}