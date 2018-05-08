package itesm.mx.saludintegral.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.models.ContactoEmergencia;

/**
 * Created by Héctor on 5/7/2018.
 */

public class ContactoEmeAdapter extends ArrayAdapter<ContactoEmergencia> {
    public ContactoEmeAdapter(Context context, ArrayList<ContactoEmergencia> listContacto) {
        super(context, 0, listContacto);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contacto_row, parent, false);
        }

        ContactoEmergencia conEme = getItem(position);

        TextView tvNombre = (TextView) convertView.findViewById(R.id.tv_contacto_nombre);
        EditText etFecha = (EditText) convertView.findViewById(R.id.tv_contacto_telefono);

        tvNombre.setText(conEme.getNombre());
        etFecha.setText(conEme.getTelefono());

        return convertView;
    }



}
