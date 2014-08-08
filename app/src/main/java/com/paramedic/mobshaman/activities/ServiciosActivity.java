package com.paramedic.mobshaman.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.fragments.AdminPasswordDialogFragment;
import com.paramedic.mobshaman.helpers.FileHelper;
import com.parse.ParseInstallation;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class ServiciosActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ArrayList<String> diagnosticos = FileHelper.readFileInternalStorage("diagnosticos",this);
        String fiebre = diagnosticos.get(0);
        String diarrea = diagnosticos.get(1);

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
            case R.id.action_settings:
                AdminPasswordDialogFragment admFrg = new AdminPasswordDialogFragment();
                admFrg.show(this.getSupportFragmentManager(),"ConfiguracionGeneral");
                break;
            case R.id.action_refresh:
                /** Paso false para poder redefinirlo desde el fragment **/
                handled = false;
                break;
            case R.id.action_actualizar_parametros:
                startActivity(new Intent(this,ActualizarInformacionActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }

        return handled;

    }



}
