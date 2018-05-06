package itesm.mx.saludintegral.fragments;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.MedicamentoOperations;
import itesm.mx.saludintegral.models.Medicamento;
import itesm.mx.saludintegral.util.Miscellaneous;

import static android.app.Activity.RESULT_OK;

/**
 * Created by FernandoDavid on 15/04/2018.
 */

public class AddMedicamentoFragment extends Fragment implements View.OnClickListener{

    final static int REQUEST_CODE = 0;
    EditText etNombre;
    EditText etGramaje;
    EditText etCantidadIngerir;
    EditText etHoraIngesta;
    EditText etCadaCuanto;
    EditText etFechaInicio;
    EditText etFechaTermino;
    Button btnTomarFoto;
    Button btnAddMed;
    Button btnFechaInicio;
    Button btnFechaTermino;
    Button btnHoraIngesta;
    ImageView ivFoto;
    Bitmap bitmap;
    Medicamento medicamento;
    RadioGroup radioGroup;
    CheckBox cbLunes;
    CheckBox cbMartes;
    CheckBox cbMiercoles;
    CheckBox cbJueves;
    CheckBox cbViernes;
    CheckBox cbSabado;
    CheckBox cbDomingo;


    MedicamentoOperations dao;

    String strNombre;
    double dGramaje;
    int iCantidad;
    String strPeriodicidad;
    Time timeHora;
    int iCadaCuanto;
    Date dateInicio;
    Date dateTermino;
    boolean bAntesDespuesComer;
    byte[] byteFoto;
    String strAntesDespues = "";
    String strHora="00:00:00";
    int iYearInicio, iMesInicio, iDiaInicio, iYearTermino, iMesTermino, iDiaTermino;
    static final int DIALOG_ID = 0;

