package itesm.mx.saludintegral.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.models.MonitoreoSueno;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Created by Héctor on 5/7/2018.
 */

public class MonitoreoArrayAdapter extends ArrayAdapter<MonitoreoSueno> {
    public MonitoreoArrayAdapter(Context context, ArrayList<MonitoreoSueno> listMonitoreos) {
        super(context, 0 ,listMonitoreos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.perfil_monitoreo_row, parent, false);
        }
        MonitoreoSueno moniSue = getItem(position);

        TextView tvFecha = (TextView) convertView.findViewById(R.id.tv_perfil_monitoreo_fecha);
        TextView tvHoras = (TextView) convertView.findViewById(R.id.tv_perfil_monitoreo_horas);

        tvFecha.setText(Miscellaneous.getStringFromDate(moniSue.getFecha()));
        tvHoras.setText(moniSue.getHoras().toString() + " horas");

        return convertView;
    }
}
