package itesm.mx.saludintegral.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidListener;

import java.util.Date;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.fragments.CalendarioFragment;
import itesm.mx.saludintegral.util.Miscellaneous;

public class CognicionActivity extends AppCompatActivity {

    String strTipo = "Cognicion";
    CaldroidListener listener = new CaldroidListener() {
        @Override
        public void onSelectDate(Date date, View view) {
            Toast.makeText(getApplicationContext(), ""+date,
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognicion);

        Miscellaneous.strTipo = strTipo;

        CalendarioFragment calendarioFragment = new CalendarioFragment();

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.frameLayout_ActivityCognicion, calendarioFragment);
        t.commit();
    }
}
