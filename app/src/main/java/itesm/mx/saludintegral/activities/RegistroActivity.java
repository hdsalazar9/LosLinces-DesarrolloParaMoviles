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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private ImageButton btnTomarFoto;
    private Button btnFecha;
    private EditText etNombre;
    private EditText etApodo;
    private EditText etFechaDeNacimiento;
    private Spinner country, city;
   // private EditText etPais;
    private EditText etCiudad;
    private ImageView ivFoto;
    private Boolean bSpinEt;
    private String sCiduadActual;

    private InfoPersonalOperations database;


    //Si llega aqui, es que no se ha registrado y tendra que registrarse
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Miscellaneous.strTipo = Miscellaneous.tipos[11];

        database = new InfoPersonalOperations(getApplicationContext());
        database.open();

        btnContinue = (Button) findViewById(R.id.btn_activity_registro_continuar);
        btnTomarFoto = (ImageButton) findViewById(R.id.btn_activity_registro_TomarFoto);
        btnFecha = (Button) findViewById(R.id.btn_registro_Fecha);
        etNombre = (EditText) findViewById(R.id.et_activity_registro_nombre);
        etApodo = (EditText) findViewById(R.id.et_activity_registro_apodo);
        etFechaDeNacimiento = (EditText) findViewById(R.id.et_activity_registro_fecha);
       // etPais = (EditText) findViewById(R.id.et_activity_registro_pais);
        etCiudad = (EditText) findViewById(R.id.et_activity_registro_ciudad);
        ivFoto = (ImageView) findViewById(R.id.iv_activity_registro_foto);
        country = (Spinner) findViewById(R.id.spinnerCountry);
        bSpinEt=true;
        city=findViewById(R.id.spinnerCity);
        //Ingresa datos al spinner
        /*Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }*/
        List<String> countries = Arrays.asList(getResources().getStringArray(R.array.paises));
        Collections.sort(countries);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTextSize(18);
                ((TextView) v).setTextColor(
                        getResources().getColorStateList(R.color.caldroid_black)
                );

                return v;
            }
        };
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(dataAdapter);

        /*for (int i=0; i<countries.size(); i++){
            if(countries.get(i).equals("México")||countries.get(i).equals("Mexico")){
                country.setSelection(i);
                iPais=i;
            }
        }*/
        country.setSelection(158);
        ArrayAdapter<String> cityAdapter=setSpinnerData(country.getSelectedItemPosition());
        if(cityAdapter!=null){
            city.setAdapter(cityAdapter);
            etCiudad.setVisibility(View.GONE);
            bSpinEt=true;
            city.setSelection(0);
            sCiduadActual=city.getSelectedItem().toString();
        }
        else{
            city.setVisibility(View.GONE);
            bSpinEt=false;
            city.setAdapter(null);

        }
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /* if(158==position|| 9==position|| 36==position|| 178==position|| 23 ==position||64 ==position||44 ==position||41==position|| 82==position ){

                }*/
                ArrayAdapter<String> cityAdapter=setSpinnerData(country.getSelectedItemPosition());
                if(cityAdapter!=null){
                    city.setVisibility(View.VISIBLE);
                    city.setAdapter(cityAdapter);
                    etCiudad.setVisibility(View.GONE);
                    bSpinEt=true;
                }
                else{
                    etCiudad.setVisibility(View.VISIBLE);
                    city.setVisibility(View.GONE);
                    bSpinEt=false;
                    if(city.getAdapter()!=null)
                        etCiudad.setText(city.getSelectedItem().toString());
                    else
                        etCiudad.setText("");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                else
                {
                    Toast.makeText(getApplicationContext(),"No se detectó cámara",Toast.LENGTH_SHORT).show();
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
                /*if(etPais.getText().toString().equals("")){
                    Toast.makeText(this, "Favor de registrar país", Toast.LENGTH_SHORT).show();
                    break;
                }*/
                if(etCiudad.getText().toString().equals("")&&!bSpinEt){
                    Toast.makeText(this, "Favor de registrar ciudad", Toast.LENGTH_SHORT).show();
                    break;
                }
                String sCiudad;
                if(bSpinEt){
                    sCiudad=city.getSelectedItem().toString();
                }
                else{
                    sCiudad=etCiudad.getText().toString();
                }
                ////Crear objeto infopersonal y guardarlo en base de datos
                InfoPersonal infoPersonal = new InfoPersonal();

                infoPersonal.setNombre(etNombre.getText().toString());
                infoPersonal.setApodo(etApodo.getText().toString());
                infoPersonal.setCiudad(sCiudad);
                infoPersonal.setPais(country.getSelectedItem().toString());
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
            matrix.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            ivFoto.setImageBitmap(bitmap);
            //ivFoto.getLayoutParams().width = 200;
            //ivFoto.getLayoutParams().height = 200;

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

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Terminar el registro",Toast.LENGTH_SHORT).show();
    }

    ArrayAdapter<String> setSpinnerData(int iPais){
        String s[];//new ArrayList<String>();//getResources().getStringArray(R.array.estados_mexico);
        switch(iPais){
            case 153:   //Mexico
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_mexico) );
                s=getResources().getStringArray(R.array.estados_mexico);
            break;
            case 10:     //Argentina
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_argentina) );
                s=getResources().getStringArray(R.array.estados_argentina);
                break;
            case 37:      //Canadá
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_canada) );
                s=getResources().getStringArray(R.array.estados_canada);
                break;
            case 178:     //Perú
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_peru) );
                s=getResources().getStringArray(R.array.estados_peru);
                break;
            case 25:      //Bolivia
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_bolivia) );
                s=getResources().getStringArray(R.array.estados_bolivia);
                break;
            case 66:        //Estados Unidos
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_estados_unidos) );
                s=getResources().getStringArray(R.array.estados_estados_unidos);
                break;
            case 46:        //Colombia
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_colombia) );
                s=getResources().getStringArray(R.array.estados_colombia);
                break;
            case 42:        //Chile
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_chile) );
                s=getResources().getStringArray(R.array.estados_chile);
                break;
            case 83:        //Guatemala
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_guatemala) );
                s=getResources().getStringArray(R.array.estados_guatemala);
                break;
            default:
                return null;
        }
        return new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,s){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                ((TextView) v).setTextSize(18);
                ((TextView) v).setTextColor(
                        getResources().getColorStateList(R.color.caldroid_black)
                );

                return v;
            }
        };
    }


}
