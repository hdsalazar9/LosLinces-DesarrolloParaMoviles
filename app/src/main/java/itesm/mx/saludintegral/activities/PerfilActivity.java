package itesm.mx.saludintegral.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.fragments.PerfilInicialFragment;

public class PerfilActivity extends AppCompatActivity{

    int frameLayoutPerfil = R.id.frameLayout_perfilActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        PerfilInicialFragment perfilInicialFragment = new PerfilInicialFragment();

        getSupportFragmentManager().beginTransaction().add(frameLayoutPerfil, perfilInicialFragment).commit();
    }

}
