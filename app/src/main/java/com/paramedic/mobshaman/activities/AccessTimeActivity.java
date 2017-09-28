package com.paramedic.mobshaman.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.paramedic.mobshaman.R;


public class AccessTimeActivity extends ActionBarActivity {

    private String entryText;
    private String exitText;
    private String registerEntryText;
    private String registerExitText;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_access_time);
        initializeUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actualizar_informacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeUI() {
        entryText = this.getString(R.string.access_time_entry);
        exitText = this.getString(R.string.access_time_exit);
        registerEntryText = this.getString(R.string.access_time_register_entry);
        registerExitText = this.getString(R.string.access_time_register_exit);
        buttonSave = (Button) findViewById(R.id.btn_access_time_save);
    }

    public void onSwitchEntryExit(View view) {

        boolean on = ((SwitchCompat) view).isChecked();
        if (on) {
            ((SwitchCompat) view).setText(entryText);
            buttonSave.setText(registerEntryText);
        } else {
            ((SwitchCompat) view).setText(exitText);
            buttonSave.setText(registerExitText);
        }
    }

    public void onRegisterEntryExit(View view) {
    }
}


