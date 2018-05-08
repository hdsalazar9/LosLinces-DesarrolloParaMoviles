package itesm.mx.saludintegral.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by Héctor on 5/7/2018.
 */

public class BitacoraEventoAdapter extends ArrayAdapter<Evento> {

    public BitacoraEventoAdapter(Context context, ArrayList<Evento> items) {
        super(context,0,items);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bitacora_evento_row, parent, false);
        }

        Evento evento = getItem(position);

        TextView tvNombre = convertView.findViewById(R.id.tv_bitacora_nombre);
        TextView tvTipo = convertView.findViewById(R.id.tv_bitacora_tipo);
        TextView tvFecha = convertView.findViewById(R.id.tv_bitacora_fecha);
        TextView tvDescrip = convertView.findViewById(R.id.tv_bitacora_descripcion);

        tvNombre.setText(evento.getName());
        tvTipo.setText(evento.getTipo());
        tvFecha.setText(Miscellaneous.getStringFromDate(evento.getFecha()));
        tvDescrip.setText(evento.getDescripcion());

        return convertView;
    }
}
