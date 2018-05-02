package itesm.mx.saludintegral.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.fragments.CalendarioFragment;
import itesm.mx.saludintegral.util.Miscellaneous;

public class EspiritualActivity extends AppCompatActivity {

    String strTipo = "Espiritual";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espiritual);

        Miscellaneous.strTipo = strTipo;

        CalendarioFragment calendarioFragment = new CalendarioFragment();

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.frameLayout_ActivityEspiritual, calendarioFragment);
        t.commit();
    }
}
