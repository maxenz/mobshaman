package com.paramedic.mobshaman.activities;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.domain.Configuration;
import com.paramedic.mobshaman.fragments.AdminPasswordDialogFragment;
import com.paramedic.mobshaman.managers.SessionManager;

public class ServiciosActivity extends ActionBarActivity {

    private Configuration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        configuration = Configuration.getInstance(this);
        setContentView(R.layout.activity_servicios);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.servicios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean handled = true;
        int id = item.getItemId();

        switch (id) {

            case R.id.action_access_time:
                startActivity(new Intent(this, AccessTimeActivity.class));
                break;

            case R.id.action_settings:
                AdminPasswordDialogFragment admFrg = new AdminPasswordDialogFragment();
                admFrg.show(this.getSupportFragmentManager(), "ConfiguracionGeneral");
                break;
            case R.id.action_refresh:
                /** Paso false para poder redefinirlo desde el fragment **/
                handled = false;
                break;
            case R.id.action_actualizar_parametros:
                startActivity(new Intent(this, ActualizarInformacionActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }

        return handled;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem entryExitMenu = menu.findItem(R.id.action_access_time);
        entryExitMenu.setVisible(configuration.enabledEntryExit());
        return true;
    }
}
