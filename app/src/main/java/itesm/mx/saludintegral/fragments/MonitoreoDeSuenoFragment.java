package itesm.mx.saludintegral.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.MonitoreoSuenoOperations;
import itesm.mx.saludintegral.models.MonitoreoSueno;


public class MonitoreoDeSuenoFragment extends Fragment implements View.OnClickListener{

    OnResponseMonitoreo mCallback;
    Button btnAceptar, btnCancelar;
    EditText etHorasSueno;
    View view;
    MonitoreoSuenoOperations dao;
    public MonitoreoDeSuenoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_monitoreo_de_sueno, container, false);
        dao = new MonitoreoSuenoOperations(getContext());
        btnAceptar=(Button)view.findViewById(R.id.button_Aceptar);
        btnCancelar=(Button)view.findViewById(R.id.button_Canelar);
        etHorasSueno=(EditText)view.findViewById(R.id.editTextHorasSueno);
        dao.open();
        btnAceptar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        Activity activity;

        if(context instanceof  Activity){
            //Actividad respondera a la interface
            activity = (Activity) context;
            try{
                mCallback = (OnResponseMonitoreo) activity;
            }   catch(ClassCastException e){
                throw new ClassCastException(activity.toString() +
                        " must implement OnResponseMonitoreo.");
            }
        }
    }

    public interface OnResponseMonitoreo {
        void onResponseMonitoreo();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_Aceptar:
                addSueno();
                break;
            case R.id.button_Canelar:
                mCallback.onResponseMonitoreo();
                break;
        }
    }

    public void addSueno(){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date currentTime = new Date();
        Date dateC=null;
        String strHoras = etHorasSueno.getText().toString();
        Double dHoras;

        if(strHoras.length() == 0) {
            Toast.makeText(getContext(),"Ingrese horas",Toast.LENGTH_SHORT).show();
            return;
        }

        dHoras = Double.parseDouble(strHoras);
        if(dHoras <= 0) {
            Toast.makeText(getContext(),"Ingrese horas válidas",Toast.LENGTH_SHORT).show();
            return;
        }

        long n=0;
        String horaActual=format.format(currentTime);
        try {
            dateC= format.parse(horaActual);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        MonitoreoSueno monitoreoSueno;
        monitoreoSueno = new MonitoreoSueno(n,dateC,dHoras);
        n= dao.addEvento(monitoreoSueno);
        monitoreoSueno.setId(n);
        Toast.makeText(getContext(), "Evento añadido", Toast.LENGTH_SHORT).show();
        mCallback.onResponseMonitoreo();
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
    public void onDetach(){
        dao.close();
        super.onDetach();
    }
    @Override
    public void onStop(){
        dao.close();
        super.onStop();
    }
}
