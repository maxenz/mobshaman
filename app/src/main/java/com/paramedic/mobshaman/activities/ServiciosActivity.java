package com.paramedic.mobshaman.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.paramedic.mobshaman.R;

public class ServiciosActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_servicios);
        //getSupportActionBar().setIcon(android.R.color.transparent);

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
//                Intent intent = new Intent(this,pruebaActivity.class);
//                startActivity(intent);
                break;
            case R.id.action_refresh:
                // --> Paso false para poder redefinirlo desde el fragment
                handled = false;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return handled;

    }

}
