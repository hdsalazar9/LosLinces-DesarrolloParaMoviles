package itesm.mx.saludintegral.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.InfoPersonalOperations;
import itesm.mx.saludintegral.models.InfoPersonal;
import itesm.mx.saludintegral.util.Miscellaneous;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Héctor on 5/6/2018.
 */

public class PerfilInicialFragment extends Fragment implements  View.OnClickListener{

    //Para guardar foto
    public static final int REQUEST_CODE = 0;
    private Bitmap bitmap;
    private byte[] byteArray;

    InfoPersonal info;
    InfoPersonalOperations ipo;
    EditText etNombre;
    EditText etApellido;
    EditText etCiudad;
    //EditText etPais;
    Spinner country;
    TextView tvFecha;
    Button btnEdit;
    Button btnBitacoraEventos;
    Button btnHistorialMedicamentos;
    Button btnFoto;
    Button btnMonitoreo;
    ImageView ivFoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil_inicial, container, false);

        etNombre = (EditText) rootView.findViewById(R.id.et_perfil_nombre);
        etApellido = (EditText) rootView.findViewById(R.id.et_perfil_apellido);
        etCiudad = (EditText) rootView.findViewById(R.id.et_perfil_ciudad);
        //etPais = (EditText) rootView.findViewById(R.id.et_perfil_pais);
        country=rootView.findViewById(R.id.spinnerCountry);
        tvFecha = (TextView) rootView.findViewById(R.id.tv_perfil_fechanacimiento);
        ivFoto = (ImageView) rootView.findViewById(R.id.iv_perfil_foto);
        btnEdit = (Button) rootView.findViewById(R.id.btn_perfil_editar);
        btnFoto = (Button) rootView.findViewById(R.id.btn_perfil_foto);
        btnMonitoreo = (Button) rootView.findViewById(R.id.btn_perfil_monitoreo);
        btnBitacoraEventos = (Button) rootView.findViewById(R.id.btn_perfil_bitacoraeventos);
        btnHistorialMedicamentos = (Button) rootView.findViewById(R.id.btn_perfil_historialmedicamentos);

        ipo = new InfoPersonalOperations(getActivity().getApplicationContext());
        ipo.open();
        info = ipo.getAllProducts();

        //Agrega paises al spinner
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, countries);
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(dataAdapter);
        for (int i=0; i<countries.size(); i++){
            if(countries.get(i).equals(info.getPais())||countries.get(i).equals(info.getPais())){
                country.setSelection(i);
            }
        }


        etNombre.setText(info.getNombre());
        etApellido.setText(info.getApodo());
        etCiudad.setText(info.getCiudad());
        //etPais.setText(info.getPais());
        tvFecha.setText(Miscellaneous.getStringFromDate(info.getFechaNacimiento()));

        Bitmap bmp = BitmapFactory.decodeByteArray(info.getFoto(), 0, info.getFoto().length);
        ivFoto.setImageBitmap(Bitmap.createScaledBitmap(bmp, bmp.getWidth(),
                bmp.getHeight(), false));

        btnEdit.setOnClickListener(this);
        btnBitacoraEventos.setOnClickListener(this);
        btnHistorialMedicamentos.setOnClickListener(this);
        btnFoto.setOnClickListener(this);
        btnMonitoreo.setOnClickListener(this);

        //Get imageview to byte array
        bitmap = ((BitmapDrawable) ivFoto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byteArray = baos.toByteArray();

        return rootView;
    }

    @Override
    public void onClick(View v){

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        switch (v.getId()){
            case R.id.btn_perfil_foto:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Validar camara celular
                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_CODE);
                }
                break;
            case R.id.btn_perfil_editar:
                //Editar
                if(etNombre.getText().toString().equals("")) { //Si esta vacio el campo de nombre
                    Toast.makeText(getContext(), "Favor de registrar nombre", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(etApellido.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Favor de registrar apodo", Toast.LENGTH_SHORT).show();
                    break;
                }
                /*if(etPais.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Favor de registrar país", Toast.LENGTH_SHORT).show();
                    break;
                }*/
                if(etCiudad.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Favor de registrar ciudad", Toast.LENGTH_SHORT).show();
                    break;
                }

                info.setNombre(etNombre.getText().toString());
                info.setApodo(etApellido.getText().toString());
                info.setCiudad(etCiudad.getText().toString());
                //info.setPais(etPais.getText().toString());
                info.setPais(country.getSelectedItem().toString());
                info.setFoto(byteArray);

                long id = ipo.addEvento(info);
                Toast.makeText(getActivity().getApplicationContext(), "Editado satisfactoriamente", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_perfil_bitacoraeventos:
                BitacoraEventoFragment bitacoraEventoFragment = new BitacoraEventoFragment();
                transaction.replace(R.id.frameLayout_perfilActivity,bitacoraEventoFragment)
                        .addToBackStack(null).commit();
                break;
            case R.id.btn_perfil_historialmedicamentos:
                HistorialMedicFragment historialMedicFragment = new HistorialMedicFragment();
                transaction.replace(R.id.frameLayout_perfilActivity,historialMedicFragment)
                        .addToBackStack(null).commit();
                break;
            case R.id.btn_perfil_monitoreo:
                PerfilMonitoreoFragment perfilMonitoreoFragment = new PerfilMonitoreoFragment();
                transaction.replace(R.id.frameLayout_perfilActivity,perfilMonitoreoFragment)
                        .addToBackStack(null).commit();
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

    @Override
    public void onResume(){
        ipo.open();
        super.onResume();
    }
    @Override
    public void onPause(){
        ipo.close();
        super.onPause();
    }
    @Override
    public void onDetach(){
        ipo.close();
        super.onDetach();
    }
    @Override
    public void onStop(){
        ipo.close();
        super.onStop();
    }
}
