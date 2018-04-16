package itesm.mx.saludintegral.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.MedicamentoOperations;
import itesm.mx.saludintegral.models.Medicamento;

import static android.app.Activity.RESULT_OK;

/**
 * Created by FernandoDavid on 15/04/2018.
 */

public class AddMedicamentoFragment extends Fragment implements View.OnClickListener{

    final static int REQUEST_CODE = 0;
    EditText etNombre;
    EditText etGramaje;
    EditText etCantidadIngerir;
    EditText etPeriodicidad;
    EditText etHoraIngesta;
    EditText etCadaCuanto;
    EditText etFechaInicio;
    EditText etFechaTermino;
    EditText etIngerirAntesDespuesComer;
    Button btnTomarFoto;
    Button btnAddMed;
    ImageView ivFoto;
    Bitmap bitmap;
    Medicamento medicamento;

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
        etPeriodicidad = rootView.findViewById(R.id.et_periodicidadMed);
        etHoraIngesta = rootView.findViewById(R.id.et_horaIngestaMed);
        etCadaCuanto = rootView.findViewById(R.id.et_cadaCuantoMed);
        etFechaInicio = rootView.findViewById(R.id.et_inicioMed);
        etFechaTermino = rootView.findViewById(R.id.et_terminoMed);
        etIngerirAntesDespuesComer = rootView.findViewById(R.id.et_antesDespuesComerMed);
        btnTomarFoto = rootView.findViewById(R.id.btn_tomarFotoMed);
        ivFoto = rootView.findViewById(R.id.iv_fotoMed);
        btnAddMed = rootView.findViewById(R.id.btn_addMed);

        dao = new MedicamentoOperations(getContext());
        dao.open();

        btnTomarFoto.setOnClickListener(this);
        btnAddMed.setOnClickListener(this);


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
        }
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

    public Medicamento newMedicamento() {
        strNombre = etNombre.getText().toString();
        dGramaje = Double.parseDouble(etGramaje.getText().toString());
        iCantidad = Integer.parseInt(etCantidadIngerir.getText().toString());
        strPeriodicidad = etPeriodicidad.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
        String strHora = etHoraIngesta.getText().toString();
        // Tiene que ser ingresada en el formato de hh:mm:ss
        timeHora = Time.valueOf(strHora);
        try {
            dateInicio = dateFormat.parse(etFechaInicio.getText().toString());
            dateTermino = dateFormat.parse(etFechaInicio.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        iCadaCuanto = Integer.parseInt(etCadaCuanto.getText().toString());
        String strAntesDespuesComer = etIngerirAntesDespuesComer.getText().toString();
        if (strAntesDespuesComer == "Antes") {
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
        Medicamento medicament = new Medicamento(l,strNombre,dGramaje,iCantidad,strPeriodicidad,timeHora,iCadaCuanto,dateInicio,dateTermino,bAntesDespuesComer,byteFoto);
        long id = dao.addEvento(medicament);
        medicament.setId(id);

        return medicament;
    }

}
