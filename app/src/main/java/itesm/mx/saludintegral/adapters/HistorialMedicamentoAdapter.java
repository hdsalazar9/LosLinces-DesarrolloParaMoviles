package itesm.mx.saludintegral.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import itesm.mx.saludintegral.R;

/**
 * Created by Héctor on 5/7/2018.
 */

public class HistorialMedicamentoAdapter extends ArrayAdapter<HistorialMedicamentoItem> {

    public HistorialMedicamentoAdapter(Context context, ArrayList<HistorialMedicamentoItem> items){
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        HistorialMedicamentoItem hmItem = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.historial_medicamento_row, parent, false);
        }

        TextView tvNombreMed = convertView.findViewById(R.id.tv_row_historial_nombreM);
        TextView tvFechaMed = convertView.findViewById(R.id.tv_row_historial_fecha);
        TextView tvTomadoTiempo = convertView.findViewById(R.id.tv_row_historial_Atiempo);

        tvNombreMed.setText(hmItem.getNombreMed());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        tvFechaMed.setText(dateFormat.format(hmItem.getFechaTomado()));

        tvTomadoTiempo.setText(hmItem.getTomadTiempo() ? "A tiempo" : "A destiempo");
        return convertView;
    }

}
