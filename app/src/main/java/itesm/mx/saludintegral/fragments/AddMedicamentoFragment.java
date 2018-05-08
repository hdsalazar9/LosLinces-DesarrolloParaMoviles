package itesm.mx.saludintegral.fragments;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
import itesm.mx.saludintegral.util.Receiver;

import static java.lang.Math.toIntExact;


import static android.app.Activity.RESULT_OK;
import static java.lang.String.valueOf;

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

    OnResponseAgregar mCallback;


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


    private PendingIntent pendingIntent;


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

        btnTomarFoto = rootView.findViewById(R.id.btn_tomarFotoMed);
        ivFoto = rootView.findViewById(R.id.iv_fotoMed);
        btnAddMed = rootView.findViewById(R.id.btn_addMed);
        radioGroup = rootView.findViewById(R.id.radioGroup);
        btnFechaInicio = rootView.findViewById(R.id.btn_fechaInicio);
        btnHoraIngesta = rootView.findViewById(R.id.btn_horaIngesta);


        dao = new MedicamentoOperations(getContext());
        dao.open();

        btnTomarFoto.setOnClickListener(this);
        btnAddMed.setOnClickListener(this);

        btnFechaInicio.setOnClickListener(this);
        //btnFechaTermino.setOnClickListener(this);
        btnHoraIngesta.setOnClickListener(this);
        etHoraIngesta.setEnabled(false);
        etFechaInicio.setEnabled(false);
        //etFechaTermino.setEnabled(false);

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
                Intent alarmIntent = new Intent(getContext(), Receiver.class);
                alarmIntent.putExtra("medicina", medicamento.getNombre());
                alarmIntent.putExtra("whereFrom", "AddMedicamento");
                alarmIntent.putExtra("id", ((int) medicamento.getId()));
                pendingIntent = PendingIntent.getBroadcast(getContext(), ((int) medicamento.getId()), alarmIntent, 0);
                start(medicamento.getCadaCuanto());
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

            case R.id.btn_horaIngesta:
                getHourPicked();
                break;
        }
    }

    public void start(int horas) {

        AlarmManager manager = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
        int interval = 1000*60*60*horas;

        Calendar calendar = Calendar.getInstance();
        Calendar ahora = Calendar.getInstance();
        ahora.setTimeInMillis(System.currentTimeMillis());

        String time [] = etHoraIngesta.getText().toString().split(":");
        String inicio [] = etFechaInicio.getText().toString().split("-");

        calendar.set(Calendar.YEAR, Integer.parseInt(inicio[2]));
        calendar.set(Calendar.MONTH, Integer.parseInt(inicio[1]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(inicio[0]));
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));


        Long days = (calendar.get(Calendar.DAY_OF_YEAR) - ahora.get(Calendar.DAY_OF_YEAR)) * 1000*60*60*24L;
        Long hours = (calendar.get(Calendar.HOUR_OF_DAY) - ahora.get(Calendar.HOUR_OF_DAY)) * 1000*60*60L;
        Long minutes = (calendar.get(Calendar.MINUTE) - ahora.get(Calendar.MINUTE)) * 1000*60L;

        Long scheduled = ahora.getTimeInMillis() + days + hours + minutes;

         Calendar agendado = Calendar.getInstance();
         agendado.setTimeInMillis(scheduled);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, agendado.getTimeInMillis(), interval, pendingIntent);
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

            //Girar foto
            Matrix matrix = new Matrix();
            matrix.postRotate(270);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

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
        strPeriodicidad = "";

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

        /*
        if (strPeriodicidad.length() == 0) {
            Toast.makeText(getContext(), "Seleccionar días", Toast.LENGTH_SHORT).show();
            return medicament;
        }
        */

        if (etCadaCuanto.getText().toString().length() == 0) {
            Toast.makeText(getContext(), "Ingresar cada cuánto se debe de consumir el medicamento", Toast.LENGTH_LONG).show();
            return medicament;
        }

        if (etFechaInicio.getText().length() == 0 ) {
            Toast.makeText(getContext(), "Seleccione fecha de Inicio", Toast.LENGTH_SHORT).show();
            return medicament;
        }

        /*
        if (etFechaTermino.getText().length() == 0 ) {
            Toast.makeText(getContext(), "Seleccione fecha de Termino", Toast.LENGTH_SHORT).show();
            return medicament;
        }
        */

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
        //String strFechaTermino = etFechaTermino.getText().toString();

        try {
            dateInicio = dateFormat.parse(strFechaInicio);
            dateTermino = dateFormat.parse(strFechaInicio);
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
        mCallback.onResponseAgregar();
        return medicament;
    }

    //Interfaz para que la actividad pueda responder al click en lista
    public interface OnResponseAgregar {
        public void onResponseAgregar();
    }


    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        Activity activity;

        if(context instanceof  Activity){
            //Actividad respondera a la interface
            activity = (Activity) context;
            try{
                mCallback = (OnResponseAgregar) activity;
            }   catch(ClassCastException e){
                throw new ClassCastException(activity.toString() +
                        " must implement OnResponseTomar.");
            }
        }

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
