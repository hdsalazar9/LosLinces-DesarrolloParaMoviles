package itesm.mx.saludintegral.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.fragments.CalendarioFragment;
import itesm.mx.saludintegral.fragments.EventoDisplayFragment;
import itesm.mx.saludintegral.fragments.FragmentoMenuCognicion;
import itesm.mx.saludintegral.fragments.ListEventoFragment;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.util.Miscellaneous;

public class CognicionActivity extends AppCompatActivity implements FragmentoMenuCognicion.OnSelectedListener, ListEventoFragment.OnResponseListener, CalendarioFragment.OnSelectFechaValida, EventoDisplayFragment.OnResponseListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognicion);

        //Crear instancia de FragmentoMenuCognicion
        FragmentoMenuCognicion fragmentoMenuCognicion = new FragmentoMenuCognicion();
        Bundle bundle = new Bundle();
        //Añade el FragmentoMenuSalud al frameLayout_ActivityCongnicion FrameLayout
        getSupportFragmentManager().beginTransaction().add(
                R.id.frameLayout_ActivityCognicion, fragmentoMenuCognicion).commit();
    }

    public void onSelected(int position){
        if(position==1){

            //Log.d("ONSELECTED",""+position);
            Miscellaneous.strTipo = Miscellaneous.tipos[2];
        }
        else
        {
            //Log.d("ONSELECTED",""+position);
            Miscellaneous.strTipo = Miscellaneous.tipos[1];
        }
        CalendarioFragment calendarioFragment = new CalendarioFragment();

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.frameLayout_ActivityCognicion, calendarioFragment);
        t.addToBackStack(null);
        t.commit();

    }

    public void onResponse(int position, Evento evento) {
        switch (position) {
            case 1:
                super.onBackPressed();
                break;

            case 2:
                EventoDisplayFragment eventoDisplayFragment = new EventoDisplayFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("evento", Parcels.wrap(evento));
                eventoDisplayFragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_ActivityCognicion, eventoDisplayFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }

    public void onSelectFechaValida(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        Log.d("COGACTIVITY","la fecha es " + formatter.format(date));
        EventoDisplayFragment eventoDisplayFragment = new EventoDisplayFragment();
        Bundle args = new Bundle();
        //args.putString("Fecha",Miscellaneous.getStringFromDate(date));
        String strFecha = formatter.format(date);
        Log.d("DEBUG",strFecha);
        args.putString("Fecha",strFecha);
        eventoDisplayFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_ActivityCognicion, eventoDisplayFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
