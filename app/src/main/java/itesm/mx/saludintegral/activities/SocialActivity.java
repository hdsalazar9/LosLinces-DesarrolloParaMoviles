package itesm.mx.saludintegral.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.fragments.CalendarioFragment;
import itesm.mx.saludintegral.fragments.CumpleanoDisplayFragment;
import itesm.mx.saludintegral.fragments.CumpleanoZoomFragment;
import itesm.mx.saludintegral.fragments.EventoDisplayFragment;
import itesm.mx.saludintegral.fragments.EventoZoomFragment;
import itesm.mx.saludintegral.fragments.FragmentoFamiliaAmigos;
import itesm.mx.saludintegral.fragments.FragmentoMenuCognicion;
import itesm.mx.saludintegral.fragments.FragmentoMenuSalud;
import itesm.mx.saludintegral.fragments.FragmentoMenuSocial;
import itesm.mx.saludintegral.fragments.ListCumpleanoFragment;
import itesm.mx.saludintegral.fragments.ListEventoFragment;
import itesm.mx.saludintegral.models.Cumpleano;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.util.Miscellaneous;

public class SocialActivity extends AppCompatActivity implements FragmentoMenuSocial.OnSelectedListener, FragmentoFamiliaAmigos.OnSelectedListener, ListEventoFragment.OnResponseListener, CalendarioFragment.OnSelectFechaValida, EventoDisplayFragment.OnResponseListener, EventoZoomFragment.OnResponseListener, CumpleanoDisplayFragment.OnResponseListener, ListCumpleanoFragment.OnResponseListener, CumpleanoZoomFragment.OnResponseListener {

    int iCurrentFrameLayout = R.id.frameLayout_ActivitySocial;
    TextView tvVacía;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        //Crear instancia de FragmentoMenuCognicion
        FragmentoMenuSocial fragmentoMenuSocial = new FragmentoMenuSocial();

        //Añade el FragmentoMenuCognicion al frameLayout_ActivityCongnicion FrameLayout
        getSupportFragmentManager().beginTransaction().add(
                iCurrentFrameLayout, fragmentoMenuSocial).commit();
    }

    public void onSelected(String tabSelected){
        FragmentoFamiliaAmigos fragmentoFamiliaAmigos = new FragmentoFamiliaAmigos();
        Bundle args = new Bundle();
        args.putString("filtro",tabSelected);
        fragmentoFamiliaAmigos.setArguments(args);
        changeFragmentHabilitaBack(fragmentoFamiliaAmigos);
    }

    public void onSelected(int iSeleccion){
        switch (iSeleccion) {
            case 0:
                Miscellaneous.strTipo = Miscellaneous.tipos[7];
                break;

            case 1:
                Miscellaneous.strTipo = Miscellaneous.tipos[6];
                break;

            case 2:
                Miscellaneous.strTipo = Miscellaneous.tipos[5];
                break;

            case 3:
                Miscellaneous.strTipo = Miscellaneous.tipos[4];
                break;
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
                args.putParcelable("evento", Parcels.wrap(evento));
                eventoZoomFragment.setArguments(args);
                changeFragmentHabilitaBack(eventoZoomFragment);
                break;
        }
    }

    public void onResponse(int position, Cumpleano cumpleano) {
        switch (position) {
            case 1:
                super.onBackPressed();
                break;

            case 2:
                CumpleanoZoomFragment cumpleanoZoomFragment = new CumpleanoZoomFragment();
                Bundle args = new Bundle();
                args.putParcelable("cumpleano", Parcels.wrap(cumpleano));
                cumpleanoZoomFragment.setArguments(args);
                changeFragmentHabilitaBack(cumpleanoZoomFragment);
                break;
        }
    }


    public void onSelectFechaValida(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Bundle args = new Bundle();
        String strFecha = formatter.format(date);
        if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[4]) || Miscellaneous.strTipo.equals(Miscellaneous.tipos[6])) {
            CumpleanoDisplayFragment cumpleanoDisplayFragment = new CumpleanoDisplayFragment();
            Log.d("DEBUG", strFecha);
            args.putString("Fecha", strFecha);
            cumpleanoDisplayFragment.setArguments(args);
            changeFragmentHabilitaBack(cumpleanoDisplayFragment);
        }
        else
        {
            Log.d("COGACTIVITY", "la fecha es " + formatter.format(date));
            EventoDisplayFragment eventoDisplayFragment = new EventoDisplayFragment();
            Log.d("DEBUG", strFecha);
            args.putString("Fecha", strFecha);
            eventoDisplayFragment.setArguments(args);
            changeFragmentHabilitaBack(eventoDisplayFragment);
        }
    }

    public void changeFragmentHabilitaBack(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(iCurrentFrameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
