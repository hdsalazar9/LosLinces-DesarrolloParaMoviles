package itesm.mx.saludintegral.adapters;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.util.Miscellaneous;

/**
 * Clase MenuItemAdapter: un ArrayAdapter para los items de los menus que utilice la aplicación.
 * Utiliza el layout menu_row para inflar los elementos del array.
 */

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {

    ViewGroup.LayoutParams layoutParams;
    int iCantidadTitles = 0;

    public MenuItemAdapter(Context context, ArrayList<MenuItem> items){
        super(context, 0, items);
        iCantidadTitles = items.size();
        Log.d("menuAdpter", "iSize: " +iCantidadTitles);
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
        Log.d("mItemAdapter",mItem.getTitle());
        ivMenuImage.setImageResource(mItem.getImage());

        switch (mItem.getTitle()){
            case "Salud Física":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorSalud, null));
                break;
            case "Monitoreo de sueño":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorSaludDos, null));
                break;
            case "Ejercicio":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorSaludDos, null));
                break;
            case "Salud Psicosocial":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorSocial, null));
                break;
            case "Amigos":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorSocialDos, null));
                break;
            case "Cumpleaños":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorSocialDos, null));
                break;
            case "Activación Cognitiva":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorEjercicio, null));
                break;
            case "Actividades":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorEjercicioDos, null));
                break;

            case "Salud Espiritual":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorEspiritual, null));
                break;

            case "Finanzas":
                linearLayout.setBackgroundColor(ResourcesCompat.getColor(
                        getContext().getResources(),R.color.colorFinanzas, null));
                break;
        }

        //int iSize = (Miscellaneous.iSizeMenu/iCantidadTitles);
        layoutParams = convertView.getLayoutParams();


        //Si estás en el menú principal...
        if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[10])) {
            Log.d("DEBUG","Uso el size del menú prinecial");
            layoutParams.height = (Miscellaneous.iSizeMenu/iCantidadTitles);
        }
        else //En cualquier otro manú
        {
            Log.d("DEBUG","Uso el size del submenú");
            layoutParams.height = (Miscellaneous.iSizeSubMenu/iCantidadTitles);
        }
        Log.d("DEBUG","Altura de cada item: "+ layoutParams.height);
        convertView.setLayoutParams(layoutParams);

        return convertView;
    }
}