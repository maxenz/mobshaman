package com.paramedic.mobshaman.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.helpers.FileHelper;
import java.util.ArrayList;
import java.util.List;

public class FinalServicioActivity extends ActionBarActivity
implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{

    AutoCompleteTextView textView=null;
    private ArrayAdapter<String> adapter;

    //These values show in autocomplete
    List<String> vMotivosDiagnosticos = new ArrayList<String>();
    List<String> vDescripcion = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_final_servicio);

        textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_final_servicio);

        setAdapterArray("diagnosticos");
        //Create adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                vDescripcion);

        textView.setThreshold(1);

        //Set adapter to AutoCompleteTextView
        textView.setAdapter(adapter);
        textView.setOnItemSelectedListener(this);
        textView.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getBaseContext(), "Position:" + i + " Month:" + adapterView.getItemAtPosition(i),
                Toast.LENGTH_LONG).show();

        Log.d("AutocompleteContacts", "Position:" + i);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private String getFrom(String texto, int index) {

        String[] v = texto.split("&");
        return v[index];

    }

    private void setAdapterArray(String fileName) {

        vMotivosDiagnosticos = FileHelper.readFileInternalStorage(fileName,this);

        vDescripcion = new ArrayList<String>();

        for(String texto : vMotivosDiagnosticos) {

            vDescripcion.add(texto.split("&")[1]);

        }
    }
}
