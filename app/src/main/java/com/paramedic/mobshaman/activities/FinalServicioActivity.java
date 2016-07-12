package com.paramedic.mobshaman.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.domain.Configuration;
import com.paramedic.mobshaman.helpers.FileHelper;
import com.paramedic.mobshaman.models.Servicio;
import com.paramedic.mobshaman.rest.ServiciosRestClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FinalServicioActivity extends ActionBarActivity
implements AdapterView.OnItemClickListener, OnItemSelectedListener{

    AutoCompleteTextView searchTextView = null;
    EditText etObservaciones = null;
    EditText etReportNumber = null;
    EditText etDiagnosisReasonsCode = null;
    Button btnFinalizarServicio = null;
    ArrayAdapter<String> adapter;
    String TIPO_FINAL_SELECCIONADO = "";
    RadioGroup radioGroup, radioGroupCopago;
    TextView titulo;
    Intent intent;
    LinearLayout layout_copago;
    View separator_copago;
    private Configuration configuration;

    List<String> vMotivosDiagnosticos = new ArrayList<String>();
    List<String> vDescripcion = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_servicio);

        configuration = Configuration.getInstance(this);

        initializeUI();
        setButtonFinalizarServicioListener();
        setRadioGroupListener();
        initListeners();

    }

    private void initListeners() {

        etDiagnosisReasonsCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    setDiagnosisReasonsDescription();
                }
                return true;
            }
        });

        etDiagnosisReasonsCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    setDiagnosisReasonsDescription();
                }
            }
        });

        searchTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String code = getCodeByDescription(searchTextView.getText().toString());
                etDiagnosisReasonsCode.setText(code);
                etObservaciones.requestFocus();
            }
        });


    }

    private void initializeUI() {

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();

        layout_copago = (LinearLayout) findViewById(R.id.contenido_copago);
        separator_copago = (View) findViewById(R.id.sep_title_final_servicio_copago);
        radioGroupCopago = (RadioGroup) findViewById(R.id.radio_group_final_copago);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_final);
        searchTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete_final_servicio);
        etObservaciones = (EditText) findViewById(R.id.et_observaciones_final);
        etReportNumber = (EditText) findViewById(R.id.et_report_number_final);
        etDiagnosisReasonsCode = (EditText) findViewById(R.id.et_diagnosis_reasons_code);
        btnFinalizarServicio = (Button) findViewById(R.id.btn_finalizar_servicio);
        titulo = (TextView) findViewById(R.id.txt_header_final_servicio);
        titulo.setText("Cierre del Servicio " + intent.getStringExtra("nroServicio"));

        searchTextView.setThreshold(1);
        searchTextView.setOnItemSelectedListener(this);
        searchTextView.setOnItemClickListener(this);

        etReportNumber.setVisibility(configuration.isRequestReportNumber() ? View.VISIBLE : View.GONE);

    }

    private void setDiagnosisReasonsDescription() {
        String code = etDiagnosisReasonsCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            searchTextView.setText(null);
            return;
        }
            String description = getDescriptionByCode(code);
            searchTextView.setText(description);
            if (description == null) {
                etDiagnosisReasonsCode.setText(null);
                showToast("El código de " + TIPO_FINAL_SELECCIONADO + " es inválido.");
            }
            etObservaciones.requestFocus();

    }

    private void setButtonFinalizarServicioListener() {

        btnFinalizarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drDescription = searchTextView.getText().toString();
                String drCode = etDiagnosisReasonsCode.getText().toString();
                String observaciones = etObservaciones.getText().toString();
                String repNumber = etReportNumber.getText().toString();
                Integer requestReportNumber = 0;
                if (!("".equals(repNumber))) {
                    requestReportNumber = Integer.valueOf(repNumber);
                }
                int id = 0;

                if (TextUtils.isEmpty(drDescription) || TextUtils.isEmpty(drCode)) {
                    showToast("Debe ingresar el " + TIPO_FINAL_SELECCIONADO);
                } else {
                    id = getIdByCode(drCode);
                    if (id == -1) {
                        showToast("El " + TIPO_FINAL_SELECCIONADO + " es inválido");
                    } else {

                        Intent returnIntent = new Intent();

                        if (radioGroup.getCheckedRadioButtonId() == R.id.radio_final_si) {

                            Double copago = intent.getDoubleExtra("copago", 0);
                            if (copago > 0) {
                                if (radioGroupCopago.getCheckedRadioButtonId() == -1) {
                                    showToast("Debe seleccionar si se cobró copago");
                                    return;
                                }
                            }

                            returnIntent.putExtra("idDiagnostico", id);
                            if (radioGroupCopago.getCheckedRadioButtonId()
                                    == R.id.radio_final_copago_no) {
                                returnIntent.putExtra("copago", 1);
                            }
                        } else {
                            returnIntent.putExtra("idMotivo", id);
                        }

                        returnIntent.putExtra("observaciones", observaciones);
                        returnIntent.putExtra("requestReportNumber", requestReportNumber);
                        setResult(RESULT_OK, returnIntent);
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
                etDiagnosisReasonsCode.setText("");
                etObservaciones.setText("");

                if (checkedId == R.id.radio_final_si) {
                    TIPO_FINAL_SELECCIONADO = "diagnóstico";
                    setDiagnosisReasonsHint("Ingrese código de diagnóstico", "Seleccione diagnóstico");
                    setAdapterArray("diagnosticos");
                    Double copago = intent.getDoubleExtra("copago", 0);
                    if (copago > 0) {
                        layout_copago.setVisibility(View.VISIBLE);
                        separator_copago.setVisibility(View.VISIBLE);
                    }

                } else {
                    TIPO_FINAL_SELECCIONADO = "motivo de no realización";
                    setDiagnosisReasonsHint("Ingrese código de motivo", "Seleccione motivo");
                    setAdapterArray("motivos");
                    layout_copago.setVisibility(View.GONE);
                    separator_copago.setVisibility(View.GONE);
                }

                searchTextView.setVisibility(View.VISIBLE);
                etDiagnosisReasonsCode.setVisibility(View.VISIBLE);
                etObservaciones.setVisibility(View.VISIBLE);
                btnFinalizarServicio.setVisibility(View.VISIBLE);

                adapter = new ArrayAdapter<String>(FinalServicioActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, vDescripcion);
                searchTextView.setAdapter(adapter);

            }
        });
    }

    private void setDiagnosisReasonsHint(String codeHint,String descriptionHint) {
        searchTextView.setHint(descriptionHint);
        etDiagnosisReasonsCode.setHint(codeHint);
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

    private String getDescriptionByCode(String code) {
        return searchWord(code, 1, 2);
    }

    private String getCodeByDescription(String description) {
        return searchWord(description, 2, 1);
    }

    private int getIdByCode(String code) {
        String id = searchWord(code,1,0);
        return id == null ? -1 : Integer.parseInt(id);
    }

    private String searchWord(String wordToSearch, int indexToSearch, int indexToReturn) {

        // 0 : ID
        // 1 : Codigo
        // 2 : Descripcion

        for (String text : vMotivosDiagnosticos) {
            String textSearched = getFrom(text, indexToSearch);
            if (wordToSearch.equals(textSearched)) {
                return getFrom(text, indexToReturn);
            }
        }

        return null;
    }

    private void setAdapterArray(String fileName) {

        vMotivosDiagnosticos = FileHelper.readFileInternalStorage(fileName,this);

        vDescripcion = new ArrayList<String>();

        for(String texto : vMotivosDiagnosticos) {

            vDescripcion.add(texto.split("&")[2]);

        }
    }

    private void showToast(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }


}
