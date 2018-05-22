package itesm.mx.saludintegral.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
Salud Integral AM - Aplicación para manejo de recordatorios para adultos mayores.

	Copyright (C) 2018 - ITESM

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>
*/

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.CumpleanoOperations;
import itesm.mx.saludintegral.controllers.EventoOperations;
import itesm.mx.saludintegral.models.Cumpleano;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by FernandoDavid on 01/05/2018.
 */

public class CalendarioFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    Button btnAddEvento, btnEliminar;
    private EventoOperations dao;
    private CumpleanoOperations dao2;
    private CaldroidFragment caldroidFragment;
    OnSelectFechaValida mCallback;
    private Integer intMesABuscar;
    Calendar cal;
    View rootView;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    CaldroidListener listener = new CaldroidListener() {
        @Override
        public void onSelectDate(Date date, View view) {

            if(Miscellaneous.mapFechaFondo.get(date) == null)
            {
                Toast.makeText(getContext(), "No hay eventos registrados en " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                mCallback.onSelectFechaValida(date);
            }
        }

        @Override
        public void onChangeMonth(int month, int year) {
            pintarDiasDeEventos(month);
        }
    };

    public void pintarDiasDeEventos(Integer intMesABuscar){
        ColorDrawable colorDrawable = new ColorDrawable();
        if(Miscellaneous.strTipo.equals(Miscellaneous.tipos[0])) {
            colorDrawable = new ColorDrawable(getResources().getColor(R.color.colorEspiritual));
        } else if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[1])) {
            colorDrawable = new ColorDrawable(getResources().getColor(R.color.colorEjercicio));
        } else if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[2])) {
            colorDrawable = new ColorDrawable(getResources().getColor(R.color.colorFinanzas));
        } else if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[3])) {
            colorDrawable = new ColorDrawable(getResources().getColor(R.color.colorSalud));
        } else if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[4])) {
            colorDrawable = new ColorDrawable(getResources().getColor(R.color.colorSocial));
        } else if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[5])) {
            colorDrawable = new ColorDrawable(getResources().getColor(R.color.colorSocial));
        } else if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[6])) {
            colorDrawable = new ColorDrawable(getResources().getColor(R.color.colorSocial));
        } else if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[7])) {
            colorDrawable = new ColorDrawable(getResources().getColor(R.color.colorSocial));
        }

        if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[4]) || Miscellaneous.strTipo.equals(Miscellaneous.tipos[6])) {
            ArrayList<Cumpleano> arregloCumpleanosDelMes = dao2.getAllProductsFromMonthAndType(intMesABuscar,Miscellaneous.strTipo);
            for(Cumpleano cu : arregloCumpleanosDelMes) {
                Miscellaneous.mapFechaFondo.put(cu.getFecha(),colorDrawable);
            }
        }
        else {
            ArrayList<Evento> arregloEventosDelMes = dao.getAllProductsFromMonthAndType(intMesABuscar, Miscellaneous.strTipo);
            Log.d("Tamaño de la lista: ",String.valueOf(arregloEventosDelMes.size()));
            for (Evento ev : arregloEventosDelMes) {
                Miscellaneous.mapFechaFondo.put(ev.getFecha(), colorDrawable);
            }
        }
        caldroidFragment.setBackgroundDrawableForDates(Miscellaneous.mapFechaFondo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        caldroidFragment = new CaldroidFragment();
        Log.d("OnCREATEVIEW", "Se crea la view");
        rootView = inflater.inflate(R.layout.fragment_calendario, container, false);
        Miscellaneous.limpiaMapFechaFondo();
        btnAddEvento = rootView.findViewById(R.id.btn_addEvento);
        btnEliminar=rootView.findViewById(R.id.btn_eliminarTodosEventos);
        caldroidFragment = new CaldroidFragment();

        Bundle args = new Bundle();
        cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);


        FragmentTransaction t = getFragmentManager().beginTransaction();
        t.replace(R.id.calendario, caldroidFragment);
        t.commit();


        caldroidFragment.setCaldroidListener(listener);

        if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[4]) || Miscellaneous.strTipo.equals(Miscellaneous.tipos[6])) {
            btnAddEvento.setText(R.string.fragment_calendario_addCumple);
        }
        else
        {
            btnAddEvento.setText(R.string.fragment_calendario_add);
        }

        btnAddEvento.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        //Cambiar de color los dates que tengan eventos registrados
        dao = new EventoOperations(getActivity().getApplicationContext());
        dao2 = new CumpleanoOperations(getActivity().getApplicationContext());
        dao.open();
        dao2.open();
        intMesABuscar = cal.get(Calendar.MONTH);
        pintarDiasDeEventos(intMesABuscar);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        switch(v.getId()) {
            case R.id.btn_addEvento:
                /* Despliega la forma a llenar del tipo de Evento
                 * Checa en qué lugar está (Miscelalaneous.strTipo) y, en base a eso, cambia el fragmento correspondiente
                 * frameLayout_ActivitySocial,frameLayout_ActivityCognicion, etc.
                 */
                //Cumpleanos Familiar/Amigos
                if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[4]) || Miscellaneous.strTipo.equals(Miscellaneous.tipos[6])) {
                    AddCumpleanosFragment addCumpleanosFragment = new AddCumpleanosFragment();
                    transaction.replace(R.id.frameLayout_ActivitySocial, addCumpleanosFragment);
                }
                else
                {
                    AddEventoFragment addEventoFragment = new AddEventoFragment();
                    //Espiritualidad
                    if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[0])) {
                        transaction.replace(R.id.frameLayout_ActivityEspiritual, addEventoFragment);
                    }

                    //Actividad Cognitiva/Finanzas
                    if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[1]) || Miscellaneous.strTipo.equals(Miscellaneous.tipos[2])) {
                        transaction.replace(R.id.frameLayout_ActivityCognicion, addEventoFragment);
                    }


                    //Ejercicios Físicos
                    if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[3])) {
                        transaction.replace(R.id.frameLayout_ActivitySalud, addEventoFragment);
                    }

                    //Eventos Familia/Amigos
                    if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[5]) || Miscellaneous.strTipo.equals(Miscellaneous.tipos[7])) {
                        transaction.replace(R.id.frameLayout_ActivitySocial, addEventoFragment);
                    }
                }
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.btn_eliminarTodosEventos:
                dao.deleteAllEventosSeccion(Miscellaneous.strTipo);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
                break;
        }
    }

    @Override
    public void onResume(){
        dao.open();
        dao2.open();
        super.onResume();
    }
    @Override
    public void onPause(){
        dao.close();
        dao2.close();
        super.onPause();
    }
    @Override
    public void onDetach(){
        dao.close();
        dao2.close();
        super.onDetach();
    }
    @Override
    public void onStop(){
        dao.close();
        dao2.close();
        super.onStop();
    }

    //Interfaz para que la actividad pueda responder al click en lista
    public interface OnSelectFechaValida {
        public void onSelectFechaValida(Date strFecha);

    }

    @Override
    public void onAttach(Context context){
        Log.d("OnAttach", "Ando en onAttach");
        super.onAttach(context);

        Activity activity;

        if(context instanceof  Activity){
            //Actividad respondera a la interface
            activity = (Activity) context;
            try{
                mCallback = (OnSelectFechaValida) activity;
            }   catch(ClassCastException e){
                throw new ClassCastException(activity.toString() +
                        " must implement OnResponseListener.");
            }
        }
    }

}
