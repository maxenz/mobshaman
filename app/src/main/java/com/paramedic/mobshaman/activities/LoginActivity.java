package com.paramedic.mobshaman.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.domain.Configuration;
import com.paramedic.mobshaman.helpers.ServiciosHelper;
import com.paramedic.mobshaman.helpers.Utils;
import com.paramedic.mobshaman.managers.AlertLoginDialogManager;
import com.paramedic.mobshaman.managers.SessionManager;
import com.paramedic.mobshaman.rest.ServiciosRestClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {

	EditText txtUsername, txtPassword;
	Configuration configuration;
    ProgressDialog progressDialog;
	Button btnLogin;
	AlertLoginDialogManager alert = new AlertLoginDialogManager();
	SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Session Manager
        session = new SessionManager(getApplicationContext());
		configuration = configuration.getInstance(this);

        /** Muestro un dialogo spinner (no dejo que usuario use UI) **/
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        
        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
                login();
			}
		});
    }

	public void login() {
		String username = txtUsername.getText().toString();
		String password = txtPassword.getText().toString();

		if(username.trim().length() > 0 && password.trim().length() > 0){
			loginRequest(username, password);
		} else {
            alert.showAlertDialog(LoginActivity.this, "Error de inicio de sesión...", "Por favor, ingrese usuario y password.", false);
        }
	}

	public void loginRequest(String username, String password) {

        final String pass = password;
		String url = configuration.getGestionUrl();
		url += "user=" + username + "&";
		url += "password=" + password + "&";
		url += "log=" + Utils.getPhoneInformation();

		ServiciosRestClient.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                progressDialog.setMessage("Iniciando sesión...");
                progressDialog.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Utils.showToast(LoginActivity.this, "Error en la red, intente nuevamente.");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Utils.showToast(LoginActivity.this, "Error en la red, intente nuevamente.");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject loginData) {

                try {
                    if (loginData.has("Error")) {
                        alert.showAlertDialog(LoginActivity.this, "Error de inicio de sesión...", "Usuario y/o password incorrecto/s.", false);
                        return;
                    }
                    String serial = loginData.get("Serial").toString();
                    String url = loginData.get("AndroidUrl").toString();
                    session.createLoginSession(serial, pass);
                    configuration.setUrl(url);
                    configuration.setLicense(serial);

                    Intent i = new Intent(getApplicationContext(), ServiciosActivity.class);
                    startActivity(i);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
            }

		});
	}

	}