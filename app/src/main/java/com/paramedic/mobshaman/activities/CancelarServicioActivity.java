package com.paramedic.mobshaman.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.paramedic.mobshaman.R;
import java.text.MessageFormat;

public class CancelarServicioActivity extends ActionBarActivity {

    Button btnCancelarServicio;
    EditText etMotivoCancelacionServicio;
    TextView txtHeader;
    Intent intent, returnIntent;
    String nroServicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelar_servicio);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        nroServicio = intent.getStringExtra("nroServicio");
        txtHeader = (TextView) findViewById(R.id.txt_header_cancelar_servicio);
        txtHeader.setText(MessageFormat.format("Cancelación del Servicio {0}", nroServicio));

        btnCancelarServicio = (Button) findViewById(R.id.btn_cancelar_servicio);
        etMotivoCancelacionServicio = (EditText) findViewById(R.id.et_motivo_cancelacion);

        btnCancelarServicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String motivo = etMotivoCancelacionServicio.getText().toString();
                if (motivo.equals("") ) {
                    showToast("Debe ingresar el motivo de cancelación");
                } else {
                    returnIntent = new Intent();
                    returnIntent.putExtra("motivoCancelacion",motivo);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
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

    private void showToast(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }
}
