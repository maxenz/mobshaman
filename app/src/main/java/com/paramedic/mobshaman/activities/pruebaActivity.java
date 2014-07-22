package com.paramedic.mobshaman.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.paramedic.mobshaman.R;

public class pruebaActivity extends ActionBarActivity {

    private ProgressDialog pDialog;
    private MiTareaAsincronaDialog tarea2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        final Button button = (Button) findViewById(R.id.buttonREST);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pDialog = new ProgressDialog(pruebaActivity.this);
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setMessage("Actualizando Servicios...");
                pDialog.setCancelable(true);
                pDialog.setMax(100);

                tarea2 = new MiTareaAsincronaDialog();
                tarea2.execute();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.prueba, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MiTareaAsincronaDialog extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            for(int i=1; i<=10; i++) {
                try {
                    tareaLarga();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                publishProgress(i*10);

                if(isCancelled())
                    break;
            }

            return true;
        }

        private void tareaLarga() throws InterruptedException {
            Thread.sleep(1000);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();

            pDialog.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {

            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    MiTareaAsincronaDialog.this.cancel(true);
                }
            });

            pDialog.setProgress(0);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result)
            {
                pDialog.dismiss();
                Toast.makeText(pruebaActivity.this, "Tarea finalizada!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(pruebaActivity.this, "Tarea cancelada!", Toast.LENGTH_SHORT).show();
        }
    }
}
