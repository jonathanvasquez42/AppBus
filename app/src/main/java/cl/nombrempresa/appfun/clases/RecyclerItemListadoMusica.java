package cl.nombrempresa.appfun.clases;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/*
 Creado 2016  Jonathan Andrés Vásquez Subiabre
 */


public class RecyclerItemListadoMusica implements RecyclerView.OnItemTouchListener {
    protected OnItemClickListener listener;
    private GestureDetector gestureDetector;

    private View childview;
    private int childViewPosition;

    public RecyclerItemListadoMusica(Context context, OnItemClickListener listener) {
        this.gestureDetector = new GestureDetector(context, new GestureListener());
        this.listener = listener;

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        childview = rv.findChildViewUnder(e.getX(), e.getY());
        childViewPosition = rv.getChildPosition(childview);

        return childview != null && gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    public interface OnItemClickListener {

        public void onItemClick(View childview, int position);
    }

    public static abstract class SimpleOnitemClickListener implements OnItemClickListener {

        public void OnItemClick(View childview, int position) {


        }
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            if (childview != null) {

                listener.onItemClick(childview, childViewPosition);
            }

            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
}