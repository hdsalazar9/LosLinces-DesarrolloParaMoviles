package itesm.mx.saludintegral.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.EventoOperations;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by FernandoDavid on 01/05/2018.
 */

public class CalendarioFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    Button btnAddEvento;
    private EventoOperations dao;
    private CaldroidFragment caldroidFragment;
    OnSelectFechaValida mCallback;

    //TODO: OBTENER LOS EVENTOS DE MES Y COLOREAR LOS DIAS QUE SE VEAN AFECTADOS

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
        ColorDrawable green = new ColorDrawable(Color.GREEN);
        ArrayList<Evento> arregloEventosDelMes = dao.getAllProductsFromMonthAndType(intMesABuscar, Miscellaneous.strTipo);

        for(Evento ev : arregloEventosDelMes){
            Miscellaneous.mapFechaFondo.put(ev.getFecha(),green);
        }
        caldroidFragment.setBackgroundDrawableForDates(Miscellaneous.mapFechaFondo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        caldroidFragment = new CaldroidFragment();
        Log.d("OnCREATEVIEW", "Se crea la view");
        View rootView = inflater.inflate(R.layout.fragment_calendario, container, false);
        Miscellaneous.mapFechaFondo = new HashMap<Date, Drawable>();
        btnAddEvento = rootView.findViewById(R.id.btn_addEvento);

        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);


        FragmentTransaction t = getFragmentManager().beginTransaction();
        t.replace(R.id.calendario, caldroidFragment);
        t.commit();


        caldroidFragment.setCaldroidListener(listener);

        btnAddEvento.setOnClickListener(this);

        //Cambiar de color los dates que tengan eventos registrados
        dao = new EventoOperations(getActivity().getApplicationContext());
        dao.open();
        Integer intMesABuscar = cal.get(Calendar.MONTH);
        pintarDiasDeEventos(intMesABuscar);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_addEvento:
                /* Despliega la forma a llenar del tipo de Evento
                 * Checa en qué lugar está (Miscelalaneous.strTipo) y, en base a eso, cambia el fragmento correspondiente
                 * frameLayout_ActivitySocial,frameLayout_ActivityCognicion, etc.
                 */
                AddEventoFragment addEventoFragment = new AddEventoFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                if(Miscellaneous.strTipo.equals(Miscellaneous.tipos[1])) {
                    transaction.replace(R.id.frameLayout_ActivityCognicion,addEventoFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

                if(Miscellaneous.strTipo.equals(Miscellaneous.tipos[0])) {
                    transaction.replace(R.id.frameLayout_ActivityEspiritual,addEventoFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
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
