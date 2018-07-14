package cl.nombrempresa.appfun.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cl.nombrempresa.appfun.R;
import cl.nombrempresa.appfun.clases.ObtenerIp;
import cl.nombrempresa.appfun.pojo.ListadoMusica;


/*
 Creado 2016  Jonathan Andrés Vásquez Subiabre
*/

public class adapterListadoMusica extends RecyclerView.Adapter<adapterListadoMusica.ViewHolder>{

    List<ListadoMusica> listadoMusicaList = new ArrayList<>();
    Context context;
    static int lastPosition = -1;


    // constructor
    public adapterListadoMusica(Context context, List<ListadoMusica> listadoMusicaList) {

        this.context = context;
        this.listadoMusicaList = listadoMusicaList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent, false);
        ViewHolder viewHolder = new ViewHolder(itemview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // se obtiene puerta de enlace
        ObtenerIp objeto = new ObtenerIp();
        String puertaEnlace = objeto.ObtenerIp(context);

        //holder.title.setText(listadoMusicaList.get(position).getListadoNombre());
        Picasso.with(context).load("http://"+puertaEnlace+listadoMusicaList.get(position).getListadoImagen()).into(holder.imagenView);
        // enviar animacion
        setAnimation(holder.cardView, position);

    }

    @Override
    public int getItemCount() {
        return listadoMusicaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //TextView title;
        ImageView imagenView;
        CardView cardView;

        public ViewHolder(View item){
            super(item);
            //title = (TextView) item.findViewById(R.id.titulo);
            imagenView = (ImageView) item.findViewById(R.id.imagenview);
            cardView = (CardView) item.findViewById(R.id.card_view);
        }
    }

    // animacion
    private void setAnimation(View viewToAnimate, int position){

        if(position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.anim_recyclerview);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }

    }

}
