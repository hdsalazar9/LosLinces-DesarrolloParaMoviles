package itesm.mx.saludintegral.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.EventoOperations;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.util.Ejercicios;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by FernandoDavid on 02/05/2018.
 */

public class AddEventoFragment extends Fragment implements View.OnClickListener {
    EditText etNombre;
    EditText etDescripcion;
    EditText etFecha;
    CheckBox cbPeriodicidad;
    EditText etSemanas;
    EditText etTipo;
    Button btnAdd;
    Button btnFecha;
    RadioGroup radioGroup;
    RadioButton rbUno;
    RadioButton rbDos;
    RadioButton rbTres;
    RadioButton rbCuatro;
    RadioButton rbCinco;
    RadioButton rbOtro;

    EventoOperations dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infalte the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_addevento, container, false);
        etNombre = rootView.findViewById(R.id.et_addevento_nombre);
        etDescripcion = rootView.findViewById(R.id.et_addevento_descripcion);
        etFecha = rootView.findViewById(R.id.et_addevento_fecha);
        cbPeriodicidad = rootView.findViewById(R.id.cb_periodicidad);
        etSemanas = rootView.findViewById(R.id.et_addevento_periodicidad);
        etTipo = rootView.findViewById(R.id.et_addevento_tipo);
        btnAdd = rootView.findViewById(R.id.btn_registerEvento);
        btnFecha = rootView.findViewById(R.id.btn_fecha);
        radioGroup = rootView.findViewById(R.id.radioGroupEvento);
        rbUno = rootView.findViewById(R.id.rb_opcionUno);
        rbDos = rootView.findViewById(R.id.rb_opcionDos);
        rbTres = rootView.findViewById(R.id.rb_opcionTres);
        rbCuatro = rootView.findViewById(R.id.rb_opcionCuatro);
        rbCinco = rootView.findViewById(R.id.rb_opcionCinco);
        rbOtro = rootView.findViewById(R.id.rb_opcionOtro);

        dao = new EventoOperations(getContext());
        dao.open();

        cbPeriodicidad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    etSemanas.setEnabled(true);
                }
                else {
                    etSemanas.setEnabled(false);
                }
            }
        });

        if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[0])) {
            asignaOpciones(Ejercicios.espiritual);
        }


        if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[1])) {
            asignaOpciones(Ejercicios.cognicion);
        }

        if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[2])) {
            asignaOpciones(Ejercicios.finanzas);
        }

        if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[3])) {
            asignaOpciones(Ejercicios.fisicos);
        }

        if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[5]) || Miscellaneous.strTipo.equals(Miscellaneous.tipos[7])) {
            asignaOpciones(Ejercicios.eventos);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_opcionUno:
                        etNombre.setEnabled(false);
                        etNombre.setText(rbUno.getText());
                        break;

                    case R.id.rb_opcionDos:
                        etNombre.setEnabled(false);
                        etNombre.setText(rbDos.getText());
                        break;

                    case R.id.rb_opcionTres:
                        etNombre.setEnabled(false);
                        etNombre.setText(rbTres.getText());
                        break;

                    case R.id.rb_opcionCuatro:
                        etNombre.setEnabled(false);
                        etNombre.setText(rbCuatro.getText());
                        break;

                    case R.id.rb_opcionCinco:
                        etNombre.setEnabled(false);
                        etNombre.setText(rbCinco.getText());
                        break;

                    case R.id.rb_opcionOtro:
                        etNombre.setEnabled(true);
                        etNombre.setText("");
                        break;
                }
            }
        });


        rbUno.setChecked(true);
        etNombre.setEnabled(false);
        etTipo.setText(Miscellaneous.strTipo);
        etTipo.setEnabled(false);
        etFecha.setEnabled(false);
        etSemanas.setEnabled(false);

        btnAdd.setOnClickListener(this);
        btnFecha.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_registerEvento:
                newEvento();
                break;

            case R.id.btn_fecha:
                Miscellaneous.strDatePicker="fechaEvento";
                getDatePicked();
                break;
        }
    }

    public void asignaOpciones(String[] strOpciones) {
        rbUno.setText(strOpciones[0]);
        rbDos.setText(strOpciones[1]);
        rbTres.setText(strOpciones[2]);
        rbCuatro.setText(strOpciones[3]);
        rbCinco.setText(strOpciones[4]);
        rbOtro.setText(strOpciones[5]);
    }

    public void newEvento() {
        String strSemanas = etSemanas.getText().toString();
        int iNumero;
        if(strSemanas.equals("")){
            iNumero = 0;
        }else {
            iNumero = Integer.parseInt(strSemanas);
        }

        Log.d("DEBUG","strSemanas: "+strSemanas);
        Log.d("DEBUG","strSemanas > 0? " + (iNumero>0));
        Log.d("DEBUG","cbPeriodicidad.isChecked() " + cbPeriodicidad.isChecked());

        if (etNombre.getText().toString().length() == 0) {
            Toast.makeText(getContext(), "Ingresar nombre del evento", Toast.LENGTH_SHORT).show();
            return;
        }

        if (etFecha.getText().length() == 0 ) {
            Toast.makeText(getContext(), "Seleccione fecha", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cbPeriodicidad.isChecked() && (iNumero<=0)) {
            Toast.makeText(getContext(), "Ingrese un número de semanas válido", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date dateFecha = new Date();
        String strNombre = etNombre.getText().toString();
        String strDescripcion = etDescripcion.getText().toString();
        String strTipo = etTipo.getText().toString();
        String strFecha = etFecha.getText().toString();
        Calendar calendar = Calendar.getInstance();

        int iVeces;

        if (etSemanas.getText().toString().length() == 0) {
            iVeces = 0;
        }
        else
        {
            iVeces = Integer.parseInt(etSemanas.getText().toString());
        }

        try {
            dateFecha = dateFormat.parse(strFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        do {
            Log.d("ADDEVENTO", "dateFecha: " + dateFecha);
            long l = 0;
            Evento evento = new Evento(l, strNombre, strDescripcion, dateFecha, strTipo);
            long id = dao.addEvento(evento);
            evento.setId(id);
            calendar.setTime(dateFecha);
            calendar.add(Calendar.DATE,7);
            dateFecha = calendar.getTime();
            iVeces--;
        } while (iVeces >= 0);

        Toast.makeText(getContext(), "Evento registrado", Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }

    public void getDatePicked() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
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