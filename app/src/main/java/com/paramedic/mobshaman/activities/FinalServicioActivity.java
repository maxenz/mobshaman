package com.paramedic.mobshaman.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.paramedic.mobshaman.R;
import com.paramedic.mobshaman.domain.Configuration;
import com.paramedic.mobshaman.helpers.FileHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FinalServicioActivity extends ActionBarActivity
implements AdapterView.OnItemClickListener, OnItemSelectedListener{

    AutoCompleteTextView searchTextView = null;
    EditText etObservaciones = null;
    EditText etReportNumber = null;
    EditText etDiagnosisReasonsCode = null;
    EditText etTimePickerDerivacion = null;
    Button btnFinalizarServicio = null;
    ArrayAdapter<String> adapter;
    String TIPO_FINAL_SELECCIONADO = "";
    RadioGroup radioGroup, radioGroupCopago, radioGroupAudioRecord;
    TextView titulo;
    Intent intent;
    LinearLayout layout_copago, layout_audio_record;
    View separator_copago;
    private Configuration configuration;
    int serviceType;

    //region Audio Properties

    private MediaRecorder audioRecorder;
    private MediaPlayer audioPlayer;
    private String audioOutputFile = null;
    private ImageButton btnStartStopRecordingAudio;
    private ImageButton btnPlayPauseRecordingAudio;
    private TextView txtViewRecordingAudio;

    private boolean isRecordingAudio;
    private boolean isPlayingAudio;
    private boolean audioPlayerHasDatasource;

    //endregion

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
        setRadioGroupAudioRecordListener();
        initListeners();
        initializeAudioProperties();

    }

    private void initListeners() {

        etDiagnosisReasonsCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    setDiagnosisReasonsDescription();
                }
                etObservaciones.requestFocus();
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

        serviceType = intent.getIntExtra("serviceType", 0);

        layout_copago = (LinearLayout) findViewById(R.id.contenido_copago);
        layout_audio_record = (LinearLayout) findViewById(R.id.content_final_audio_record);
        separator_copago = (View) findViewById(R.id.sep_title_final_servicio_copago);
        radioGroupCopago = (RadioGroup) findViewById(R.id.radio_group_final_copago);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_final);
        radioGroupAudioRecord = (RadioGroup) findViewById(R.id.radio_group_final_voice_message);
        searchTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete_final_servicio);
        etObservaciones = (EditText) findViewById(R.id.et_observaciones_final);
        etReportNumber = (EditText) findViewById(R.id.et_report_number_final);
        etDiagnosisReasonsCode = (EditText) findViewById(R.id.et_diagnosis_reasons_code);
        btnFinalizarServicio = (Button) findViewById(R.id.btn_finalizar_servicio);
        titulo = (TextView) findViewById(R.id.txt_header_final_servicio);
        titulo.setText("Cierre del Servicio " + intent.getStringExtra("nroServicio"));

        //Obligo mayusculas en observaciones y diagnostico/motivo de no realizacion

        InputFilter[] capsFilter = new InputFilter[] {new InputFilter.AllCaps()};

        etObservaciones.setFilters(capsFilter);
        etDiagnosisReasonsCode.setFilters(capsFilter);

        searchTextView.setThreshold(1);
        searchTextView.setOnItemSelectedListener(this);
        searchTextView.setOnItemClickListener(this);

        etReportNumber.setVisibility(configuration.isRequestReportNumber() ? View.VISIBLE : View.GONE);

        etTimePickerDerivacion = (EditText) findViewById(R.id.et_time_picker_derivacion);

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
        //etObservaciones.requestFocus();

    }

    private void setButtonFinalizarServicioListener() {

        btnFinalizarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String drDescription = searchTextView.getText().toString();
                String drCode = etDiagnosisReasonsCode.getText().toString();
                String observaciones = etObservaciones.getText().toString();
                String timePickerDerivation = etTimePickerDerivacion.getText().toString();
                String repNumber = etReportNumber.getText().toString();
                Integer requestReportNumber = 0;
                if (!("".equals(repNumber))) {
                    requestReportNumber = Integer.valueOf(repNumber);
                }
                int id;

                boolean serviceWasDone = radioGroup.getCheckedRadioButtonId() == R.id.radio_final_si;
                boolean codeOrDescriptionEmpty = TextUtils.isEmpty(drDescription) || TextUtils.isEmpty(drCode);
                boolean derivationTimeEmpty = TextUtils.isEmpty(timePickerDerivation);

                if (serviceWasDone && serviceType == 0) {
                    if (codeOrDescriptionEmpty) {
                        showToast("Debe ingresar el " + TIPO_FINAL_SELECCIONADO);
                        return;
                    }
                }

                if (!serviceWasDone && codeOrDescriptionEmpty) {
                    showToast("Debe ingresar el " + TIPO_FINAL_SELECCIONADO);
                    return;
                }

                if (serviceWasDone && serviceType == 1) {
                    if (derivationTimeEmpty) {
                        showToast("Debe ingresar el horario de derivación");
                        return;
                    }
                }

                id = getIdByCode(drCode);
                if (id == -1) {
                    showToast("El " + TIPO_FINAL_SELECCIONADO + " es inválido");
                } else {

                    Intent returnIntent = new Intent();

                    if (serviceWasDone) {

                        if (serviceType == 1) {
                            returnIntent.putExtra("derivationTime",timePickerDerivation);
                        }
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

                    // --> Audio
                    if (radioGroupAudioRecord.getCheckedRadioButtonId() == R.id.radio_final_voice_message_yes) {
                        if (audioPlayerHasDatasource) {
                            returnIntent.putExtra("audio", audioOutputFile);
                        }
                    }

                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            }

        });
    }

    private void setRadioGroupAudioRecordListener() {
        radioGroupAudioRecord.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                txtViewRecordingAudio.setText("");
                if (i == R.id.radio_final_voice_message_yes) {
                    layout_audio_record.setVisibility(View.VISIBLE);
                } else {
                    layout_audio_record.setVisibility(View.GONE);
                    btnFinalizarServicio.setVisibility(View.VISIBLE);
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

                etTimePickerDerivacion.setVisibility(View.GONE);

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

                if (serviceType == 1 && checkedId == R.id.radio_final_si) {
                    searchTextView.setVisibility(View.GONE);
                    etDiagnosisReasonsCode.setVisibility(View.GONE);
                    etTimePickerDerivacion.setVisibility(View.VISIBLE);
                }

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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {}

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {}

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

    //region Audio Record

    private void initializeAudioProperties() {

        txtViewRecordingAudio = (TextView) findViewById(R.id.txt_final_recording_audio);
        audioOutputFile = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/" + String.valueOf(new Date().getTime()) + ".3gpp";

        setAudioRecorder();

        btnStartStopRecordingAudio = (ImageButton) findViewById(R.id.btn_final_start_stop_audio);
        btnStartStopRecordingAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStopRecordingAudio();
            }
        });

        btnPlayPauseRecordingAudio = (ImageButton) findViewById(R.id.btn_final_play_pause_audio);
        btnPlayPauseRecordingAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPauseRecordingAudio();
            }
        });

        btnPlayPauseRecordingAudio.setEnabled(false);
        radioGroupAudioRecord.check(R.id.radio_final_voice_message_no);

    }

    private void setAudioPlayerListener() {
        audioPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    isPlayingAudio = false;
                    btnPlayPauseRecordingAudio.setImageResource(R.drawable.ic_play_circle_outline_white_24dp);
                    txtViewRecordingAudio.setText("");
                }
            }
        });
    }

    private void setAudioRecorder() {

        audioRecorder = new MediaRecorder();
        audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        audioRecorder.setOutputFile(audioOutputFile);

    }

    private void startStopRecordingAudio() {
        if (isRecordingAudio) {
            stopRecordingAudio();
        } else {
            startRecordingAudio();
        }
    }

    private void playPauseRecordingAudio() {
        if (isPlayingAudio) {
            pauseAudio();
        } else {
            playAudio();
        }
    }

    private void startRecordingAudio() {
        try {
            isRecordingAudio = true;
            audioPlayerHasDatasource = false;

            setAudioRecorder();
            audioPlayer = new MediaPlayer();
            setAudioPlayerListener();

            audioRecorder.prepare();
            audioRecorder.start();
        } catch (IllegalStateException e) {
            // start:it is called before prepare()
            // prepare: it is called after start() or before setOutputFormat()
            e.printStackTrace();
        } catch (IOException e) {
            // prepare() fails
            e.printStackTrace();
        }

        txtViewRecordingAudio.setText("Grabando...");
        btnFinalizarServicio.setVisibility(View.GONE);
        btnPlayPauseRecordingAudio.setEnabled(false);
        btnStartStopRecordingAudio.setImageResource(R.drawable.ic_stop_white_24dp);

        Toast.makeText(getApplicationContext(), "Comenzó a grabarse el audio.",
                Toast.LENGTH_SHORT).show();
    }

    private void stopRecordingAudio() {

        try {
            isRecordingAudio = false;
            audioRecorder.stop();

            btnFinalizarServicio.setVisibility(View.VISIBLE);
            btnPlayPauseRecordingAudio.setEnabled(true);
            btnStartStopRecordingAudio.setImageResource(R.drawable.ic_record_voice_over_white_24dp);
            txtViewRecordingAudio.setText("Grabación finalizada.");
            btnFinalizarServicio.setEnabled(true);

            Toast.makeText(getApplicationContext(), "La grabación ha finalizado correctamente.",
                    Toast.LENGTH_SHORT).show();
        } catch (IllegalStateException e) {
            //  it is called before start()
            e.printStackTrace();
        } catch (RuntimeException e) {
            // no valid audio/video data has been received
            e.printStackTrace();
        }

    }

    private void playAudio() {
        try{
            isPlayingAudio = true;

            if (!audioPlayerHasDatasource) {
                audioPlayer.setDataSource(audioOutputFile);
                audioPlayerHasDatasource = true;
            }

            audioPlayer.prepare();
            audioPlayer.start();

            btnPlayPauseRecordingAudio.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);
            txtViewRecordingAudio.setText("Reproduciendo audio...");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void pauseAudio() {
        try {
            isPlayingAudio = false;
            if (audioPlayer != null) {
                audioPlayer.stop();
                btnPlayPauseRecordingAudio.setImageResource(R.drawable.ic_play_circle_outline_white_24dp);
                txtViewRecordingAudio.setText("Audio pausado.");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //endregion

}
