package itesm.mx.saludintegral.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.parceler.Parcels;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.fragments.AddMedicamentoFragment;
import itesm.mx.saludintegral.fragments.FragmentoMedicamento;
import itesm.mx.saludintegral.fragments.FragmentoMenuSalud;
import itesm.mx.saludintegral.fragments.FragmentoTomarMedicamento;
import itesm.mx.saludintegral.models.Medicamento;

public class SaludActivity extends AppCompatActivity implements FragmentoMenuSalud.OnSelectedListener, FragmentoMedicamento.OnResponseListener, FragmentoTomarMedicamento.OnResponseTomar{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salud);

        //Crear instancia de FragmentoMenuSalud
        FragmentoMenuSalud fragmentoMenuSalud = new FragmentoMenuSalud();
        Bundle bundle = new Bundle();
        //Añade el FragmentoMenuSalud al frameLayout_ActivitySalud FrameLayout
        getSupportFragmentManager().beginTransaction().add(
                R.id.frameLayout_ActivitySalud, fragmentoMenuSalud).commit();
    }

    public void onSelected(int position){
        switch (position) {
            case 0:
                FragmentoMedicamento fragmentoMedicamento=new FragmentoMedicamento();
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_ActivitySalud, fragmentoMedicamento);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    public void onResponse(int position, Medicamento medicamento){
        if(position==2){
            FragmentoTomarMedicamento fragmentoTomarMedicamento=new FragmentoTomarMedicamento();
            Bundle bundle = new Bundle();
            bundle.putParcelable("medicamento", Parcels.wrap(medicamento));
            fragmentoTomarMedicamento.setArguments(bundle);
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout_ActivitySalud, fragmentoTomarMedicamento);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else{
            AddMedicamentoFragment addMedicamentoFragment=new AddMedicamentoFragment();
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout_ActivitySalud, addMedicamentoFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void onResponseTomar(){
        FragmentoMedicamento fragmentoMedicamento=new FragmentoMedicamento();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_ActivitySalud, fragmentoMedicamento);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
