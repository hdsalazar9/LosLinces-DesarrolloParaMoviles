package itesm.mx.saludintegral.adapters;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;

/**
 * Clase MenuItemAdapter: un ArrayAdapter para los items de los menus que utilice la aplicación.
 * Utiliza el layout menu_row para inflar los elementos del array.
 */

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {



    public MenuItemAdapter(Context context, ArrayList<MenuItem> items){
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        MenuItem mItem = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_row, parent, false);
        }

        TextView tvItemTitle = convertView.findViewById(R.id.tvItemTitle);
        ImageView ivMenuImage = convertView.findViewById(R.id.ivItemImage);
        LinearLayout linearLayout = convertView.findViewById(R.id.ll_menu_row);

        tvItemTitle.setText(mItem.getTitle());
        ivMenuImage.setImageResource(mItem.getImage());

        switch (mItem.getTitle()){
            case "Salud":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorSalud, null));
                break;
            case "Social":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorSocial, null));
                break;
            case "Cognicion":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorEjercicio, null));
                break;
            case "Espiritual":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorEspiritual, null));
                break;
        }

        return convertView;
    }
}