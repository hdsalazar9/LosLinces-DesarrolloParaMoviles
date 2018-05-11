package itesm.mx.saludintegral.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.models.Evento;
import itesm.mx.saludintegral.models.Medicamento;

/**
 * Created by FernandoDavid on 05/05/2018.
 */

public class EventoAdapter extends ArrayAdapter<Evento> {
    ArrayList eventos;

    public EventoAdapter (Context context, ArrayList<Evento> eventos) {
        super(context,0,eventos);
        this.eventos = eventos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Evento evento = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.evento_row, parent,false);
        }
        TextView tvNombre = (TextView) convertView.findViewById(R.id.tv_evento_row_nombreEvento);
        TextView tvDescripcion = (TextView) convertView.findViewById(R.id.tv_evento_row_descripcion);
        tvNombre.setText(evento.getName());
        tvDescripcion.setText(evento.getDescripcion());

        return convertView;
    }

    public void remove(Medicamento evento) {
        eventos.remove(evento);
    }

}
