package itesm.mx.saludintegral.activities;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import itesm.mx.saludintegral.R;
import itesm.mx.saludintegral.fragments.BitacoraEventoFragment;
import itesm.mx.saludintegral.fragments.ContactosEmergenciaFragment;
import itesm.mx.saludintegral.fragments.HistorialMedicFragment;
import itesm.mx.saludintegral.fragments.ListContactoEmergencia;
import itesm.mx.saludintegral.fragments.PerfilInicialFragment;
import itesm.mx.saludintegral.fragments.PerfilMonitoreoFragment;
import itesm.mx.saludintegral.fragments.RegistroContactoFragment;
import itesm.mx.saludintegral.models.ContactoEmergencia;
import itesm.mx.saludintegral.util.Miscellaneous;

public class PerfilActivity extends AppCompatActivity implements ListContactoEmergencia.OnResponseListener, ContactosEmergenciaFragment.OnResponseListener {

    int iCurrentFrameLayout = R.id.frameLayout_perfilActivity;
    Toolbar toolbar;
    TextView tvPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        toolbar = (Toolbar) findViewById(R.id.toolbar_perfil);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().show();
        tvPerfil = findViewById(R.id.tv_perfil);
        tvPerfil.setVisibility(View.VISIBLE);

        //Reutilizar actividad para mostrar contactos de emergencia
        if(Objects.equals(Miscellaneous.strTipo, Miscellaneous.tipos[8])){
            getSupportActionBar().hide();
            tvPerfil.setVisibility(View.INVISIBLE);
            ContactosEmergenciaFragment contactosEmergenciaFragment = new ContactosEmergenciaFragment();
            changeFragmentNoBack(contactosEmergenciaFragment);
        }
        else {
            PerfilInicialFragment perfilInicialFragment = new PerfilInicialFragment();
            changeFragmentHabilitaBack(perfilInicialFragment);
        }
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.perfilmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        tvPerfil.setVisibility(View.INVISIBLE);
        switch (id) {
            case R.id.historial:
                HistorialMedicFragment historialMedicFragment = new HistorialMedicFragment();
                changeFragmentHabilitaBack(historialMedicFragment);
                getSupportActionBar().hide();
                break;
            case R.id.bitacora:
                BitacoraEventoFragment bitacoraEventoFragment = new BitacoraEventoFragment();
                changeFragmentHabilitaBack(bitacoraEventoFragment);
                getSupportActionBar().hide();
                break;
            case R.id.suenos:
                PerfilMonitoreoFragment perfilMonitoreoFragment = new PerfilMonitoreoFragment();
                changeFragmentHabilitaBack(perfilMonitoreoFragment);
                getSupportActionBar().hide();
                break;
        }

        return true;
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
            if(Miscellaneous.strTipo.equals(Miscellaneous.tipos[8])){
                super.onBackPressed();
            }
            else
            {
                tvPerfil.setVisibility(View.VISIBLE);
                getSupportActionBar().show();
                super.onBackPressed();
            }
        }
    }

}
