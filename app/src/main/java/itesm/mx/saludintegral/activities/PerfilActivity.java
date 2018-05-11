package itesm.mx.saludintegral.activities;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.fragments.ContactosEmergenciaFragment;
import itesm.mx.saludintegral.fragments.ListContactoEmergencia;
import itesm.mx.saludintegral.fragments.PerfilInicialFragment;
import itesm.mx.saludintegral.fragments.RegistroContactoFragment;
import itesm.mx.saludintegral.models.ContactoEmergencia;
import itesm.mx.saludintegral.util.Miscellaneous;

public class PerfilActivity extends AppCompatActivity implements ListContactoEmergencia.OnResponseListener, ContactosEmergenciaFragment.OnResponseListener {

    int iCurrentFrameLayout = R.id.frameLayout_perfilActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Reutilizar actividad para mostrar contactos de emergencia
        if(Objects.equals(Miscellaneous.strTipo, Miscellaneous.tipos[8])){
            ContactosEmergenciaFragment contactosEmergenciaFragment = new ContactosEmergenciaFragment();
            changeFragmentNoBack(contactosEmergenciaFragment);
            //getSupportFragmentManager().beginTransaction().add(frameLayoutPerfil, contactosEmergenciaFragment).commit();
        }
        else {
            PerfilInicialFragment perfilInicialFragment = new PerfilInicialFragment();
            changeFragmentHabilitaBack(perfilInicialFragment);
            //getSupportFragmentManager().beginTransaction().add(frameLayoutPerfil, perfilInicialFragment).commit();
        }
    }

    public void onResponse (int id, ContactoEmergencia contactoEmergencia) {
        switch (id) {
            case 0:
                RegistroContactoFragment registroFragment = new RegistroContactoFragment();
                changeFragmentHabilitaBack(registroFragment);
                break;

            case 1:
                Uri uri = Uri.parse("tel:" + contactoEmergencia.getTelefono());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this,"No hay app de marcar.",Toast.LENGTH_LONG).show();
                }
                /*
                Toast.makeText(getApplicationContext(),"SE SELECCIONÓ UN ELELEMENTO", Toast.LENGTH_SHORT).show();
                Log.d("PerfilActivity","Empieza la app del celular");
                */
                break;
        }
    }

    public void changeFragmentHabilitaBack(android.support.v4.app.Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(iCurrentFrameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeFragmentNoBack(android.support.v4.app.Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(iCurrentFrameLayout, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (Miscellaneous.strTipo.equals(Miscellaneous.tipos[9])) {
            Intent i = new Intent(this, MainMenu.class);
            startActivity(i);
        }
        else
        {
            super.onBackPressed();
        }
    }

}
