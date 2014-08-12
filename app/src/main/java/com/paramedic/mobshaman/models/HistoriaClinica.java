package com.paramedic.mobshaman.models;

import android.graphics.Color;

/**
 * Created by soporte on 11/08/2014.
 */
public class HistoriaClinica {

    private int ID;
    private String FecIncidente;
    private int NroServicio;
    private String ColorHexa;
    private String Grado;
    private String Paciente;
    private String Sintomas;
    private String Diagnostico;
    private String Movil;

    public int getGradoColor() {
        return Color.parseColor("#" + ColorHexa);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFecIncidente() {
        return FecIncidente;
    }

    public void setFecIncidente(String fecIncidente) {
        FecIncidente = fecIncidente;
    }

    public int getNroServicio() {
        return NroServicio;
    }

    public void setNroServicio(int nroServicio) {
        NroServicio = nroServicio;
    }

    public String getColorHexa() {
        return ColorHexa;
    }

    public void setColorHexa(String colorHexa) {
        ColorHexa = colorHexa;
    }

    public String getGrado() {
        return Grado;
    }

    public void setGrado(String grado) {
        Grado = grado;
    }

    public String getPaciente() {
        return Paciente;
    }

    public void setPaciente(String paciente) {
        Paciente = paciente;
    }

    public String getSintomas() {
        return Sintomas;
    }

    public void setSintomas(String sintomas) {
        Sintomas = sintomas;
    }

    public String getDiagnostico() {
        return Diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        Diagnostico = diagnostico;
    }

    public String getMovil() {
        return Movil;
    }

    public void setMovil(String movil) {
        Movil = movil;
    }
}
