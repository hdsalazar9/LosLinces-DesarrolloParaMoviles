package itesm.mx.saludintegral.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.adapters.ContactoEmeAdapter;
import itesm.mx.saludintegral.adapters.EventoAdapter;
import itesm.mx.saludintegral.controllers.ContactoEmergenciaOperations;
import itesm.mx.saludintegral.dbcreation.DataBaseSchema;
import itesm.mx.saludintegral.models.ContactoEmergencia;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by FernandoDavid on 08/05/2018.
 */

public class ListContactoEmergencia extends ListFragment implements AdapterView.OnItemClickListener {
    View view;
    ContactoEmeAdapter adapter;
    ContactoEmergenciaOperations dao;
    ArrayList<ContactoEmergencia> listContactos;
    OnResponseListener mCallback;
    TextView tvVacio;

    public ListContactoEmergencia() {
        //Requires empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_listevento, container, false);
        tvVacio = view.findViewById(R.id.tv_fragment_listevento_vacio);
        listContactos = new ArrayList<ContactoEmergencia>();
        dao = new ContactoEmergenciaOperations(getContext());
        dao.open();
        listContactos = null;

        listContactos = mostrarContactos();
        if (listContactos.size() == 0) {
            tvVacio.setVisibility(View.VISIBLE);
        }
        else
        {
            tvVacio.setVisibility(View.GONE);
        }
        Log.d("DEBUG", "Tamano " + String.valueOf(listContactos.size()));
        adapter = new ContactoEmeAdapter(getContext(), listContactos);
        setListAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        ListView ls = (ListView) view.findViewById(android.R.id.list);
        ls.setTextFilterEnabled(true);
        registerForContextMenu(ls);
        ls.setOnItemClickListener(this);
        Log.d("onActivityCreated", "Se le puso un listener a la lista");
        super.onActivityCreated(savedInstance);
    }

    public ArrayList<ContactoEmergencia> mostrarContactos() {
        ArrayList<ContactoEmergencia> contactoList = dao.getAllProducts();
        if (contactoList != null) {
            Log.d("DEBUG", "Evento list NO vacia");
            return contactoList;
        }
        else
        {
            Log.d("DEBUG", "Evento list vacia");
            return null;
        }
    }

    @Override
    public void onResume(){
        dao.open();
        super.onResume();
    }
    @Override
    public void onPause(){
        dao.close();
        super.onPause();
    }
    @Override
    public void onDetach(){
        dao.close();
        super.onDetach();
    }
    @Override
    public void onStop(){
        dao.close();
        super.onStop();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        ContactoEmergencia contactoEmergencia = (ContactoEmergencia) parent.getItemAtPosition(position);
        mCallback.onResponse(1, contactoEmergencia);
    }

    //Interfaz para que la actividad pueda responder al click en lista
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