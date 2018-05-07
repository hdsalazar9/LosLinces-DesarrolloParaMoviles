package itesm.mx.saludintegral.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.models.Cumpleano;

/**
 * Created by FernandoDavid on 03/05/2018.
 */

public class CumpleanoDisplayFragment extends Fragment {

    Button btnBack;
    OnResponseListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Infalte the layout for this fragment
        Log.d("CREATE","SE CREA LA VISTA DE CUMPLEANOISPLAYFRAGMENT");
        View rootView = inflater.inflate(R.layout.fragment_cumpleanodisplay,container,false);

        btnBack = rootView.findViewById(R.id.btn_regresar);
        ListCumpleanoFragment listCumpleanoFragment = new ListCumpleanoFragment();
        Bundle info = getArguments();

        if (info != null) {
            String strFecha = info.getString("Fecha");
            Log.d("DEBUG",strFecha);
        }

        listCumpleanoFragment.setArguments(info);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.listView, listCumpleanoFragment);
        //transaction.addToBackStack(null);
        transaction.commit();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onResponse(1,null);
            }
        });

        return rootView;
    }

    //Interfaz para que la actividad pueda responder al click en lista
    public interface OnResponseListener {
        public void onResponse(int tipo, Cumpleano cumpleano);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        Activity activity;

        if(context instanceof  Activity){
            //Actividad respondera a la interface
            activity = (Activity) context;
            try{
                mCallback = (OnResponseListener) activity;
            }   catch(ClassCastException e){
                throw new ClassCastException(activity.toString() +
                        " must implement OnResponseListener.");
            }
        }
    }

}
