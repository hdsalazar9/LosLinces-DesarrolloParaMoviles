package itesm.mx.saludintegral.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.util.Miscellaneous;

public class EspiritualActivity extends AppCompatActivity {

    String strTipo = "Espiritual";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espiritual);

        Miscellaneous.strTipo = strTipo;

    }
}
