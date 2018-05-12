package itesm.mx.saludintegral.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.EventoOperations;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.models.Medicamento;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by FernandoDavid on 06/05/2018.
 */

public class EventoZoomFragment extends Fragment implements View.OnClickListener{

    TextView tvNombre;
    TextView tvDesscripcion;
    TextView tvFecha;
    TextView tvTipo;
    Button btnEliminar, btnEliminarTodos;
    Button btnRegresar;
    OnResponseListener mCallback;
    EventoOperations dao;

    Evento evento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_eventozoom, container, false);

        tvNombre = rootView.findViewById(R.id.tv_fragmentozoom_nombre);
        tvDesscripcion = rootView.findViewById(R.id.tv_fragmentozoom_descripcion);
        tvFecha = rootView.findViewById(R.id.tv_fragmentozoom_fecha);
        tvTipo = rootView.findViewById(R.id.tv_fragmentozoom_tipo);
        btnEliminar = rootView.findViewById(R.id.btn_eventozoom_eliminarEvento);
        btnRegresar= rootView.findViewById(R.id.btn_eventozoom_cerrar);
        btnEliminarTodos=rootView.findViewById(R.id.btn_eventozoom_eliminarEventos);

        dao = new EventoOperations(getContext());
        dao.open();

        Bundle bundle = getArguments();
        evento = new Evento();
        if(bundle != null) {
            evento = (Evento) Parcels.unwrap(bundle.getParcelable("evento"));
            tvNombre.setText(evento.getName());
            tvDesscripcion.setText(evento.getDescripcion());
            tvFecha.setText(Miscellaneous.getStringFromDate(evento.getFecha()));
            tvTipo.setText(evento.getTipo());
        }

        btnEliminar.setOnClickListener(this);
        btnRegresar.setOnClickListener(this);
        btnEliminarTodos.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_eventozoom_cerrar:
                mCallback.onResponse(1,null);
                break;

            case R.id.btn_eventozoom_eliminarEvento:
                removeEvento();
                Toast.makeText(getContext(),"Evento Eliminado",Toast.LENGTH_SHORT).show();
                mCallback.onResponse(1,null);
                break;
            case R.id.btn_eventozoom_eliminarEventos:
                removeAllEventos();
                Toast.makeText(getContext(),"Eventos Eliminados",Toast.LENGTH_SHORT).show();
                mCallback.onResponse(1,null);
                break;
        }
    }

    //Interfaz para que la actividad pueda responder al click en lista
    public interface OnResponseListener {
        public void onResponse(int tipo, Evento evento);
    }

    public void removeEvento() {
        dao.deleteEvento(evento.getName(),evento.getFecha());
    }

    public void removeAllEventos(){
        dao.deleteEventosIguales(evento.getName(),evento.getIdEventos());
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
