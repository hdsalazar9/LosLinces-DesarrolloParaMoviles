package itesm.mx.saludintegral.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import itesm.mx.saludintegral.adapters.MenuItem;
import itesm.mx.saludintegral.adapters.MenuItemAdapter;
import itesm.mx.saludintegral.R;

import itesm.mx.saludintegral.controllers.InfoPersonalOperations;
import itesm.mx.saludintegral.models.InfoPersonal;
import itesm.mx.saludintegral.util.Miscellaneous;




/*
Main Menu Activity: Referente a "Page 4" del prototipo de Ninjamock
*/

public class MainMenu extends ListActivity implements AdapterView.OnItemClickListener{

    InfoPersonal info;
    InfoPersonalOperations ipo;

    private ArrayAdapter<MenuItem> menuItemArrayAdapter;
    private ArrayList<MenuItem> menuItems;
    ImageView ivProfilePicture;
    TextView tvUserName;
    LinearLayout llHolaUsuario;
    LinearLayout listViewLinearLayout;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //refresh();
        }
    };
    Boolean bSoloUnaVez = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_holder);

        Log.d("MAINMENU", "Actualizo el nombre de strtipo");
        Miscellaneous.strTipo = Miscellaneous.tipos[10];

        listViewLinearLayout = findViewById(R.id.ListViewLinearLayout);

        menuItems = getMenuItems();
        menuItemArrayAdapter = new MenuItemAdapter(this, menuItems);

        ipo = new InfoPersonalOperations(this);
        ipo.open();
        info = ipo.getAllProducts();
        ipo.close();

        ivProfilePicture = (ImageView) findViewById(R.id.ivProfileImage);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        llHolaUsuario = (LinearLayout) findViewById(R.id.ll_activity_mainmenu_hola);

        llHolaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Miscellaneous.strTipo = Miscellaneous.tipos[9];
                intent = new Intent(getApplicationContext(), PerfilActivity.class);
                startActivity(intent);
            }
        });

        setUserInfo();

        setListAdapter(menuItemArrayAdapter);

        getListView().setOnItemClickListener(this);

        final ViewTreeObserver observer= listViewLinearLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Log.d("Log", "Height: " + listViewLinearLayout.getHeight());
                        if (bSoloUnaVez) {
                            Miscellaneous.iSizeMenu = listViewLinearLayout.getHeight();
                            bSoloUnaVez = false;
                        }
                    }
                });

        //Floating button
        FloatingActionButton fab = findViewById(R.id.fabContacto);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Miscellaneous.strTipo = Miscellaneous.tipos[8];
                Intent intent;
                intent = new Intent(getApplicationContext(), PerfilActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //refresh();
        Log.d("lifecycle","onStart invoked");
    }
    @Override
    protected void onPause() {
        super.onPause();
        //refresh();
        Log.d("lifecycle","onPause invoked");
    }
    @Override
    protected void onStop() {
        super.onStop();
        //refresh();
        Log.d("lifecycle","onStop invoked");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        //refresh();
        Log.d("lifecycle","onRestart invoked");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //refresh();
        Log.d("lifecycle","onDestroy invoked");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("onResumed", "ando en onResumed");
        Miscellaneous.strTipo = Miscellaneous.tipos[10];

        ipo = new InfoPersonalOperations(this);
        ipo.open();
        info = ipo.getAllProducts();
        ipo.close();
        setUserInfo();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        MenuItem menuItem = (MenuItem) parent.getItemAtPosition(position);

        Intent intent;
        switch (menuItem.getTitle()){
            case ("Salud Física"):
                /* Ir a actividad de Salud */
                Miscellaneous.strTipo = "";
                intent=new Intent(this, SaludActivity.class);
                startActivity(intent);
                break;
            case ("Salud Psicosocial"):
                /* Ir a actividad de Social */
                Miscellaneous.strTipo = "";
                intent=new Intent(this, SocialActivity.class);
                startActivity(intent);
                break;
            case ("Activación Cognitiva"):
                /* Ir a actividad de Cognicion */
                Miscellaneous.strTipo = "";
                intent=new Intent(this, CognicionActivity.class);
                startActivity(intent);
                break;
            case ("Salud Espiritual"):
                /* Ir a actividad de Espiritual */
                Miscellaneous.strTipo = "";
                intent=new Intent(this, EspiritualActivity.class);
                startActivity(intent);
                break;
        }

    }

    public ArrayList<MenuItem> getMenuItems(){
        MenuItem mItem;
        ArrayList<MenuItem> Menu = new ArrayList<>();

        mItem = new MenuItem(Miscellaneous.titulos[0], R.drawable.pill);
        Menu.add(mItem);

        mItem = new MenuItem(Miscellaneous.titulos[1], R.drawable.social_ok);
        Menu.add(mItem);

        mItem = new MenuItem(Miscellaneous.titulos[2], R.drawable.light);
        Menu.add(mItem);

        mItem = new MenuItem(Miscellaneous.titulos[3], R.drawable.peace);
        Menu.add(mItem);

        return Menu;
    }

    public void setUserInfo(){

        tvUserName.setText(("Hola, " + info.getNombre()));

        Bitmap bmp = BitmapFactory.decodeByteArray(info.getFoto(), 0, info.getFoto().length);


        ivProfilePicture.setImageBitmap(Bitmap.createScaledBitmap(bmp, bmp.getWidth(),
                bmp.getHeight(), false));
    }

    @Override
    public void onBackPressed() {
        //No permitir que de back, pues regresaria a registro
        this.finishAffinity();
        //moveTaskToBack(true);
    }

    public void refresh(){

        menuItemArrayAdapter.notifyDataSetChanged();
    }

}