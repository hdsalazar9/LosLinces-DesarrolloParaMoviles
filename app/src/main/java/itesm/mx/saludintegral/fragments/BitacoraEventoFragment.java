package itesm.mx.saludintegral.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.BitacoraEventoAdapter;
import itesm.mx.saludintegral.controllers.EventoOperations;
import itesm.mx.saludintegral.models.Evento;

/**
 * Created by Héctor on 5/6/2018.
 */

public class BitacoraEventoFragment extends Fragment implements View.OnClickListener{

    EventoOperations evoOp;

    ArrayList<Evento> eventList;
    ArrayAdapter<Evento> eventAdapter;
    String sFiltro = "Todo";

    Button btnTodo;
    Button btnSalud;
    Button btnSocial;
    Button btnCognicion;
    Button btnEspiritual;
    ListView lvEventos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bitacora_evento, container, false);

        lvEventos = rootView.findViewById(R.id.lv_bitacora);
        btnTodo = rootView.findViewById(R.id.btn_bitacora_todo);
        btnSalud = rootView.findViewById(R.id.btn_bitacora_salud);
        btnSocial = rootView.findViewById(R.id.btn_bitacora_social);
        btnCognicion = rootView.findViewById(R.id.btn_bitacora_cognicion);
        btnEspiritual = rootView.findViewById(R.id.btn_bitacora_espiritual);

        //Abrir BD y obtener los datos a desplegar en la lista
        evoOp = new EventoOperations(getContext());
        evoOp.open();
        eventList = evoOp.getAllProducts();
        Collections.sort(eventList, new Evento());
        eventAdapter = new BitacoraEventoAdapter(getContext(),eventList);
        lvEventos.setAdapter(eventAdapter);

        btnTodo.setOnClickListener(this);
        btnSalud.setOnClickListener(this);
        btnSocial.setOnClickListener(this);
        btnCognicion.setOnClickListener(this);
        btnEspiritual.setOnClickListener(this);

        return rootView;
    }

    //TODO: implementar querys para buscar solo cierto tipo de eventos
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_bitacora_todo:
                if(!sFiltro.equals("Todo")){
                    sFiltro = "Todo";
                    eventList = evoOp.getAllProducts();
                    Collections.sort(eventList, new Evento());
                    eventAdapter.notifyDataSetChanged();
                    eventAdapter = new BitacoraEventoAdapter(getContext(),eventList);
                    lvEventos.setAdapter(eventAdapter);
                }
                break;
            case R.id.btn_bitacora_salud:
                if(!sFiltro.equals("Salud")){
                    sFiltro = "Salud";
                    eventList = evoOp.getAllEventosTypeSalud();
                    Collections.sort(eventList, new Evento());
                    eventAdapter = new BitacoraEventoAdapter(getContext(),eventList);
                    lvEventos.setAdapter(eventAdapter);
                }
                break;
            case R.id.btn_bitacora_social:
                if(!sFiltro.equals("Social")){
                    sFiltro = "Social";
                    eventList = evoOp.getAllEventosTypeSocial();
                    Collections.sort(eventList, new Evento());
                    eventAdapter = new BitacoraEventoAdapter(getContext(),eventList);
                    lvEventos.setAdapter(eventAdapter);
                }

                break;
            case R.id.btn_bitacora_cognicion:
                if(!sFiltro.equals("Cognicion")){
                    sFiltro = "Cognicion";
                    eventList = evoOp.getAllEventosTypeCognicion();
                    Collections.sort(eventList, new Evento());
                    eventAdapter = new BitacoraEventoAdapter(getContext(),eventList);
                    lvEventos.setAdapter(eventAdapter);
                }

                break;
            case R.id.btn_bitacora_espiritual:
                if(!sFiltro.equals("Espiritual")){
                    sFiltro = "Espiritual";
                    eventList = evoOp.getAllEventosTypeEspiritual();
                    Collections.sort(eventList, new Evento());
                    eventAdapter = new BitacoraEventoAdapter(getContext(),eventList);
                    lvEventos.setAdapter(eventAdapter);
                }

                break;
        }
    }

    @Override
    public void onResume(){
        evoOp.open();
        super.onResume();
    }
    @Override
    public void onPause(){
        evoOp.close();
        super.onPause();
    }
    @Override
    public void onDetach(){
        evoOp.close();
        super.onDetach();
    }
}
