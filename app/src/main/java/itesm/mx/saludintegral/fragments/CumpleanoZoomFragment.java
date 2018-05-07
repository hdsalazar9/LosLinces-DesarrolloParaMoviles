package itesm.mx.saludintegral.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.CumpleanoOperations;
import itesm.mx.saludintegral.controllers.EventoOperations;
import itesm.mx.saludintegral.models.Cumpleano;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by FernandoDavid on 06/05/2018.
 */

public class CumpleanoZoomFragment extends Fragment implements View.OnClickListener{

    TextView tvNombre;
    TextView tvTelefono;
    TextView tvFecha;
    TextView tvTipo;
    Button btnEliminar;
    Button btnRegresar;
    OnResponseListener mCallback;
    CumpleanoOperations dao;

    Cumpleano cumpleano;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cumpleanozoom, container, false);

        tvNombre = rootView.findViewById(R.id.tv_fragmentozoom_nombre);
        tvTelefono = rootView.findViewById(R.id.tv_fragmentozoom_telefono);
        tvFecha = rootView.findViewById(R.id.tv_fragmentozoom_fecha);
        tvTipo = rootView.findViewById(R.id.tv_fragmentozoom_tipo);
        btnEliminar = rootView.findViewById(R.id.btn_eventozoom_eliminarEvento);
        btnRegresar= rootView.findViewById(R.id.btn_eventozoom_cerrar);

        dao = new CumpleanoOperations(getContext());
        dao.open();

        Bundle bundle = getArguments();
        cumpleano = new Cumpleano();
        if(bundle != null) {
            cumpleano = (Cumpleano) Parcels.unwrap(bundle.getParcelable("cumpleano"));
            Log.d("CumpleZoom","Nombre: " + cumpleano.getNombre());
            tvNombre.setText(cumpleano.getNombre());
            tvTelefono.setText(cumpleano.getTelefono());
            tvFecha.setText(Miscellaneous.getStringFromDate(cumpleano.getFecha()));
            tvTipo.setText(cumpleano.getTipo());
        }

        btnEliminar.setOnClickListener(this);
        btnRegresar.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_eventozoom_cerrar:
                mCallback.onResponse(1,null);
                break;

            case R.id.btn_eventozoom_eliminarEvento:
                removeCumpeleano();
                Toast.makeText(getContext(),"Cumpleaños Eliminado",Toast.LENGTH_SHORT).show();
                mCallback.onResponse(1,null);
                break;
        }
    }

    //Interfaz para que la actividad pueda responder al click en lista
    public interface OnResponseListener {
        public void onResponse(int tipo, Cumpleano cumpleano);
    }

    public void removeCumpeleano() {
        dao.deleteCumpleano(cumpleano.getNombre(),cumpleano.getFecha());
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        Activity activity;

        if(context instanceof  Activity){
            //Actividad respondera a la interface
            activity = (Activity) context;
            try{
                mCallback = (OnResponseListener) activity;
            }   catch(ClassCastException e){
                throw new ClassCastException(activity.toString() +
                        " must implement OnResponseListener.");
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
