package itesm.mx.saludintegral.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.CumpleanoOperations;
import itesm.mx.saludintegral.models.Cumpleano;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by FernandoDavid on 06/05/2018.
 */

public class AddCumpleanosFragment extends Fragment implements View.OnClickListener {

    EditText etNombre;
    EditText etFecha;
    EditText etTipo;
    EditText etTelefono;
    Button btnAgregarCumple;
    Button btnFecha;

    CumpleanoOperations dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_addcumpleanos, container, false);

        etNombre = rootView.findViewById(R.id.et_fragment_addcumpleanos_nombre);
        etFecha = rootView.findViewById(R.id.et_fragment_addcumpleanos_fecha);
        etTipo= rootView.findViewById(R.id.et_fragment_addcumpleanos_tipo);
        etTelefono = rootView.findViewById(R.id.et_fragment_addcumpleanos_teléfono);
        btnFecha = rootView.findViewById(R.id.btn_fecha);
        btnAgregarCumple = rootView.findViewById(R.id.btn_fragment_addcumpleanos_registrar);

        etTipo.setText(Miscellaneous.strTipo);
        dao = new CumpleanoOperations(getContext());
        dao.open();

        btnFecha.setOnClickListener(this);
        btnAgregarCumple.setOnClickListener(this);

        etFecha.setEnabled(false);
        etTipo.setEnabled(false);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fragment_addcumpleanos_registrar:
                newCumple();
                break;

            case R.id.btn_fecha:
                Miscellaneous.strDatePicker="fechaCumple";
                getDatePicked();
                break;
        }
    }

    public void newCumple() {
        String strNombre = etNombre.getText().toString();
        String strFecha = etFecha.getText().toString();
        String strTipo = etTipo.getText().toString();
        String strTel = etTelefono.getText().toString();

        if (strNombre.length() == 0) {
            Toast.makeText(getContext(), "Ingresar nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        if (strFecha.length() == 0) {
            Toast.makeText(getContext(), "Seleccione fecha", Toast.LENGTH_SHORT).show();
            return;
        }

        if (strTel.length() == 0) {
            Toast.makeText(getContext(), "Ingresar teléfono", Toast.LENGTH_SHORT).show();
            return;
        }

        Date dateFecha = Miscellaneous.getDateFromString(strFecha);
        long l = 0;
        Cumpleano cumpleano = new Cumpleano(l, strNombre, dateFecha, strTipo, strTel);
        long id = dao.addEvento(cumpleano);
        cumpleano.setId(id);
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

}
