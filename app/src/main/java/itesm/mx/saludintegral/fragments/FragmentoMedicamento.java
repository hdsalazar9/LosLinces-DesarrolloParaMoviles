package itesm.mx.saludintegral.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.adapters.MedicamentoAdapter;
import itesm.mx.saludintegral.controllers.MedicamentoOperations;
import itesm.mx.saludintegral.models.Medicamento;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoMedicamento extends ListFragment implements AdapterView.OnItemClickListener{
    View view;
    MedicamentoAdapter adapter;
    MedicamentoOperations dao;
    byte[] array;
    ArrayList<Medicamento> listMedicamento;
    OnResponseListener mCallback;

    public FragmentoMedicamento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragmento_medicamento, container, false);
        FloatingActionButton btn = (FloatingActionButton) view.findViewById(R.id.fab);
        dao = new MedicamentoOperations(getContext());
        dao.open();
        array=null;
        listMedicamento = mostrarMedicamentos();
        //listAux=listEvento;
        adapter = new MedicamentoAdapter(getContext(), listMedicamento);
        setListAdapter(adapter);
        Log.d("DEBUG","Tamano " + String.valueOf(listMedicamento.size()));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onResponse(1, null);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        ListView ls = (ListView) view.findViewById(android.R.id.list);
        ls.setTextFilterEnabled(true);
        registerForContextMenu(ls);
        ls.setOnItemClickListener(this);
        super.onActivityCreated(savedInstance);
    }

    public ArrayList<Medicamento> mostrarMedicamentos(){
        ArrayList<Medicamento> medicamentoList=dao.getAllProducts();
        if(medicamentoList!=null){
            Log.d("DEBUG","Medicamento list NO vacia");
            return medicamentoList;
        }
        else {
            Log.d("DEBUG","Medicamento list vacia");
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
        //Electrodomestico electrodomestico = (Electrodomestico) parent.getItemAtPosition(position);
        /*Bundle bundle = new Bundle();
        Intent i = new Intent(Tab1Fragment.this, DetalleActivity.class);
        bundle.putSerializable("libro",libro);
        i.putExtras(bundle);
        startActivity(i);*/
        //Intent intent = new Intent(getActivity(), FragmentoTomarMed.class);

        Medicamento medicamento=(Medicamento)parent.getItemAtPosition(position);
        mCallback.onResponse(2, medicamento);

        /*intent.putExtra("medicamento", Parcels.wrap(medicamento));
        startActivity(intent);*/
    }

    //Interfaz para que la actividad pueda responder al click en lista
    public interface OnResponseListener {
        public void onResponse(int tipo, Medicamento medicamento);
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
