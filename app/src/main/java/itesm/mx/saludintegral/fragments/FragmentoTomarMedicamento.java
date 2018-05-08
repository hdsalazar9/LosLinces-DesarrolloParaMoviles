package itesm.mx.saludintegral.fragments;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

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
import itesm.mx.saludintegral.util.Miscellaneous;
import itesm.mx.saludintegral.util.Receiver;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoTomarMedicamento extends Fragment implements View.OnClickListener{
    View view;
    TomarMedicamentoOperations dao;
    MedicamentoOperations dao2;
    Medicamento medicamento;
    OnResponseTomar mCallback;
    Boolean bAtiempo = false;
    TextView tvNombre, tvCadaCuanto, tvGramaje, tvFechaComienzo, tvADComer, tvCantidad, tvFaltan;
    ImageView ivImagenMed;
    Button btnAgregar, btnBorrar;
    public FragmentoTomarMedicamento() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmento_tomar_medicamento, container, false);
        dao = new TomarMedicamentoOperations(getContext());
        dao.open();
        dao2 = new MedicamentoOperations(getContext());
        dao2.open();
        tvNombre=(TextView) view.findViewById(R.id.textView_nombre);
        tvCadaCuanto=(TextView)view.findViewById(R.id.textView_cadaCuanto);
        tvFaltan=(TextView)view.findViewById(R.id.textView_faltan);
        tvGramaje=(TextView)view.findViewById(R.id.textView_gramaje);
        tvFechaComienzo=(TextView)view.findViewById(R.id.textView_fecha);
        tvADComer=(TextView)view.findViewById(R.id.textView_adComer);
        tvCantidad=(TextView)view.findViewById(R.id.textView_cantidad);
        btnAgregar=(Button)view.findViewById(R.id.button_ingerido);
        btnBorrar=(Button)view.findViewById(R.id.button_borrar);
        ivImagenMed=(ImageView)view.findViewById(R.id.imageView_medicamento);
        Bundle args = getArguments();
        medicamento=new Medicamento();
        if(args != null) {
            medicamento =(Medicamento) Parcels.unwrap(args.getParcelable("medicamento"));
                tvNombre.setText("Nombre: "+medicamento.getNombre());

                String sCada=medicamento.getCadaCuanto()>1?" Horas":" Hora";
                tvCadaCuanto.setText("Cada: "+String.valueOf(medicamento.getCadaCuanto())+ sCada);
                ArrayList<TomarMedicamento>  tomarMedicamento=dao.getAllTomarMedicamentoFrom(String.valueOf(medicamento.getId()));
                String sFalta=MedicamentoAdapter.getTimeTo(medicamento.getHora().toString(),medicamento.getCadaCuanto(), medicamento.getFechaComienzo(), tomarMedicamento);
                if(!sFalta.equals("Retraso")){
                    bAtiempo = true;
                    sFalta="Faltan: "+sFalta;
                    tvFaltan.setText(sFalta);
                }
                else
                {
                    tvFaltan.setText(sFalta);
                }
                tvGramaje.setText("Gramaje: "+String.valueOf(medicamento.getGramaje()));
                tvFechaComienzo.setText("Comienza: "+ Miscellaneous.getStringFromDate(medicamento.getFechaComienzo()));
                tvADComer.setText((medicamento.getAntesDespuesDeComer()?"Antes de comer":"Despues de comer"));
                tvCantidad.setText("Cantidad: "+String.valueOf(medicamento.getCantidad()));
        }
        btnBorrar.setOnClickListener(this);
        btnAgregar.setOnClickListener(this);
        return view;
    }
    public TomarMedicamento newTomarMedicamento(TomarMedicamento tomarMedicamento){
        long id = dao.addTomarMedicamento(tomarMedicamento);
        tomarMedicamento.setId(id);
        //listAux.add(evento);
        return tomarMedicamento;
    }

    @Override
    public void onResume(){
        dao.open();
        dao2.open();
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
        dao2.close();
        super.onDetach();
    }

    @Override
    public void onClick(View v) {   //*Agregar aquí código de respuesta acertada/equivocada
        switch (v.getId()){
            case R.id.button_borrar:
                Intent alarmIntent = new Intent(getContext(), Receiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        getContext(), (int) medicamento.getId(), alarmIntent, 0);
                AlarmManager manager = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
                manager.cancel(pendingIntent);
                removeProduct();
                break;
            case R.id.button_ingerido:
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                Date currentTime = new Date();
                Date dateC=null;
                long n=0;
                String horaActual=format.format(currentTime);
                try {
                    dateC= format.parse(horaActual);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                TomarMedicamento tomar=new TomarMedicamento(n, medicamento.getId(), bAtiempo, dateC);
                newTomarMedicamento(tomar);
                mCallback.onResponseTomar();
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
                mCallback = (OnResponseTomar) activity;
            }   catch(ClassCastException e){
                throw new ClassCastException(activity.toString() +
                        " must implement OnResponseTomar.");
            }
        }
    }
}
