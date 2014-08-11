package com.paramedic.mobshaman.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.helpers.FileHelper;
import com.paramedic.mobshaman.models.Servicio;
import com.paramedic.mobshaman.rest.ServiciosRestClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FinalServicioActivity extends ActionBarActivity
implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{

    AutoCompleteTextView searchTextView = null;
    EditText etObservaciones = null;
    Button btnFinalizarServicio = null;
    ArrayAdapter<String> adapter;
    String TIPO_FINAL_SELECCIONADO = "";
    RadioGroup radioGroup;
    TextView titulo;
    Intent intent;

    List<String> vMotivosDiagnosticos = new ArrayList<String>();
    List<String> vDescripcion = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_servicio);

        initializeUI();
        setButtonFinalizarServicioListener();
        setRadioGroupListener();

    }

    private void initializeUI() {

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();

        radioGroup = (RadioGroup) findViewById(R.id.radio_group_final);
        searchTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete_final_servicio);
        etObservaciones = (EditText) findViewById(R.id.et_observaciones_final);
        btnFinalizarServicio = (Button) findViewById(R.id.btn_finalizar_servicio);
        titulo = (TextView) findViewById(R.id.txt_header_final_servicio);
        titulo.setText("Cierre del Servicio " + intent.getStringExtra("nroServicio"));

        searchTextView.setThreshold(1);
        searchTextView.setOnItemSelectedListener(this);
        searchTextView.setOnItemClickListener(this);

    }

    private void setButtonFinalizarServicioListener() {

        btnFinalizarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String palabra = searchTextView.getText().toString();
                String observaciones = etObservaciones.getText().toString();
                int id = 0;

                if (palabra.equals("")) {
                    showToast("Debe ingresar el " + TIPO_FINAL_SELECCIONADO);
                } else {
                    id = searchID(palabra);
                    if (id == -1) {
                        showToast("El " + TIPO_FINAL_SELECCIONADO + " es inválido");
                    } else {

                        Intent returnIntent = new Intent();

                        if (radioGroup.getCheckedRadioButtonId() == R.id.radio_final_si) {
                            returnIntent.putExtra("idDiagnostico",id);
                        } else {
                            returnIntent.putExtra("idMotivo",id);
                        }

                        returnIntent.putExtra("observaciones",observaciones);
                        setResult(RESULT_OK,returnIntent);
                        finish();
                    }
                }

            }
        });
    }

    private void setRadioGroupListener() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                searchTextView.setText("");
                etObservaciones.setText("");

                if (checkedId == R.id.radio_final_si) {
                    TIPO_FINAL_SELECCIONADO = "diagnóstico";
                    searchTextView.setHint("Seleccione Diagnóstico");
                    setAdapterArray("diagnosticos");
                } else {
                    TIPO_FINAL_SELECCIONADO = "motivo de no realización";
                    searchTextView.setHint("Seleccione Motivo");
                    setAdapterArray("motivos");
                }

                searchTextView.setVisibility(View.VISIBLE);
                etObservaciones.setVisibility(View.VISIBLE);
                btnFinalizarServicio.setVisibility(View.VISIBLE);

                adapter = new ArrayAdapter<String>(FinalServicioActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,vDescripcion);
                searchTextView.setAdapter(adapter);

            }
        });
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

    private int searchID(String palabraABuscar) {

        String md = "";

        for (String texto : vMotivosDiagnosticos) {

            md = getFrom(texto,1);
            if (palabraABuscar.equals(md)) {
                return Integer.parseInt(getFrom(texto,0));
            }
        }

        return -1;
    }

    private void setAdapterArray(String fileName) {

        vMotivosDiagnosticos = FileHelper.readFileInternalStorage(fileName,this);

        vDescripcion = new ArrayList<String>();

        for(String texto : vMotivosDiagnosticos) {

            vDescripcion.add(texto.split("&")[1]);

        }
    }

    private void showToast(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }


}
