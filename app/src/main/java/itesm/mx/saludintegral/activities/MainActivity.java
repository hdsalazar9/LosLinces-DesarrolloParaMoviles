package itesm.mx.saludintegral.activities;

/*
* Esta es la development branch
* */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import itesm.mx.saludintegral.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*

        Por ahora, nuestra main activity te dirige al
        menu principal de actividades.

         */

        Intent i = new Intent(this, MainMenu.class);
        startActivity(i);
    }
}
