package com.paramedic.mobshaman.models;

import com.paramedic.mobshaman.R;

import java.io.Serializable;

/**
 * Created by maxo on 22/07/14.
 */
public class Servicio implements Serializable{

    private int id;
    private String cliente;
    private String grado;
    private String nroIncidente;
    private String domicilio;
    private String sexo;
    private String edad;
    private String horario;
    private String fecIncidente;
    private String nroAfiliado;
    private String aviso;
    private String localidad;
    private String partido;
    private String entreCalle1;
    private String entreCalle2;
    private String referencia;
    private String latitud;
    private String longitud;
    private String sintomas;
    private String paciente;
    private String planId;
    private Double coPago;
    private String observaciones;

    public Servicio(int id,String cliente, String grado, String nroIncidente,
                    String domicilio, String sexo,String edad, String horario) {

        super();
        this.id = id;
        this.cliente = cliente;
        this.grado = grado;
        this.nroIncidente = nroIncidente;
        this.domicilio = domicilio;
        this.sexo = sexo;
        this.edad = edad;
        this.horario = horario;


    }

    public Servicio(int id,String cliente, String grado, String nroIncidente,
                    String domicilio, String sexo,String edad,
                    String fecIncidente, String nroAfiliado, String aviso,
                    String localidad, String partido,String entreCalle1,
                    String entreCalle2, String referencia, String latitud,
                    String longitud, String sintomas, String paciente,
                    String planId, Double coPago, String observaciones) {
        super();
        this.id = id;
        this.cliente = cliente;
        this.grado = grado;
        this.nroIncidente = nroIncidente;
        this.domicilio = domicilio;
        this.sexo = sexo;
        this.edad = edad;
        this.fecIncidente = fecIncidente;
        this.nroAfiliado = nroAfiliado;
        this.aviso = aviso;
        this.localidad = localidad;
        this.partido = partido;
        this.entreCalle1 = entreCalle1;
        this.entreCalle2 = entreCalle2;
        this.referencia = referencia;
        this.latitud = latitud;
        this.longitud = longitud;
        this.sintomas = sintomas;
        this.paciente = paciente;
        this.planId = planId;
        this.coPago = coPago;
        this.observaciones = observaciones;
    }

    public String getFecIncidente() {
        return fecIncidente;
    }

    public void setFecIncidente(String fecIncidente) {
        this.fecIncidente = fecIncidente;
    }

    public String getNroAfiliado() {
        return nroAfiliado;
    }

    public void setNroAfiliado(String nroAfiliado) {
        this.nroAfiliado = nroAfiliado;
    }

    public String getAviso() {
        return aviso;
    }

    public void setAviso(String aviso) {
        this.aviso = aviso;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getEntreCalle1() {
        return entreCalle1;
    }

    public void setEntreCalle1(String entreCalle1) {
        this.entreCalle1 = entreCalle1;
    }

    public String getEntreCalle2() {
        return entreCalle2;
    }

    public void setEntreCalle2(String entreCalle2) {
        this.entreCalle2 = entreCalle2;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Double getCoPago() {
        return coPago;
    }

    public void setCoPago(Double coPago) {
        this.coPago = coPago;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getId() {
        return this.id;
    }

    public String getGrado() {
        return this.grado;
    }

    public String getCliente() {
        return this.cliente;
    }

    public String getNroIncidente() {
        return this.nroIncidente;
    }

    public String getSexo() {
        return this.sexo;
    }

    public String getEdad() {
        return this.edad;
    }

    public String getDomicilio() {
        return this.domicilio;
    }

    public String getSexoEdad() {
        return this.sexo + this.edad;
    }

    public String getHorario() {
        return this.horario;
    }

    public String getDatosGrales() {

        return cliente + " / " + nroIncidente + " / " + getSexoEdad();
    }

    public int getGradoDrawable() {

        if (grado.equals("R")) {
            return R.drawable.shape_grado_rojo;
        } else if (grado.equals("V")) {
            return R.drawable.shape_grado_verde;
        } else if (grado.equals("A")) {
            return R.drawable.shape_grado_amarillo;
        } else {
            return R.drawable.shape_grado_verde;
        }
    }

    public int getGradoColor() {

        if (grado.equals("R")) {
            return R.color.rojo;
        } else if (grado.equals("V")) {
            return R.color.verde;
        } else if (grado.equals("A")) {
            return R.color.amarillo;
        } else {
            return R.color.verde;
        }
    }
}
