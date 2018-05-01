package itesm.mx.saludintegral.activities;

/*
* Esta es la development branch
* */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.InfoPersonalOperations;
import itesm.mx.saludintegral.models.InfoPersonal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*

        Por ahora, nuestra main activity te dirige al
        menu principal de actividades.

        */
        InfoPersonalOperations database = new InfoPersonalOperations(getApplicationContext());
        database.open();
        InfoPersonal arrInfo = database.getAllProducts();

        if(arrInfo.getNombre()!=null){ //Si no esta vacio, ya se registro mandarlo al menu principal
            Intent i = new Intent(this, MainMenu.class);
            startActivity(i);
        }
        else{ //Si no se ha registrado, mandarlo a registrarse
            Intent i = new Intent(this, RegistroActivity.class);
            startActivity(i);
        }
    }
}
