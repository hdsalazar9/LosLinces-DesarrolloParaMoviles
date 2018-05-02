package itesm.mx.saludintegral.fragments;

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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by FernandoDavid on 01/05/2018.
 */

public class CalendarioFragment extends Fragment implements View.OnClickListener {

    Button btnAddEvento;

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
        // Infalte the layout for this fragment
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

                if(Miscellaneous.strTipo.equals("Cognicion")) {
                    transaction.replace(R.id.frameLayout_ActivityCognicion,addEventoFragment).commit();
                }

                if(Miscellaneous.strTipo.equals("Espiritual")) {
                    transaction.replace(R.id.frameLayout_ActivityEspiritual,addEventoFragment).commit();
                }

                break;
        }
    }
}
