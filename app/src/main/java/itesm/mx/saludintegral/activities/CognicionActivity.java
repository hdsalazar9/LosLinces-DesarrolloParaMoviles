package itesm.mx.saludintegral.activities;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.fragments.CalendarioFragment;
import itesm.mx.saludintegral.fragments.EventoDisplayFragment;
import itesm.mx.saludintegral.fragments.EventoZoomFragment;
import itesm.mx.saludintegral.fragments.FragmentoMenuCognicion;
import itesm.mx.saludintegral.fragments.ListEventoFragment;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.util.Miscellaneous;

public class CognicionActivity extends AppCompatActivity implements FragmentoMenuCognicion.OnSelectedListener, ListEventoFragment.OnResponseListener, CalendarioFragment.OnSelectFechaValida, EventoDisplayFragment.OnResponseListener, EventoZoomFragment.OnResponseListener {

    int iCurrentFrameLayout = R.id.frameLayout_ActivityCognicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognicion);

        //Crear instancia de FragmentoMenuCognicion
        FragmentoMenuCognicion fragmentoMenuCognicion = new FragmentoMenuCognicion();

        //Añade el FragmentoMenuCognicion al frameLayout_ActivityCongnicion FrameLayout
        getSupportFragmentManager().beginTransaction().add(
                iCurrentFrameLayout, fragmentoMenuCognicion).commit();
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
        changeFragmentHabilitaBack(calendarioFragment);
    }

    public void onResponse(int position, Evento evento) {
        switch (position) {
            case 1:
                super.onBackPressed();
                break;

            case 2:
                EventoZoomFragment eventoZoomFragment = new EventoZoomFragment();
                Bundle args = new Bundle();
                args.putParcelable("evento",Parcels.wrap(evento));
                eventoZoomFragment.setArguments(args);
                changeFragmentHabilitaBack(eventoZoomFragment);
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
        changeFragmentHabilitaBack(eventoDisplayFragment);
    }

    public void changeFragmentHabilitaBack(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(iCurrentFrameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
