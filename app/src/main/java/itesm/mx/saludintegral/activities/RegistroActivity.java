package itesm.mx.saludintegral.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.InfoPersonalOperations;
import itesm.mx.saludintegral.fragments.DatePickerFragment;
import itesm.mx.saludintegral.models.InfoPersonal;
import itesm.mx.saludintegral.util.Miscellaneous;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {


    //Para guardar la foto
    public static final int REQUEST_CODE = 0;
    private Bitmap bitmap;
    private byte[] byteArray;

    private Button btnContinue;
    private Button btnTomarFoto;
    private Button btnFecha;
    private EditText etNombre;
    private EditText etApodo;
    private EditText etFechaDeNacimiento;
    private EditText etPais;
    private EditText etCiudad;
    private ImageView ivFoto;

    private InfoPersonalOperations database;


    //Si llega aqui, es que no se ha registrado y tendra que registrarse
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        database = new InfoPersonalOperations(getApplicationContext());
        database.open();

        btnContinue = (Button) findViewById(R.id.btn_activity_registro_continuar);
        btnTomarFoto = (Button) findViewById(R.id.btn_activity_registro_TomarFoto);
        btnFecha = (Button) findViewById(R.id.btn_registro_Fecha);
        etNombre = (EditText) findViewById(R.id.et_activity_registro_nombre);
        etApodo = (EditText) findViewById(R.id.et_activity_registro_apodo);
        etFechaDeNacimiento = (EditText) findViewById(R.id.et_activity_registro_fecha);
        etPais = (EditText) findViewById(R.id.et_activity_registro_pais);
        etCiudad = (EditText) findViewById(R.id.et_activity_registro_ciudad);
        ivFoto = (ImageView) findViewById(R.id.iv_activity_registro_foto);

        if(savedInstanceState != null){
            byteArray = savedInstanceState.getByteArray("picture");
        }

        //Get imageview to byte array
        bitmap = ((BitmapDrawable) ivFoto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byteArray = baos.toByteArray();

        etFechaDeNacimiento.setEnabled(false);
        btnFecha.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        btnTomarFoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registro_Fecha:
                Miscellaneous.strDatePicker = "fechaNacimiento";
                getDatePicked();
                break;

            case R.id.btn_activity_registro_TomarFoto:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Validar camara celular
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_CODE);
                }
                break;

            case R.id.btn_activity_registro_continuar:
                if(etNombre.getText().toString().equals("")) { //Si esta vacio el campo de nombre
                    Toast.makeText(this, "Favor de registrar nombre", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(etApodo.getText().toString().equals("")){
                    Toast.makeText(this, "Favor de registrar apodo", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(etFechaDeNacimiento.getText().toString().equals("")){
                    Toast.makeText(this, "Favor de registrar fecha de naciemiento", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(etPais.getText().toString().equals("")){
                    Toast.makeText(this, "Favor de registrar país", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(etCiudad.getText().toString().equals("")){
                    Toast.makeText(this, "Favor de registrar ciudad", Toast.LENGTH_SHORT).show();
                    break;
                }

                ////Crear objeto infopersonal y guardarlo en base de datos
                InfoPersonal infoPersonal = new InfoPersonal();

                infoPersonal.setNombre(etNombre.getText().toString());
                infoPersonal.setApodo(etApodo.getText().toString());
                infoPersonal.setCiudad(etCiudad.getText().toString());
                infoPersonal.setPais(etPais.getText().toString());
                infoPersonal.setFoto(byteArray);

                //Obtener la fecha desde lo escrito por el usuario
                Date date = Miscellaneous.getDateFromString(etFechaDeNacimiento.getText().toString());
                infoPersonal.setFechaNacimiento(date);

                long id = database.addEvento(infoPersonal);
                Toast.makeText(this, "Registrado satisfactoriamente!", Toast.LENGTH_SHORT).show();
                Log.d("Registro activity:", infoPersonal.toString());
                Intent i = new Intent(this, MainMenu.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        //Se tomo exisotamente la foto, guardarla
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");
            //Girar foto 270 grados
            Matrix matrix = new Matrix();
            matrix.postRotate(270);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            ivFoto.setImageBitmap(bitmap);
            ivFoto.getLayoutParams().width = 200;
            ivFoto.getLayoutParams().height = 200;

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        }
    }

    public void getDatePicked() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //Conservar imagen
        outState.putByteArray("picture",byteArray);
    }

    @Override
    public void onResume(){
        database.open();
        super.onResume();
    }
    @Override
    public void onPause(){
        database.close();
        super.onPause();
    }
}
