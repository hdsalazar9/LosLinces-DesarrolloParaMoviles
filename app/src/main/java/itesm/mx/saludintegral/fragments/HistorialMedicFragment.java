package itesm.mx.saludintegral.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.adapters.HistorialMedicamentoAdapter;
import itesm.mx.saludintegral.adapters.HistorialMedicamentoItem;
import itesm.mx.saludintegral.controllers.MedicamentoOperations;
import itesm.mx.saludintegral.controllers.TomarMedicamentoOperations;
import itesm.mx.saludintegral.models.Medicamento;
import itesm.mx.saludintegral.models.TomarMedicamento;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by Héctor on 5/6/2018.
 */

public class HistorialMedicFragment extends Fragment {

    TomarMedicamentoOperations tomMedOp;
    ListView lvMed;

    ArrayAdapter<HistorialMedicamentoItem> histMedAdapter;
    ArrayList<HistorialMedicamentoItem> histMedItems;
    ArrayList<TomarMedicamento> tomMedArr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historial_medicamentos, container, false);
        Miscellaneous.strTipo="";
        lvMed = rootView.findViewById(R.id.lv_fragment_historialmed);

        //Abrir BD y obtener lista de medicamentos y eventos de tomar medicamento
        tomMedOp = new TomarMedicamentoOperations(getContext());
        tomMedOp.open();
        tomMedArr = tomMedOp.getAllProducts();
        tomMedOp.close();

        //Obtener lista para desplegar y ponersela a la lista
        histMedItems = getItemsForArray();
        histMedAdapter = new HistorialMedicamentoAdapter(getContext(),histMedItems);
        lvMed.setAdapter(histMedAdapter);

        return rootView;
    }

    private ArrayList<HistorialMedicamentoItem> getItemsForArray(){
        ArrayList<HistorialMedicamentoItem> auxArr = new ArrayList<>();
        HistorialMedicamentoItem histItem;

        for(TomarMedicamento tmd: tomMedArr){
            histItem = new HistorialMedicamentoItem(tmd.getsNombreMed(),tmd.getFechaHora(),tmd.getTomadoATiempo());
            auxArr.add(histItem);
        }

        return auxArr;
    }

    @Override
    public void onResume(){
        tomMedOp.open();
        super.onResume();
    }
    @Override
    public void onPause(){
        tomMedOp.close();
        Miscellaneous.strTipo=Miscellaneous.tipos[9];

        super.onPause();
    }
    @Override
    public void onDetach(){
        tomMedOp.close();
        super.onDetach();
    }
    @Override
    public void onStop(){
        tomMedOp.close();
        super.onStop();
    }
}
