package itesm.mx.saludintegral.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.util.TipsAlimentacion;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlimentacionFragment extends Fragment implements View.OnTouchListener{

    View view;
    TextView tvTip;
    GestureDetectorCompat mDetector;
    int indice=0;
    public AlimentacionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_alimentacion, container, false);
        tvTip=view.findViewById(R.id.textViewTip);
        tvTip.setText(TipsAlimentacion.sTips[0]);
        MyGestureListener myGestureListener=new MyGestureListener(getContext());
        mDetector=new GestureDetectorCompat(getContext(), myGestureListener);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        view.setOnTouchListener(this);
        super.onActivityCreated(savedInstance);
    }

    public boolean onTouch(View v, MotionEvent event) {
        mDetector.onTouchEvent(event);
        return true;
    }

    public class MyGestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{
        public static final String LISTENER_TAG="Listener";
        Context context;

        public MyGestureListener(Context applicationContext) {
            context=applicationContext;
        }

        @Override
        public void onShowPress(MotionEvent e)
        {
            Log.d(LISTENER_TAG, "onShowPress");
        }

        @Override
        public void onLongPress(MotionEvent e)
        {
            Log.d(LISTENER_TAG, "onLongPress");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
            Log.d(LISTENER_TAG, "onSingleTapUp");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1,MotionEvent e2, float distanceX, float distanceY)
        {
            Log.d(LISTENER_TAG, "onScroll");
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1,MotionEvent e2, float velocityX, float velocityY)
        {
            Log.d(LISTENER_TAG, "onFling");
            if(e1.getX()>e2.getX()){
                Log.d(LISTENER_TAG, "onFlingRight");
                indice=indice+1;
                if(indice>TipsAlimentacion.sTips.length-1){
                    indice=0;
                }
                tvTip.setText(TipsAlimentacion.sTips[indice]);
            }
            if(e1.getX()<e2.getX()){
                Log.d(LISTENER_TAG, "onFlingLet");
                indice=indice-1;
                if(indice<0){
                    indice=TipsAlimentacion.sTips.length-1;
                }
                tvTip.setText(TipsAlimentacion.sTips[indice]);
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e)
        {
            Log.d(LISTENER_TAG, "onDown");
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e)
        {
            Log.d(LISTENER_TAG, "onSingleTapConfirmed");
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e)
        {
            Log.d(LISTENER_TAG, "onDoubleTap");
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e)
        {
            Log.d(LISTENER_TAG, "onDoubleTapEvent");
            return false;
        }


    }

}
