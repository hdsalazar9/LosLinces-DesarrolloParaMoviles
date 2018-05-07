package itesm.mx.saludintegral.activities;

import android.support.v4.app.Fragment;
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
import itesm.mx.saludintegral.fragments.EventoZoomFragment;
import itesm.mx.saludintegral.fragments.ListEventoFragment;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.util.Miscellaneous;

public class EspiritualActivity extends AppCompatActivity implements ListEventoFragment.OnResponseListener, CalendarioFragment.OnSelectFechaValida, EventoDisplayFragment.OnResponseListener, EventoZoomFragment.OnResponseListener {

    int iCurrentFrameLayout = R.id.frameLayout_ActivityEspiritual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espiritual);

        Miscellaneous.strTipo = Miscellaneous.tipos[0];

        CalendarioFragment calendarioFragment = new CalendarioFragment();

        changeFragmentNoBack(calendarioFragment);
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

    public void onResponse(int position, Evento evento) {
        switch (position) {
            case 1:
                super.onBackPressed();
                break;

            case 2:
                EventoZoomFragment eventoZoomFragment = new EventoZoomFragment();
                Bundle args = new Bundle();
                args.putParcelable("evento", Parcels.wrap(evento));
                eventoZoomFragment.setArguments(args);
                changeFragmentHabilitaBack(eventoZoomFragment);
                break;
        }
    }

    public void changeFragmentHabilitaBack(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(iCurrentFrameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeFragmentNoBack(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(iCurrentFrameLayout, fragment);
        transaction.commit();
    }

}
