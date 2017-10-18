package com.paramedic.mobshaman.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.domain.Configuration;
import com.paramedic.mobshaman.helpers.Utils;
import com.paramedic.mobshaman.models.MobileAccessTime;
import com.paramedic.mobshaman.rest.ServiciosRestClient;
import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import static com.paramedic.mobshaman.application.MobShamanApplication.context;

public class AccessTimeActivity extends ActionBarActivity {
    private ProgressDialog pDialog;
    private String URL_HC;
    private Configuration configuration;
    private String entryText;
    private String exitText;
    private String registerEntryText;
    private String registerExitText;
    private Button buttonSave;
    private EditText etLegajo, etDNI;
    private int TipoMovimiento = 0;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        configuration = Configuration.getInstance(this);
        intent = new Intent(this, ServiciosActivity.class);
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
        etLegajo = (EditText) findViewById(R.id.et_access_time_legajo);
        etDNI = (EditText) findViewById(R.id.et_access_time_dni);
        URL_HC = configuration.getUrl() + "/api/mobileaccesstime?licencia=" + configuration.getLicense();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAccessTime();
            }
        });
    }

    private MobileAccessTime getMobileAccessTime() {
        return new MobileAccessTime(
                etLegajo.getText().toString(),
                Long.valueOf(etDNI.getText().toString()),
                configuration.getMobile(),
                TipoMovimiento,
                0.0,
                0.0,
                Utils.getPhoneNumber(getApplicationContext()));
    }

    public void onSwitchEntryExit(View view) {

        boolean on = ((SwitchCompat) view).isChecked();
        if (on) {
            ((SwitchCompat) view).setText(entryText);
            buttonSave.setText(registerEntryText);
            TipoMovimiento = 0;
        } else {
            ((SwitchCompat) view).setText(exitText);
            buttonSave.setText(registerExitText);
            TipoMovimiento = 1;
        }
    }

    public void onRegisterEntryExit(View view) {
    }

    public void postAccessTime() {

        Gson g = new Gson();
        String jsonAccessTime = g.toJson(getMobileAccessTime());
        StringEntity entity = null;
        try {
            entity = new StringEntity(jsonAccessTime);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

//        URL_HC = "http://10.0.3.2/wapimobile/api/mobileaccesstime?licencia=5688923116";

        ServiciosRestClient.post(context, URL_HC, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                String message = TipoMovimiento == 0 ? "Registrando ingreso..." : "Registrando egreso...";
                pDialog.setMessage(message);
                pDialog.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Utils.showToast(getApplicationContext(), "Error " + statusCode + ": " + throwable.getMessage());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Utils.showToast(getApplicationContext(), "Error " + statusCode + ": " + throwable.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject result) {
                Utils.showToast(getApplicationContext(), result.toString());
                startActivity(intent);
            }

            @Override
            public void onFinish() {
                pDialog.dismiss();
            }
        });
    }
}


