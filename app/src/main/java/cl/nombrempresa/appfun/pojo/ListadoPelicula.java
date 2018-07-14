package cl.nombrempresa.appfun.pojo;

/*
 Creado 2016  Jonathan Andrés Vásquez Subiabre
 */

public class ListadoPelicula {

    private int listadoId;
    private String listadoNombre;
    private String listadoImagen;
    private String listadoRutaVideo;


    public void setListadoId(int listadoId) {
        this.listadoId = listadoId;
    }

    public int getListadoId() {
        return listadoId;
    }

    public void setListadoNombre(String listadoNombre) {
        this.listadoNombre = listadoNombre;
    }

    public String getListadoNombre() {
        return listadoNombre;
    }

    public void setListadoImagen(String listadoImagen) {
        this.listadoImagen = listadoImagen;
    }

    public String getListadoImagen() {
        return listadoImagen;
    }

    public void setListadoRutaVideo(String listadoRutaVideo) {
        this.listadoRutaVideo = listadoRutaVideo;
    }

    public String getListadoRutaVideo() {
        return listadoRutaVideo;
    }

}
