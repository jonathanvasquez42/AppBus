package cl.nombrempresa.appfun.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cl.nombrempresa.appfun.R;
import cl.nombrempresa.appfun.clases.ObtenerIp;
import cl.nombrempresa.appfun.pojo.PistasMusica;


/**
 * Created by jonathan on 03-12-16.
 */

public class adapterPistasMusica extends RecyclerView.Adapter<adapterPistasMusica.ViewHolder>{

    List<PistasMusica> pistasMusicaList = new ArrayList<>();
    Context context;
    static int lastPosition = -1;


    // constructor
    public adapterPistasMusica(Context context, List<PistasMusica> pistasMusicaList) {

        this.context = context;
        this.pistasMusicaList = pistasMusicaList;

    }

    @Override
    public adapterPistasMusica.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_musica,parent, false);
        adapterPistasMusica.ViewHolder viewHolder = new adapterPistasMusica.ViewHolder(itemview);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(adapterPistasMusica.ViewHolder holder, int position) {
        // se obtiene puerta de enlace
        ObtenerIp objeto = new ObtenerIp();
        String puertaEnlace = objeto.ObtenerIp(context);

        holder.title.setText(pistasMusicaList.get(position).getListadoNombre());
        //Picasso.with(context).load("http://"+puertaEnlace+pistasMusicaList.get(position).getListadoImagen()).into(holder.imagenView);
        // enviar animacion
        //setAnimation(holder.cardView, position);

    }

    @Override
    public int getItemCount() {
        return pistasMusicaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        //ImageView imagenView;
        //CardView cardView;

        public ViewHolder(View item){
            super(item);

            title = (TextView) item.findViewById(R.id.titulo);
            //imagenView = (ImageView) item.findViewById(R.id.imagenview);
            //cardView = (CardView) item.findViewById(R.id.card_view);
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