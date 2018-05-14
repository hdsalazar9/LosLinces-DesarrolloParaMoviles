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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    Spinner country, city;
    TextView tvFecha;
    Button btnEdit;
    ImageButton btnFoto;
    ImageView ivFoto;
    Boolean bEditable, bSpinEt, bFirstTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil_inicial, container, false);

        bEditable = false;
        etNombre = (EditText) rootView.findViewById(R.id.et_perfil_nombre);
        etApellido = (EditText) rootView.findViewById(R.id.et_perfil_apellido);
        etCiudad = (EditText) rootView.findViewById(R.id.et_perfil_ciudad);
        //etPais = (EditText) rootView.findViewById(R.id.et_perfil_pais);
        country=rootView.findViewById(R.id.spinnerCountry);
        tvFecha = (TextView) rootView.findViewById(R.id.tv_perfil_fechanacimiento);
        ivFoto = (ImageView) rootView.findViewById(R.id.iv_perfil_foto);
        btnEdit = (Button) rootView.findViewById(R.id.btn_perfil_editar);
        btnFoto = (ImageButton) rootView.findViewById(R.id.btn_perfil_foto);
        city=rootView.findViewById(R.id.spinnerCity);
        ipo = new InfoPersonalOperations(getActivity().getApplicationContext());
        ipo.open();
        info = ipo.getAllProducts();
        bSpinEt=false;
        bFirstTime=true;
        //Agrega paises al spinner
        List<String> countries = Arrays.asList(getResources().getStringArray(R.array.paises));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, countries){
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
        int iPais=0;
        for (int i=0; i<countries.size(); i++){
            if(countries.get(i).equals(info.getPais())||countries.get(i).equals(info.getPais())){
                country.setSelection(i);
                iPais=i;
                //Toast.makeText(getContext(), "Index: "+String.valueOf(i), Toast.LENGTH_SHORT).show();
            }
        }

        ArrayAdapter<String> cityAdapter=setSpinnerData(iPais);
        if(cityAdapter!=null){
            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            city.setAdapter(cityAdapter);
            city.setVisibility(View.GONE);
            bSpinEt=true;
            setCiudad();
        }
        else{
            city.setVisibility(View.GONE);
            bSpinEt=false;
            city.setAdapter(null);

        }

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(getContext(), "Index: "+String.valueOf(position), Toast.LENGTH_SHORT).show();
                ArrayAdapter<String> cityAdapter=setSpinnerData(country.getSelectedItemPosition());
                if(cityAdapter!=null){
                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    city.setVisibility(View.VISIBLE);
                    city.setAdapter(cityAdapter);
                    etCiudad.setVisibility(View.GONE);
                    bSpinEt=true;
                    if(bFirstTime)
                        setCiudad();
                    bFirstTime=false;
                }
                else{
                    etCiudad.setVisibility(View.VISIBLE);
                    city.setVisibility(View.GONE);
                    bSpinEt=false;
                    if(city.getAdapter()!=null)
                        etCiudad.setText(city.getSelectedItem().toString());
                    else
                        etCiudad.setText(info.getCiudad());
                    bFirstTime=false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnFoto.setVisibility(View.INVISIBLE);
        etNombre.setText(info.getNombre());
        etApellido.setText(info.getApodo());
        etCiudad.setText(info.getCiudad());
        //etPais.setText(info.getPais());
        tvFecha.setText(Miscellaneous.getStringFromDate(info.getFechaNacimiento()));

        etNombre.setEnabled(false);
        etApellido.setEnabled(false);
        etCiudad.setEnabled(false);
        country.setEnabled(false);
        city.setEnabled(false);
        city.setVisibility(View.GONE);
        Bitmap bmp = BitmapFactory.decodeByteArray(info.getFoto(), 0, info.getFoto().length);
        ivFoto.setImageBitmap(Bitmap.createScaledBitmap(bmp, bmp.getWidth(),
                bmp.getHeight(), false));

        btnEdit.setOnClickListener(this);
        btnFoto.setOnClickListener(this);

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
                else
                {
                    Toast.makeText(getContext(),"No se detectó cámara",Toast.LENGTH_SHORT).show();
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
                if(etCiudad.getText().toString().equals("")&&!bSpinEt){
                    Toast.makeText(getContext(), "Favor de registrar ciudad", Toast.LENGTH_SHORT).show();
                    break;
                }
                String sCiudad;
                if(bSpinEt){
                    sCiudad=city.getSelectedItem().toString();
                }
                else{
                    sCiudad=etCiudad.getText().toString();
                }
                info.setNombre(etNombre.getText().toString());
                info.setApodo(etApellido.getText().toString());
                info.setCiudad(sCiudad);
                //info.setPais(etPais.getText().toString());
                info.setPais(country.getSelectedItem().toString());
                info.setFoto(byteArray);

                if (bEditable)
                {
                    if (etNombre.getText().toString().equals("")) { //Si esta vacio el campo de nombre
                        Toast.makeText(getContext(), "Favor de registrar nombre", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etApellido.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Favor de registrar apodo", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etCiudad.getText().toString().equals("")&&!bSpinEt) {
                        Toast.makeText(getContext(), "Favor de registrar estado", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String sCiudad2;
                    if(bSpinEt){
                        sCiudad2=city.getSelectedItem().toString();
                    }
                    else{
                        sCiudad2=etCiudad.getText().toString();
                    }

                    info.setNombre(etNombre.getText().toString());
                    info.setApodo(etApellido.getText().toString());
                    info.setCiudad(sCiudad2);
                    info.setFoto(byteArray);

                    long id = ipo.addEvento(info);
                    Toast.makeText(getActivity().getApplicationContext(), "Editado satisfactoriamente", Toast.LENGTH_SHORT).show();
                    bEditable = false;
                    btnEdit.setText(getResources().getString(R.string.seccion_emepzaredicion));
                    btnFoto.setVisibility(View.INVISIBLE);
                    etNombre.setEnabled(false);
                    etApellido.setEnabled(false);
                    etCiudad.setEnabled(false);
                    country.setEnabled(false);
                    city.setEnabled(false);
                    bEditable = false;
                }
                else
                {
                    if(bSpinEt){
                        city.setVisibility(View.VISIBLE);
                        etCiudad.setVisibility(View.GONE);
                    }
                    else{
                        etCiudad.setVisibility(View.VISIBLE);
                        city.setVisibility(View.GONE);
                    }
                    btnFoto.setVisibility(View.VISIBLE);
                    etNombre.setEnabled(true);
                    etApellido.setEnabled(true);
                    etCiudad.setEnabled(true);
                    country.setEnabled(true);
                    bEditable = true;
                    btnEdit.setText(getResources().getString(R.string.editar));
                    country.setEnabled(true);
                    city.setEnabled(true);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        int iSize = 600;

        //Se tomo exisotamente la foto, guardarla
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");
            //Girar foto 270 grados
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            ivFoto.setImageBitmap(bitmap);
            ivFoto.getLayoutParams().width = iSize;
            ivFoto.getLayoutParams().height = iSize;

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

    ArrayAdapter<String> setSpinnerData(int iPais){
        List<String> s;//new ArrayList<String>();//getResources().getStringArray(R.array.estados_mexico);
        switch(iPais){
            case 153:   //Mexico
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_mexico) );
                s=Arrays.asList(getResources().getStringArray(R.array.estados_mexico));
            break;
            case 10:     //Argentina
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_argentina) );
                s=Arrays.asList(getResources().getStringArray(R.array.estados_argentina));
            break;
            case 37:      //Canadá
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_canada) );
                s=Arrays.asList(getResources().getStringArray(R.array.estados_canada));
            break;
            case 178:     //Perú
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_peru) );
                s=Arrays.asList(getResources().getStringArray(R.array.estados_peru));
            break;
            case 25:      //Bolivia
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_bolivia) );
                s=Arrays.asList(getResources().getStringArray(R.array.estados_bolivia));
            break;
            case 66:        //Estados Unidos
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_estados_unidos) );
                s=Arrays.asList(getResources().getStringArray(R.array.estados_estados_unidos));
            break;
            case 46:        //Colombia
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_colombia) );
                s=Arrays.asList(getResources().getStringArray(R.array.estados_colombia));
            break;
            case 42:        //Chile
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_chile) );
                s=Arrays.asList(getResources().getStringArray(R.array.estados_chile));
            break;
            case 83:        //Guatemala
                //return new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.estados_guatemala) );
                s=Arrays.asList(getResources().getStringArray(R.array.estados_guatemala));
            break;
            default:
                return null;
        }
        return new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,s){
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
    public void setCiudad(){
            for (int i=0; i<city.getAdapter().getCount();i++){
                if (city.getItemAtPosition(i).equals(info.getCiudad())) {
                    city.setSelection(i);
                }
            }
    }
}
