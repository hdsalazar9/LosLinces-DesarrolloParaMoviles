package itesm.mx.saludintegral.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import itesm.mx.saludintegral.adapters.MenuItem;
import itesm.mx.saludintegral.adapters.MenuItemAdapter;
import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.InfoPersonalOperations;
import itesm.mx.saludintegral.models.InfoPersonal;

/*
Main Menu Activity: Referente a "Page 4" del prototipo de Ninjamock
*/

public class MainMenu extends ListActivity implements AdapterView.OnItemClickListener, View.OnClickListener{

    InfoPersonal info;
    InfoPersonalOperations ipo;

    private ArrayAdapter<MenuItem> menuItemArrayAdapter;
    private ArrayList<MenuItem> menuItems;
    ImageView ivProfilePicture;
    TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        menuItems = getMenuItems();
        menuItemArrayAdapter = new MenuItemAdapter(this, menuItems);

        ipo = new InfoPersonalOperations(this);

        ipo.open();

        info = ipo.getAllProducts();

        ivProfilePicture = (ImageView) findViewById(R.id.ivProfileImage);
        tvUserName = (TextView) findViewById(R.id.tvUserName);

        setUserInfo();

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
                intent = new Intent(this, ExampleReceiver.class);
                startActivity(intent);
                break;
            case ("Ejercicio"):
                /* Ir a actividad de Ejercicio */
                break;
            case ("Espiritual"):
                /* Ir a actividad de Ejercicio */
                break;
        }

    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }

    public ArrayList<MenuItem> getMenuItems(){
        MenuItem mItem;
        ArrayList<MenuItem> Menu = new ArrayList<>();

        mItem = new MenuItem("Salud", R.drawable.medicina_icon);
        Menu.add(mItem);

        mItem = new MenuItem("Social", R.drawable.social_icon);
        Menu.add(mItem);

        mItem = new MenuItem("Ejercicio", R.drawable.exercise_icon);
        Menu.add(mItem);

        mItem = new MenuItem("Espiritual", R.drawable.spiritual_icon);
        Menu.add(mItem);

        return Menu;
    }

    public void setUserInfo(){

        tvUserName.setText(("Hola, " + info.getNombre()));

        Bitmap bmp = BitmapFactory.decodeByteArray(info.getFoto(), 0, info.getFoto().length);

        ivProfilePicture.setImageBitmap(Bitmap.createScaledBitmap(bmp, 30,
                30, false));

        ivProfilePicture.setOnClickListener(this);

    }

}
