package itesm.mx.saludintegral.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

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

public class CalendarioFragment extends Fragment implements View.OnClickListener {

    Button btnAddEvento;
    private EventoOperations database;
    //TODO: OBTENER LOS EVENTOS DE MES Y COLOREAR LOS DIAS QUE SE VEAN AFECTADOS

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    CaldroidListener listener = new CaldroidListener() {
        @Override
        public void onSelectDate(Date date, View view) {
            Toast.makeText(getContext() , ""+date,
                    Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calendario, container, false);

        btnAddEvento = rootView.findViewById(R.id.btn_addEvento);

        CaldroidFragment caldroidFragment = new CaldroidFragment();
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
        database = new EventoOperations(getActivity().getApplicationContext());
        database.open();
        Integer intMesABuscar = cal.get(Calendar.MONTH);
        ColorDrawable green = new ColorDrawable(Color.GREEN);
        ArrayList<Evento> arregloEventosDelMes = database.getAllProductsFromMonthAndType(intMesABuscar, Miscellaneous.strTipo);

        HashMap<Date,Drawable> mapFechaFondo = new HashMap<Date, Drawable>();
        for(Evento ev : arregloEventosDelMes){
            mapFechaFondo.put(ev.getFecha(),green);
        }
        caldroidFragment.setBackgroundDrawableForDates(mapFechaFondo);

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

                Toast.makeText(getContext() , "Se despliega el fragmento para añadir un evento",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onResume(){
        database.open();
        super.onResume();
    }
    @Override
    public void onPause(){
        database.close();
        super.onPause();
    }
}
