package itesm.mx.saludintegral.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.ContactoEmergenciaOperations;
import itesm.mx.saludintegral.models.ContactoEmergencia;

/**
 * Created by Héctor on 5/7/2018.
 */

public class RegistroContactoFragment extends Fragment implements View.OnClickListener{

    ContactoEmergenciaOperations conEmeOp;
    Button btnAdd;
    EditText etNombre;
    EditText etTelefono;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_registro_contacto, container, false);

        btnAdd = rootView.findViewById(R.id.btn_contacto_add);
        etNombre = rootView.findViewById(R.id.et_contacto_registro_nombre);
        etTelefono = rootView.findViewById(R.id.et_contacto_registro_telefono);

        btnAdd.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        //Validar que no esten vacios los campos
        if(etNombre.getText().toString().equals("")){
            Toast t1 = Toast.makeText(getContext(),"Favor de registrar nombre...", Toast.LENGTH_SHORT);
            t1.show();
            return;
        }
        if(etTelefono.getText().toString().equals("")){
            Toast t1 = Toast.makeText(getContext(),"Favor de registrar telefono...", Toast.LENGTH_SHORT);
            t1.show();
            return;
        }

        conEmeOp = new ContactoEmergenciaOperations(getContext());
        conEmeOp.open();
        ContactoEmergencia contactoEmergencia = new ContactoEmergencia();
        contactoEmergencia.setNombre(etNombre.getText().toString());
        contactoEmergencia.setTelefono(etTelefono.getText().toString());

        conEmeOp.addEvento(contactoEmergencia);
        Toast t1 = Toast.makeText(getContext(),"Registro completo!", Toast.LENGTH_SHORT);
        t1.show();
        getActivity().onBackPressed();
    }


    @Override
    public void onResume(){
        conEmeOp.open();
        super.onResume();
    }
    @Override
    public void onPause(){
        conEmeOp.close();
        super.onPause();
    }
    @Override
    public void onDetach(){
        conEmeOp.close();
        super.onDetach();
    }
}
