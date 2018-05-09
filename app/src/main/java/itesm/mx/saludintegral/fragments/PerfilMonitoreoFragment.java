package itesm.mx.saludintegral.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.adapters.MonitoreoArrayAdapter;
import itesm.mx.saludintegral.controllers.MonitoreoSuenoOperations;
import itesm.mx.saludintegral.models.MonitoreoSueno;

/**
 * Created by Héctor on 5/7/2018.
 */

public class PerfilMonitoreoFragment extends Fragment {

    MonitoreoSuenoOperations msOp;
    ListView lvMonitoreos;

    ArrayAdapter<MonitoreoSueno> monitoreoArrayAdap;
    ArrayList<MonitoreoSueno> listMonitoreos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil_monitoreo, container, false);

        lvMonitoreos = rootView.findViewById(R.id.lv_perfil_monitoreo);
        //Abrir BD y obtener monitoreos de sueño
        msOp = new MonitoreoSuenoOperations(getContext());
        msOp.open();
        listMonitoreos = msOp.getAllEvents();

        monitoreoArrayAdap = new MonitoreoArrayAdapter(getContext(),listMonitoreos);
        lvMonitoreos.setAdapter(monitoreoArrayAdap);

        return rootView;
    }

    @Override
    public void onResume(){
        msOp.open();
        super.onResume();
    }
    @Override
    public void onPause(){
        msOp.close();
        super.onPause();
    }
    @Override
    public void onDetach(){
        msOp.close();
        super.onDetach();
    }
    @Override
    public void onStop(){
        msOp.close();
        super.onStop();
    }
}
