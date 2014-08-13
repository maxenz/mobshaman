package com.paramedic.mobshaman.models;

/**
 * Created by maxo on 05/08/14.
 * Clase que me facilita el manejo de las llamadas asincronicas. Le paso el titulo del dialog,
 * el mensaje, la url que llama y demas, y en un objeto tengo lo que uso cuando llamo
 * a algun servicio rest
 */
public class AccionesRestModel {

    private String TituloAlertDialog;
    private String MensajeAlertDialog;
    private String MensajeCancelAlertDialog;
    private String Url;
    private String LoadingMessage;


    public AccionesRestModel(String tituloAlertDialog, String mensajeAlertDialog,
                             String mensajeCancelAlertDialog, String url, String loadingMessage) {
        TituloAlertDialog = tituloAlertDialog;
        MensajeAlertDialog = mensajeAlertDialog;
        MensajeCancelAlertDialog = mensajeCancelAlertDialog;
        Url = url;
        LoadingMessage = loadingMessage;
    }

    public String getTituloAlertDialog() {
        return TituloAlertDialog;
    }

    public void setTituloAlertDialog(String tituloAlertDialog) {
        TituloAlertDialog = tituloAlertDialog;
    }

    public String getMensajeAlertDialog() {
        return MensajeAlertDialog;
    }

    public void setMensajeAlertDialog(String mensajeAlertDialog) {
        MensajeAlertDialog = mensajeAlertDialog;
    }

    public String getMensajeCancelAlertDialog() {
        return MensajeCancelAlertDialog;
    }

    public void setMensajeCancelAlertDialog(String mensajeCancelAlertDialog) {
        MensajeCancelAlertDialog = mensajeCancelAlertDialog;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getLoadingMessage() {
        return LoadingMessage;
    }

    public void setLoadingMessage(String loadingMessage) {
        LoadingMessage = loadingMessage;
    }
}
