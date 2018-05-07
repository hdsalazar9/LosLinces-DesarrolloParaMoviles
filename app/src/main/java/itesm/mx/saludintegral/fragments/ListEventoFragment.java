package itesm.mx.saludintegral.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.adapters.EventoAdapter;
import itesm.mx.saludintegral.controllers.EventoOperations;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by FernandoDavid on 05/05/2018.
 */

public class ListEventoFragment extends ListFragment implements AdapterView.OnItemClickListener {

    View view;
    EventoAdapter adapter;
    EventoOperations dao;
    ArrayList<Evento> listEvento;
    OnResponseListener mCallback;
    String strFecha;
    Date fechaSeleccionada;
    TextView tvVacio;

    public ListEventoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_listevento, container, false);
        Log.d("onCreateView", "Se creó la ListView");
        tvVacio = view.findViewById(R.id.tv_fragment_listevento_vacio);
        listEvento = new ArrayList<Evento>();
        dao = new EventoOperations(getContext());
        dao.open();
        listEvento = null;
        Bundle args = getArguments();

        if(args != null) {
            strFecha = args.getString("Fecha");
            Log.d("ARGS", "strFecha: " + strFecha);
            //fechaSeleccionada = Miscellaneous.getDateFromString(strFecha);
            //DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy");
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            try {
                fechaSeleccionada = dateFormat.parse(strFecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        listEvento = mostrarEventos();
        if (listEvento.size() == 0) {
            tvVacio.setVisibility(View.VISIBLE);
        }
        else
        {
            tvVacio.setVisibility(View.GONE);
        }
        Log.d("DEBUG", "Tamano " + String.valueOf(listEvento.size()));
        adapter = new EventoAdapter(getContext(), listEvento);
        setListAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        ListView ls = (ListView) view.findViewById(android.R.id.list);
        ls.setTextFilterEnabled(true);
        registerForContextMenu(ls);
        ls.setOnItemClickListener(this);
        super.onActivityCreated(savedInstance);
    }

    public ArrayList<Evento> mostrarEventos() {
        ArrayList<Evento> eventoList = dao.getAllEventosFromDateAndType(fechaSeleccionada, Miscellaneous.strTipo);
        if (eventoList != null) {
            Log.d("DEBUG", "Evento list NO vacia");
            return eventoList;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Evento evento = (Evento) parent.getItemAtPosition(position);
        mCallback.onResponse(2, evento);
    }

    //Interfaz para que la actividad pueda responder al click en lista
    public interface OnResponseListener {
        public void onResponse(int tipo, Evento evento);
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