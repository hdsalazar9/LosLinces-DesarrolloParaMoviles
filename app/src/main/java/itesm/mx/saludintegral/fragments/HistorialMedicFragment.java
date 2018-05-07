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

/**
 * Created by Héctor on 5/6/2018.
 */

public class HistorialMedicFragment extends Fragment {

    TomarMedicamentoOperations tomMedOp;
    MedicamentoOperations medOp;
    ListView lvMed;

    ArrayList<HistorialMedicamentoItem> histMedItems;
    ArrayAdapter<HistorialMedicamentoItem> histMedAdapter;
    ArrayList<TomarMedicamento> tomMedArr;
    ArrayList<Medicamento> medArr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_historial_medicamentos, container, false);

        lvMed = rootView.findViewById(R.id.lv_fragment_historialmed);

        //Abrir BD y obtener lista de medicametnos y eventos de tomar medicamento
        tomMedOp = new TomarMedicamentoOperations(getContext());
        medOp = new MedicamentoOperations(getContext());

        tomMedOp.open();
        medOp.open();

        tomMedArr = tomMedOp.getAllProducts();
        medArr = medOp.getAllProducts();

        tomMedOp.close();
        medOp.close();

        //Obtener lista para desplegar y ponersela a la lista
        histMedItems = getItemsForArray();
        histMedAdapter = new HistorialMedicamentoAdapter(getContext(),histMedItems);
        lvMed.setAdapter(histMedAdapter);

        return rootView;
    }

    private ArrayList<HistorialMedicamentoItem> getItemsForArray(){
        ArrayList<HistorialMedicamentoItem> auxArr = new ArrayList<>();
        HistorialMedicamentoItem histItem;
        String nombreMed;

        for(TomarMedicamento tmd: tomMedArr){
            nombreMed = getNameFromMedId(tmd.getIdMedicamento());
            histItem = new HistorialMedicamentoItem(nombreMed,tmd.getFechaHora(),tmd.getTomadoATiempo());
            auxArr.add(histItem);
        }

        return auxArr;
    }

    private String getNameFromMedId(long idABuscar){
        for(Medicamento md: medArr){
            if(md.getId() == idABuscar) return md.getNombre();
        }
        return "Unnamed";
    }

    @Override
    public void onResume(){
        tomMedOp.open();
        medOp.open();
        super.onResume();
    }
    @Override
    public void onPause(){
        tomMedOp.close();
        medOp.close();
        super.onPause();
    }
    @Override
    public void onDetach(){
        tomMedOp.close();
        medOp.close();
        super.onDetach();
    }
}
