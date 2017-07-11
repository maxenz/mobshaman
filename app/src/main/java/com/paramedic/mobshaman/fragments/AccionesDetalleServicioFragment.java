package com.paramedic.mobshaman.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.activities.CancelarServicioActivity;
import com.paramedic.mobshaman.activities.DetalleServicioActivity;
import com.paramedic.mobshaman.activities.FinalServicioActivity;
import com.paramedic.mobshaman.activities.HistoriaClinicaActivity;
import com.paramedic.mobshaman.activities.MapaServicioActivity;
import com.paramedic.mobshaman.activities.ServiciosActivity;
import com.paramedic.mobshaman.domain.Configuration;
import com.paramedic.mobshaman.helpers.DialogHelper;
import com.paramedic.mobshaman.interfaces.AlertListener;
import com.paramedic.mobshaman.models.AccionesRestModel;
import com.paramedic.mobshaman.models.Servicio;
import com.paramedic.mobshaman.rest.ApiClient;
import com.paramedic.mobshaman.rest.ServiciosRestClient;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by soporte on 23/07/2014.
 */
public class AccionesDetalleServicioFragment extends BaseFragment {

    ProgressDialog pDialog;
    Servicio serv;
    Button btnLlegadaServicio, btnSalidaServicio, btnFinalServicio,
            btnHistoriaClinica, btnCancelarServicio, btnDistanciaServicio, btnAttachImage;
    private Configuration configuration;
    private RequestParams finishRequestParams;
    static final int REQUEST_TAKE_PHOTO = 11111;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem itemMapa = menu.findItem(R.id.action_ubicacion_servicio);
        itemMapa.setVisible(false);

    }

    public AccionesDetalleServicioFragment(){
        super();
    }

    /**
     * Static factory method
     * @param sectionNumber
     * @return
     */
    public static AccionesDetalleServicioFragment newInstance(int sectionNumber) {
        AccionesDetalleServicioFragment fragment = new AccionesDetalleServicioFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_acciones_detalle_servicio, container, false);

        configuration = Configuration.getInstance(this.getActivity());

        getServicioFromIntent();

        initializeUI(myView);

        initializeActionButtons();

        return myView;
    }

    private void getServicioFromIntent() {
        Intent intent = this.getActivity().getIntent();
        serv = (Servicio) intent.getSerializableExtra("Servicio");
    }

    private void initializeActionButtons() {

        RequestParams reqParams = new RequestParams();
        reqParams.put("licencia", configuration.getLicense());
        reqParams.put("movil",configuration.getMobile());
        reqParams.put("viajeID", serv.getIdServicio());

        AccionesRestModel salidaServ = new AccionesRestModel("Salida del servicio",
                "¿Seguro que desea dar salida al servicio?","Salida de servicio cancelada",
                configuration.getUrl() + "/actions/setSalidaMovil", "Dando salida al servicio...");

        AccionesRestModel llegadaServ = new AccionesRestModel("Llegada del servicio",
                "¿Seguro que desea dar llegada al servicio?","Llegada de servicio cancelada",
                configuration.getUrl() + "/actions/setLlegadaMovil", "Dando llegada al servicio...");


        doActionServicio(salidaServ,btnSalidaServicio,reqParams);
        doActionServicio(llegadaServ,btnLlegadaServicio,reqParams);

        btnFinalServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doStartActivityForResult(FinalServicioActivity.class, 1);
            }
        });

        btnAttachImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(false);
            }
        });

        btnHistoriaClinica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), HistoriaClinicaActivity.class);
                i.putExtra("viajeId", serv.getIdServicio());
                startActivity(i);
            }
        });

        btnCancelarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doStartActivityForResult(CancelarServicioActivity.class, 2);
            }
        });

        btnDistanciaServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MapaServicioActivity.class);
                i.putExtra("Servicio", serv);
                i.putExtra("mapActionType", 1);
                startActivity(i);
            }
        });

    }

    private void doStartActivityForResult(Class clase,int reqCode ) {
        Intent i = new Intent(getActivity(), clase);
        i.putExtra("nroServicio", serv.getNroServicio());
        i.putExtra("copago", serv.getCoPago());
        i.putExtra("serviceType", serv.getClasificacionId());
        startActivityForResult(i, reqCode);
    }

    private void initializeUI(View myView) {

        finishRequestParams = new RequestParams();

        btnLlegadaServicio = (Button) myView.findViewById(R.id.btn_llegada_servicio);
        btnSalidaServicio = (Button) myView.findViewById(R.id.btn_salida_servicio);
        btnFinalServicio = (Button) myView.findViewById(R.id.btn_final_servicio);
        btnHistoriaClinica = (Button) myView.findViewById(R.id.btn_hc_paciente_servicio);
        btnCancelarServicio = (Button) myView.findViewById(R.id.btn_cancelacion_servicio);
        btnDistanciaServicio = (Button) myView.findViewById(R.id.btn_distancia_servicio);
        btnAttachImage = (Button) myView.findViewById(R.id.btn_attach_image);

        if (serv.getFlgRename() == 1) {
            btnSalidaServicio.setText("Derivación");
            btnLlegadaServicio.setText("Internación");
        }

        toggleButton(btnLlegadaServicio,serv.getHabLlegada());
        toggleButton(btnSalidaServicio,serv.getHabSalida());
        toggleButton(btnFinalServicio, serv.getHabFinal());
        toggleButton(btnCancelarServicio, serv.getHabCancelacion());

        pDialog = new ProgressDialog(getActivity());
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }

    private void showToast(String mensaje) {
        Toast.makeText(getActivity().getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    private void toggleButton(Button btn, int habilitado) {
        boolean isEnabled = habilitado == 1 ? true : false;
        if (!isEnabled) btn.setBackgroundDrawable(getResources().getDrawable(R.drawable.disabled_button_round));
        btn.setEnabled(isEnabled);
        btn.setClickable(isEnabled);
    }

    private void showLoadingMessage(String message) {
        pDialog.setMessage(message);
        pDialog.show();
    }

    private void doActionServicio(final AccionesRestModel accServ, Button btn,
                                  final RequestParams rp) {
        setActionListener(btn, accServ.getTituloAlertDialog(),
                accServ.getMensajeAlertDialog(),
                new AlertListener() {
                    @Override
                    public void PositiveMethod(final DialogInterface dialog, final int id) {
                        try {
                            doAsyncTaskPostServicio(accServ.getUrl(), accServ.getLoadingMessage(),
                                    rp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void NegativeMethod(DialogInterface dialog, int id) {
                        showToast(accServ.getMensajeCancelAlertDialog());
                    }
                }
        );
    }

    public void doAsyncTaskPostServicio(String url, final String dialogMessage,
                                         RequestParams rp) throws JSONException{

            ServiciosRestClient.post(url, rp, new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                    showLoadingMessage(dialogMessage);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    showToast("Error en la red. Intente nuevamente ");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    showToast("Error en la red. Intente nuevamente ");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        showToast(response.getString("Message"));
                        startActivity(new Intent(getActivity(), ServiciosActivity.class));

                    } catch (JSONException e) {
                        showToast(e.getMessage());
                    }
                }

                @Override
                public void onFinish() {
                    pDialog.dismiss();
                }
            });

    }

    private void setActionListener(Button btnAction, final String title,
                                   final String messageDialog, final AlertListener alertListener) {

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelper dg = new DialogHelper();
                dg.getConfirmDialog(getActivity(), title, messageDialog, "Si", "No", false, alertListener);
            }
        });
    }

    private void finishIncident() {
        try {
            doAsyncTaskPostServicio(configuration.getUrl() + "/actions/setFinalServicio","Finalizando servicio...", finishRequestParams);
        } catch (JSONException e) {
            showToast(e.getMessage());
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {

            //FINAL DEL SERVICIO
            case 1:
                if(resultCode == getActivity().RESULT_OK){

                    finishRequestParams.add("reportNumber", String.valueOf(data.getIntExtra("requestReportNumber",0)));
                    finishRequestParams.add("licencia",configuration.getLicense());
                    finishRequestParams.add("movil",configuration.getMobile());
                    finishRequestParams.add("viajeID", String.valueOf(serv.getIdServicio()));
                    finishRequestParams.add("motivoID", String.valueOf(data.getIntExtra("idMotivo", 0)));
                    finishRequestParams.add("diagnosticoID", String.valueOf(data.getIntExtra("idDiagnostico", 0)));
                    finishRequestParams.add("observaciones", data.getStringExtra("observaciones"));
                    finishRequestParams.add("copago", String.valueOf(data.getIntExtra("copago",0)));
                    finishRequestParams.add("derivationTime", String.valueOf(data.getStringExtra("derivationTime")));

                    try {
                        finishRequestParams.put("audio", new File(String.valueOf(data.getStringExtra("audio"))));
                    } catch(FileNotFoundException e) {}

                    if (configuration.isRequestAttachImage()) {
                        showUploadPhotoPopup(true);
                    } else {
                        finishIncident();
                    }

                } else {
                    showToast("No se finalizó el servicio");
                }
            break;

            //CANCELACION DEL SERVICIO
            case 2:
                if(resultCode == getActivity().RESULT_OK) {
                    String motivoCancelacion = data.getStringExtra("motivoCancelacion");
                    RequestParams rp = new RequestParams();
                    rp.add("licencia",configuration.getLicense());
                    rp.add("movil",configuration.getMobile());
                    rp.add("viajeID", String.valueOf(serv.getIdServicio()));
                    rp.add("observaciones",motivoCancelacion);
                    try {
                        doAsyncTaskPostServicio(configuration.getUrl() + "/actions/setCancelacionServicio",
                                "Cancelando servicio...",rp);
                    } catch (JSONException e) {
                        showToast(e.getMessage());
                    }
                } else {
                    showToast("No se canceló el servicio");
                }
            break;

            case REQUEST_TAKE_PHOTO:
                if (resultCode != 0) {
                    try {
                        DetalleServicioActivity activity = (DetalleServicioActivity)getActivity();
                        File f = new File(activity.getCurrentPhotoPath());
                        boolean isFinishingService = activity.getIsFinishingService();
                        uploadPhoto(f, isFinishingService);
                    } catch (Exception ex) {
                        showToast(ex.getMessage());
                    }
                }

            break;

        }

    }

    /**
     * Start the camera by dispatching a camera intent.
     */
    protected void dispatchTakePictureIntent(boolean isFinishingService) {

        // Check if there is a camera.
        Context context = getActivity();
        PackageManager packageManager = context.getPackageManager();
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false){
            Toast.makeText(getActivity(), "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        // Camera exists? Then proceed...
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        DetalleServicioActivity activity = (DetalleServicioActivity)getActivity();
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go.
            // If you don't do this, you may get a crash in some devices.
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast toast = Toast.makeText(activity, "There was a problem saving the photo...", Toast.LENGTH_SHORT);
                toast.show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri fileUri = Uri.fromFile(photoFile);
                activity.setCapturedImageURI(fileUri);
                activity.setCurrentPhotoPath(fileUri.getPath());
                activity.setIsFinishingService(isFinishingService);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        activity.getCapturedImageURI());

                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    protected File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        DetalleServicioActivity activity = (DetalleServicioActivity)getActivity();
        activity.setCurrentPhotoPath("file:" + image.getAbsolutePath());
        return image;
    }


    private void uploadPhoto(File file, final boolean isFinishingService) {

        ApiClient sac = new ApiClient(getActivity(), configuration.getUrl());

        //ApiClient sac = new ApiClient(getActivity(), "http://192.168.56.2/wapimobileforcache/");
        pDialog.setMessage("Enviando imagen...");
        pDialog.show();

        sac.postImageCall(file, String.valueOf(serv.getIdServicio())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                pDialog.dismiss();
                if (code == 200) {
                    if (isFinishingService) {
                        finishIncident();
                    } else {
                        Toast.makeText(getActivity(), "La imagen se envió correctamente.", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Error: " + String.valueOf(response.body()), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getActivity(), "Error: " + String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void showUploadPhotoPopup(final boolean isFinishingService) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
        adb.setTitle("Adjuntar imagen");
        adb.setMessage("¿Desea adjuntar una imagen al incidente?");
        adb.setPositiveButton("Si", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dispatchTakePictureIntent(isFinishingService);
            }
        });
        adb.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                if (isFinishingService) {
                    finishIncident();
                }
                dialog.cancel();
            }
        });

        adb.show();
    }

}
