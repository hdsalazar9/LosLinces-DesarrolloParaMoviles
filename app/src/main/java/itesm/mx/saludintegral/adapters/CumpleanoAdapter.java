package itesm.mx.saludintegral.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.models.Cumpleano;
import itesm.mx.saludintegral.models.Medicamento;

/**
 * Created by FernandoDavid on 05/05/2018.
 */

public class CumpleanoAdapter extends ArrayAdapter<Cumpleano> {
    ArrayList cumpleanos;

    public CumpleanoAdapter(Context context, ArrayList<Cumpleano> cumpleanos) {
        super(context,0,cumpleanos);
        this.cumpleanos = cumpleanos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cumpleano cumpleano = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cumpleano_row, parent,false);
        }
        TextView tvNombre = (TextView) convertView.findViewById(R.id.tv_cumpleano_row_nombre);
        TextView tvTelefono = (TextView) convertView.findViewById(R.id.tv_cumpleano_row_telefono);

        Log.d("Adapter name: ", cumpleano.getNombre());
        Log.d("Adapter tel: ", cumpleano.getTelefono());
        Log.d("Adapter Id: ", ""+cumpleano.getId());


        tvNombre.setText(cumpleano.getNombre());
        tvTelefono.setText(cumpleano.getTelefono());

        return convertView;
    }

    public void remove(Medicamento evento) {
        cumpleanos.remove(evento);
    }

}
