package itesm.mx.saludintegral.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidListener;

import org.parceler.Parcels;

import java.util.Date;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.fragments.AddMedicamentoFragment;
import itesm.mx.saludintegral.fragments.CalendarioFragment;
import itesm.mx.saludintegral.fragments.FragmentoMenuCognicion;
import itesm.mx.saludintegral.fragments.FragmentoMenuSalud;
import itesm.mx.saludintegral.fragments.FragmentoTomarMedicamento;
import itesm.mx.saludintegral.models.Medicamento;
import itesm.mx.saludintegral.util.Miscellaneous;

public class CognicionActivity extends AppCompatActivity implements FragmentoMenuCognicion.OnSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognicion);

        //Crear instancia de FragmentoMenuCognicion
        FragmentoMenuCognicion fragmentoMenuCognicion = new FragmentoMenuCognicion();
        Bundle bundle = new Bundle();
        //Añade el FragmentoMenuSalud al frameLayout_ActivitySalud FrameLayout
        getSupportFragmentManager().beginTransaction().add(
                R.id.frameLayout_ActivityCognicion, fragmentoMenuCognicion).commit();
    }

    public void onSelected(int position){
        if(position==1){

            //Log.d("ONSELECTED",""+position);
            Miscellaneous.strTipo = Miscellaneous.tipos[2];
            CalendarioFragment calendarioFragment = new CalendarioFragment();

            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(R.id.frameLayout_ActivityCognicion, calendarioFragment);
            t.addToBackStack(null);
            t.commit();
        }
        else{

            //Log.d("ONSELECTED",""+position);
            Miscellaneous.strTipo = Miscellaneous.tipos[1];
            CalendarioFragment calendarioFragment = new CalendarioFragment();

            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(R.id.frameLayout_ActivityCognicion, calendarioFragment);
            t.addToBackStack(null);
            t.commit();
        }
    }
}