    public AddMedicamentoFragment() {
        // Required empty public constructor.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infalte the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_addmedicamento, container,false);

        etNombre = rootView.findViewById(R.id.et_nombreMed);
        etGramaje = rootView.findViewById(R.id.et_gramajeMed);
        etCantidadIngerir = rootView.findViewById(R.id.et_cantidadIngerirMed);
        etHoraIngesta = rootView.findViewById(R.id.et_horaIngestaMed);
        etCadaCuanto = rootView.findViewById(R.id.et_cadaCuantoMed);
        etFechaInicio = rootView.findViewById(R.id.et_inicioMed);
        etFechaTermino = rootView.findViewById(R.id.et_terminoMed);
        btnTomarFoto = rootView.findViewById(R.id.btn_tomarFotoMed);
        ivFoto = rootView.findViewById(R.id.iv_fotoMed);
        btnAddMed = rootView.findViewById(R.id.btn_addMed);
        radioGroup = rootView.findViewById(R.id.radioGroup);
        cbLunes = rootView.findViewById(R.id.cb_Lunes);
        cbMartes = rootView.findViewById(R.id.cb__Martes);
        cbMiercoles = rootView.findViewById(R.id.cb_Miercoles);
        cbJueves = rootView.findViewById(R.id.cb_Jueves);
        cbViernes = rootView.findViewById(R.id.cb_Viernes);
        cbSabado = rootView.findViewById(R.id.cb_Sabado);
        cbDomingo = rootView.findViewById(R.id.cb_Domingo);
        btnFechaInicio = rootView.findViewById(R.id.btn_fechaInicio);
        btnFechaTermino = rootView.findViewById(R.id.btn_fechaTermino);
        btnHoraIngesta = rootView.findViewById(R.id.btn_horaIngesta);

        dao = new MedicamentoOperations(getContext());
        dao.open();

        btnTomarFoto.setOnClickListener(this);
        btnAddMed.setOnClickListener(this);
        btnFechaInicio.setOnClickListener(this);
        btnFechaTermino.setOnClickListener(this);
        btnHoraIngesta.setOnClickListener(this);
        //etHoraIngesta.setEnabled(false);
        etFechaInicio.setEnabled(false);
        etFechaTermino.setEnabled(false);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_Antes:
                        strAntesDespues = "Antes";
                        break;

                    case R.id.radio_Despues:
                        strAntesDespues = "Despues";
                        break;
                }
            }
        });

        etHoraIngesta.setText("00:00");

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addMed:
                medicamento = newMedicamento();
                break;

            case R.id.btn_tomarFotoMed:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_CODE );
                }
                break;

            case R.id.btn_fechaInicio:
                Miscellaneous.strDatePicker = "fechaInicio";
                getDatePicked();
                break;

            case R.id.btn_fechaTermino:
                Miscellaneous.strDatePicker = "fechaTermino";
                getDatePicked();
                break;

            case R.id.btn_horaIngesta:
                getHourPicked();
                break;
        }
    }

    public void getHourPicked() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "TimePicker");
    }

    public void getDatePicked() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            // Se comprime
            byteFoto = stream.toByteArray();

            ivFoto.setImageBitmap(bitmap);
        }
    }

    public String getDias() {
        String diasIngesta = "";

        if(cbLunes.isChecked()) {
            diasIngesta+="Lunes ";
        }
        if(cbMartes.isChecked()) {
            diasIngesta+="Martes ";
        }
        if(cbMiercoles.isChecked()) {
            diasIngesta+="Miercoles ";
        }
        if(cbJueves.isChecked()) {
            diasIngesta+="Jueves ";
        }
        if(cbViernes.isChecked()) {
            diasIngesta+="Viernes ";
        }
        if(cbSabado.isChecked()) {
            diasIngesta+="Sabado ";
        }
        if(cbDomingo.isChecked()) {
            diasIngesta+="Domingo";
        }

        return diasIngesta;
    }


    public Medicamento newMedicamento() {
        Log.d("FUNCION","newMedicamento()");
        Medicamento medicament = new Medicamento();
        strPeriodicidad = getDias();

        if (etNombre.getText().toString().length() == 0) {
            Toast.makeText(getContext(), "Ingresar nombre de medicamento", Toast.LENGTH_SHORT).show();
            return medicament;
        }

        if (etGramaje.getText().toString().length() == 0) {
            Toast.makeText(getContext(), "Ingresar gramaje", Toast.LENGTH_SHORT).show();
            return medicament;
        }

        if (etCantidadIngerir.getText().toString().length() == 0) {
            Toast.makeText(getContext(), "Ingresar cantidad a consumir", Toast.LENGTH_SHORT).show();
            return medicament;
        }

        if (strPeriodicidad.length() == 0) {
            Toast.makeText(getContext(), "Seleccionar días", Toast.LENGTH_SHORT).show();
            return medicament;
        }

        if (etCadaCuanto.getText().toString().length() == 0) {
            Toast.makeText(getContext(), "Ingresar cada cuánto se debe de consumir el medicamento", Toast.LENGTH_LONG).show();
            return medicament;
        }

        if (etFechaInicio.getText().length() == 0 ) {
            Toast.makeText(getContext(), "Seleccione fecha de Inicio", Toast.LENGTH_SHORT).show();
            return medicament;
        }

        if (etFechaTermino.getText().length() == 0 ) {
            Toast.makeText(getContext(), "Seleccione fecha de Termino", Toast.LENGTH_SHORT).show();
            return medicament;
        }

        if (strAntesDespues.length() == 0 ) {
            Toast.makeText(getContext(), "Seleccione si se consume antes o después de comer", Toast.LENGTH_SHORT).show();
            return medicament;
        }

        strNombre = etNombre.getText().toString();
        dGramaje = Double.parseDouble(etGramaje.getText().toString());
        iCantidad = Integer.parseInt(etCantidadIngerir.getText().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        // Tiene que ser ingresada en el formato de hh:mm:ss
        strHora = etHoraIngesta.getText().toString() + ":00";
        timeHora = Time.valueOf(strHora);

        String strFechaInicio = etFechaInicio.getText().toString();
        String strFechaTermino = etFechaTermino.getText().toString();

        try {
            dateInicio = dateFormat.parse(strFechaInicio);
            dateTermino = dateFormat.parse(strFechaTermino);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("FECHAS","dateInicio: " + dateInicio);
        Log.d("FECHAS","dateTermino: " + dateTermino);

        Log.d("FECHAS","dateTermino despues de dateInicio?? " + dateTermino.after(dateInicio));

        if(dateInicio.after(dateTermino)) {
            Toast.makeText(getContext(), "Fecha término tiene que preceder a la fecha de inicio",Toast.LENGTH_LONG).show();
            return medicament;
        }

        iCadaCuanto = Integer.parseInt(etCadaCuanto.getText().toString());
        String strAntesDespuesComer = strAntesDespues;
        if (strAntesDespuesComer.equals("Antes")) {
            bAntesDespuesComer = true;
        } else {
            bAntesDespuesComer = false;
        }

        if (bitmap == null) {
            ivFoto.buildDrawingCache();
            bitmap = (Bitmap) ivFoto.getDrawingCache();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);

            byteFoto = stream.toByteArray();
        }
        long l =0;
        medicament = new Medicamento(l, strNombre, dGramaje, iCantidad, strPeriodicidad, timeHora, iCadaCuanto, dateInicio, dateTermino, bAntesDespuesComer, byteFoto);
        long id = dao.addEvento(medicament);
        medicament.setId(id);

        FragmentoMedicamento fragmentoMedicamento = new FragmentoMedicamento();
        getFragmentManager().beginTransaction().replace(R.id.frameLayout_ActivitySalud, fragmentoMedicamento).commit();

        return medicament;
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

}
