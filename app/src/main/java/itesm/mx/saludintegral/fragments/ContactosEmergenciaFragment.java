package itesm.mx.saludintegral.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.adapters.ContactoEmeAdapter;
import itesm.mx.saludintegral.controllers.ContactoEmergenciaOperations;
import itesm.mx.saludintegral.models.ContactoEmergencia;

/**
 * Created by Héctor on 5/7/2018.
 */

public class ContactosEmergenciaFragment extends Fragment{

    ContactoEmergenciaOperations conEmeOp;

    ArrayList<ContactoEmergencia> listContacto;
    ArrayAdapter<ContactoEmergencia> contactoAdapter;

    ListView lvContactos;
    Button btnAddCont;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contactos_emergencia, container, false);

        lvContactos = rootView.findViewById(R.id.lv_contactos_emergencia);
        btnAddCont = rootView.findViewById(R.id.btn_addContactoEm);

        conEmeOp = new ContactoEmergenciaOperations(getContext());
        conEmeOp.open();

        listContacto = conEmeOp.getAllProducts();
        contactoAdapter = new ContactoEmeAdapter(getContext(),listContacto);
        lvContactos.setAdapter(contactoAdapter);

        lvContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                System.out.println("SI ESTAS PICANDO BIEN DUDE");
                Uri number = Uri.parse("tel:123456789");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });

        btnAddCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistroContactoFragment registroFragment = new RegistroContactoFragment();
                getFragmentManager().beginTransaction().replace(R.id.frameLayout_perfilActivity, registroFragment).
                        addToBackStack(null).commit();
            }
        });

        return rootView;
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
