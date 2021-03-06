package itesm.mx.saludintegral.activities;

/*
* Esta es la development branch
* */

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.controllers.InfoPersonalOperations;
import itesm.mx.saludintegral.models.InfoPersonal;
import itesm.mx.saludintegral.util.Miscellaneous;

public class MainActivity extends AppCompatActivity {

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            cambiaPantalla();
        }
    };
    TextView tvTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitulo = (TextView) findViewById(R.id.tv_titulo_principal);

        tvTitulo.setText(Miscellaneous.tipos[12]);

        Handler handler = new Handler();
        handler.postDelayed(runnable,1500);

    }

    @Override
    public void onBackPressed() {

    }

    public void cambiaPantalla() {
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
