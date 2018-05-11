package itesm.mx.saludintegral.activities;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.app.FragmentManager;

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
import itesm.mx.saludintegral.fragments.AddMedicamentoFragment;
import itesm.mx.saludintegral.fragments.AlimentacionFragment;
import itesm.mx.saludintegral.fragments.CalendarioFragment;
import itesm.mx.saludintegral.fragments.EventoDisplayFragment;
import itesm.mx.saludintegral.fragments.EventoZoomFragment;
import itesm.mx.saludintegral.fragments.FragmentoMedicamento;
import itesm.mx.saludintegral.fragments.FragmentoMenuSalud;
import itesm.mx.saludintegral.fragments.FragmentoTomarMedicamento;

import itesm.mx.saludintegral.fragments.ListEventoFragment;
import itesm.mx.saludintegral.models.Evento;

import itesm.mx.saludintegral.fragments.MonitoreoDeSuenoFragment;

import itesm.mx.saludintegral.models.Medicamento;
import itesm.mx.saludintegral.util.Miscellaneous;

public class SaludActivity extends AppCompatActivity implements FragmentoMenuSalud.OnSelectedListener, FragmentoMedicamento.OnResponseListener, FragmentoTomarMedicamento.OnResponseTomar, AddMedicamentoFragment.OnResponseAgregar, MonitoreoDeSuenoFragment.OnResponseMonitoreo,ListEventoFragment.OnResponseListener, CalendarioFragment.OnSelectFechaValida, EventoDisplayFragment.OnResponseListener, EventoZoomFragment.OnResponseListener{

    int iCurrentFrameLayout = R.id.frameLayout_ActivitySalud;
    FrameLayout frameLayout;
    Boolean bSoloUnaVez = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salud);

        //Crear instancia de FragmentoMenuSalud
        FragmentoMenuSalud fragmentoMenuSalud = new FragmentoMenuSalud();
        //Añade el FragmentoMenuSalud al frameLayout_ActivitySalud FrameLayout
        getSupportFragmentManager().beginTransaction().add(
                iCurrentFrameLayout, fragmentoMenuSalud).commit();
    }

    public void onSelected(int position){
        android.support.v4.app.FragmentTransaction transaction;
        switch (position) {
            case 0:
                FragmentoMedicamento fragmentoMedicamento=new FragmentoMedicamento();
                changeFragmentHabilitaBack(fragmentoMedicamento);
                break;
            case 1:
                MonitoreoDeSuenoFragment monitoreoDeSuenoFragment=new MonitoreoDeSuenoFragment();
                changeFragmentHabilitaBack(monitoreoDeSuenoFragment);
                break;
            case 2:
                AlimentacionFragment alimentacionFragment=new AlimentacionFragment();
                changeFragmentHabilitaBack(alimentacionFragment);
                break;
            case 3:
                Miscellaneous.strTipo = Miscellaneous.tipos[3];
                CalendarioFragment calendarioFragment = new CalendarioFragment();
                changeFragmentHabilitaBack(calendarioFragment);
                break;
        }
    }

    public void onResponse(int position, Medicamento medicamento){
        if(position==2){//Cambia al fragmento de detalle de mdeicamento
            FragmentManager fm = this.getSupportFragmentManager();
            fm.popBackStack();
            FragmentoTomarMedicamento fragmentoTomarMedicamento=new FragmentoTomarMedicamento();
            Bundle bundle = new Bundle();
            bundle.putParcelable("medicamento", Parcels.wrap(medicamento));
            fragmentoTomarMedicamento.setArguments(bundle);
            changeFragmentHabilitaBack(fragmentoTomarMedicamento);
        }
        else{//Cambia al fragmento de agregar medicamento
            FragmentManager fm = this.getSupportFragmentManager();
            fm.popBackStack();
            AddMedicamentoFragment addMedicamentoFragment=new AddMedicamentoFragment();
            changeFragmentHabilitaBack(addMedicamentoFragment);
        }
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

    public void onResponseTomar(){//Cierra el fragmento de tomar medicamento y lo reemplaza por la lista de medicamentos
        FragmentManager fm = this.getSupportFragmentManager();
        fm.popBackStack();
        FragmentoMedicamento fragmentoMedicamento=new FragmentoMedicamento();
        changeFragmentHabilitaBack(fragmentoMedicamento);
    }

    public void onResponseAgregar(){//Cierra el fragmento de agregar medicamento y lo reemplaza por la lista de medicamentos
        FragmentManager fm = this.getSupportFragmentManager();
        fm.popBackStack();
        FragmentoMedicamento fragmentoMedicamento=new FragmentoMedicamento();
        changeFragmentHabilitaBack(fragmentoMedicamento);
    }

    public void changeFragmentHabilitaBack(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(iCurrentFrameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onResponseMonitoreo(){
        onBackPressed();
    }

    /*public void onResponseAlimentacion(){
        FragmentoMenuSalud fragmentoMenuSalud=new FragmentoMenuSalud();
        changeFragmentHabilitaBack(fragmentoMenuSalud);
    }*/
}
