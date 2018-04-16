package itesm.mx.saludintegral.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.adapters.MedicamentoAdapter;
import itesm.mx.saludintegral.controllers.MedicamentoOperations;
import itesm.mx.saludintegral.controllers.TomarMedicamentoOperations;
import itesm.mx.saludintegral.models.Medicamento;
import itesm.mx.saludintegral.models.TomarMedicamento;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoTomarMedicamento extends Fragment implements View.OnClickListener{
    View view;
    TomarMedicamentoOperations dao;
    MedicamentoOperations dao2;
    Medicamento medicamento;
    OnResponseTomar mCallback;
    TextView tvNombre, tvTiempo, tvPeriodo;

    public FragmentoTomarMedicamento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragmento_tomar_medicamento, container, false);
        dao = new TomarMedicamentoOperations(getContext());
        dao.open();
        dao2 = new MedicamentoOperations(getContext());
        dao2.open();
        tvNombre=(TextView) view.findViewById(R.id.text_nombre);
        tvTiempo=(TextView)view.findViewById(R.id.textView_tiempo);
        tvPeriodo=(TextView)view.findViewById(R.id.textView_periodo);
        Bundle args = getArguments();
        if(args != null){
            medicamento=args.getParcelable("medicamento");
        }
        tvNombre.setText(medicamento.getNombre());
        tvTiempo.setText(MedicamentoAdapter.getTimeLeft(medicamento.getHora().toString(),medicamento.getCadaCuanto()));
        tvPeriodo.setText(String.valueOf(medicamento.getCadaCuanto()));
        return view;
    }
    public TomarMedicamento newTomarMedicamento(TomarMedicamento tomarMedicamento){
        long id = dao.addEvento(tomarMedicamento);
        tomarMedicamento.setId(id);
        //listAux.add(evento);
        return tomarMedicamento;
    }

    @Override
    public void onResume(){
        dao.open();
        super.onResume();
    }
    @Override
    public void onPause(){
        dao.close();
        super.onPause();
    }

    @Override
    public void onClick(View v) {   //*Agregar aquí código de respuesta acertada/equivocada
        switch (v.getId()){
            case R.id.button_borrar:

                break;
            case R.id.button_ingerido:
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date currentTime = new Date();
                Date dateC=null;
                long n=0;
                String horaActual=format.format(currentTime);
                try {
                    dateC= format.parse(horaActual);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TomarMedicamento tomar=new TomarMedicamento(n, medicamento.getId(), true, dateC);
                newTomarMedicamento(tomar);
                 break;
        }
    }

    public void removeProduct(){
        String name = medicamento.getNombre();
        boolean result = dao2.deleteMedicamento(name);
        if(result){
            mCallback.onResponseTomar();
        }else{
            Toast.makeText(getContext(), "No Match Found", Toast.LENGTH_SHORT).show();
        }
    }

    //Interfaz para que la actividad pueda responder al click en lista
    public interface OnResponseTomar {
        public void onResponseTomar();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        Activity activity;

        if(context instanceof  Activity){
            //Actividad respondera a la interface
            activity = (Activity) context;
            try{
                mCallback = (FragmentoTomarMedicamento.OnResponseTomar) activity;
            }   catch(ClassCastException e){
                throw new ClassCastException(activity.toString() +
                        " must implement OnResponseListener.");
            }
        }
    }
}
