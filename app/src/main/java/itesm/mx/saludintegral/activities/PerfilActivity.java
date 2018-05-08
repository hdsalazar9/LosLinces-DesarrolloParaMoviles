package itesm.mx.saludintegral.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Objects;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.fragments.ContactosEmergenciaFragment;
import itesm.mx.saludintegral.fragments.PerfilInicialFragment;
import itesm.mx.saludintegral.util.Miscellaneous;

public class PerfilActivity extends AppCompatActivity{

    int frameLayoutPerfil = R.id.frameLayout_perfilActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Reutilizar actividad para mostrar contactos de emergencia
        if(Objects.equals(Miscellaneous.strTipo, Miscellaneous.tipos[8])){
            ContactosEmergenciaFragment contactosEmergenciaFragment = new ContactosEmergenciaFragment();
            getSupportFragmentManager().beginTransaction().add(frameLayoutPerfil, contactosEmergenciaFragment).commit();
            return;
        }

        PerfilInicialFragment perfilInicialFragment = new PerfilInicialFragment();
        getSupportFragmentManager().beginTransaction().add(frameLayoutPerfil, perfilInicialFragment).commit();
    }

}
