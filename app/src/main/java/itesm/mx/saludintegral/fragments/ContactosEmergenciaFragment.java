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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.adapters.ContactoEmeAdapter;
import itesm.mx.saludintegral.controllers.ContactoEmergenciaOperations;
import itesm.mx.saludintegral.models.ContactoEmergencia;

/**
 * Created by Héctor on 5/7/2018.
 */

public class ContactosEmergenciaFragment extends Fragment implements View.OnClickListener{

    Button btnAddCont;
    OnResponseListener mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contactos_emergencia, container, false);

        btnAddCont = rootView.findViewById(R.id.btn_addContactoEm);

        ListContactoEmergencia listContactoEmergencia = new ListContactoEmergencia();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.listView,listContactoEmergencia);
        transaction.commit();

        btnAddCont.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        mCallback.onResponse(0, null);
        /*

                */
    }

    public interface OnResponseListener {
        public void onResponse(int id, ContactoEmergencia contactoEmergencia);
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
