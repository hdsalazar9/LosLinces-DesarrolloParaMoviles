package itesm.mx.saludintegral.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import itesm.mx.saludintegral.adapters.MenuItem;
import itesm.mx.saludintegral.adapters.MenuItemAdapter;
import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.util.Miscellaneous;

/*
Main Menu Activity: Referente a "Page 4" del prototipo de Ninjamock
*/

public class MainMenu extends ListActivity implements AdapterView.OnItemClickListener {

    private ArrayAdapter<MenuItem> menuItemArrayAdapter;
    private ArrayList<MenuItem> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        menuItems = getMenuItems();
        menuItemArrayAdapter = new MenuItemAdapter(this, menuItems);

        setListAdapter(menuItemArrayAdapter);

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        MenuItem menuItem = (MenuItem) parent.getItemAtPosition(position);

        /*  Aqui falta implementar la lógica de la selección
            de un item del menu. */

        Intent intent;

        switch (menuItem.getTitle()){
            case ("Salud"):
                /* Ir a actividad de Salud */
                intent=new Intent(this, SaludActivity.class);
                startActivity(intent);
                break;
            case ("Social"):
                /* Ir a actividad de Social */
                intent=new Intent(this, SocialActivity.class);
                startActivity(intent);
                break;
            case ("Cognicion"):
                /* Ir a actividad de Cognicion */
                intent=new Intent(this, CognicionActivity.class);
                startActivity(intent);
                break;
            case ("Espiritual"):
                /* Ir a actividad de Espiritual */
                intent=new Intent(this, EspiritualActivity.class);
                startActivity(intent);
                break;
        }

    }

    public ArrayList<MenuItem> getMenuItems(){
        MenuItem mItem;
        ArrayList<MenuItem> Menu = new ArrayList<>();

        mItem = new MenuItem("Salud", R.drawable.medicina_icon);
        Menu.add(mItem);

        mItem = new MenuItem("Social", R.drawable.social_icon);
        Menu.add(mItem);

        mItem = new MenuItem("Cognicion", R.drawable.exercise_icon);
        Menu.add(mItem);

        mItem = new MenuItem("Espiritual", R.drawable.spiritual_icon);
        Menu.add(mItem);

        return Menu;
    }
}
