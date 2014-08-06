package com.paramedic.mobshaman.models;

import com.paramedic.mobshaman.R;

import java.io.Serializable;

/**
 * Created by maxo on 22/07/14.
 */
public class Servicio implements Serializable{

    private int IdServicio;
    private String Cliente;
    private String Grado;
    private String NroServicio;
    private String Domicilio;
    private String Sexo;
    private String Edad;
    private String Horario;
    private String FecIncidente;
    private String NroAfiliado;
    private String Aviso;
    private String Localidad;
    private String Partido;
    private String EntreCalle1;
    private String EntreCalle2;
    private String Referencia;
    private String Latitud;
    private String Longitud;
    private String Sintomas;
    private String Paciente;
    private String PlanId;
    private Double CoPago;
    private String Observaciones;



    public String getDatosGrales() {

        return Cliente + " / " + NroServicio + " / " + getSexoEdad();
    }

    public String getSexoEdad() {
        return Sexo + Edad;
    }

    public int getGradoDrawable() {

        if (Grado.equals("R")) {
            return R.drawable.shape_grado_rojo;
        } else if (Grado.equals("V")) {
            return R.drawable.shape_grado_verde;
        } else if (Grado.equals("A")) {
            return R.drawable.shape_grado_amarillo;
        } else {
            return R.drawable.shape_grado_verde;
        }
    }

    public int getGradoColor() {

        if (Grado.equals("R")) {
            return R.color.rojo;
        } else if (Grado.equals("V")) {
            return R.color.verde;
        } else if (Grado.equals("A")) {
            return R.color.amarillo;
        } else {
            return R.color.verde;
        }
    }

    public int getIdServicio() {
        return IdServicio;
    }

    public void setIdServicio(int idServicio) {
        IdServicio = idServicio;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getGrado() {
        return Grado;
    }

    public void setGrado(String grado) {
        Grado = grado;
    }

    public String getNroServicio() {
        return NroServicio;
    }

    public void setNroServicio(String nroServicio) {
        NroServicio = nroServicio;
    }

    public String getDomicilio() {
        return Domicilio;
    }

    public void setDomicilio(String domicilio) {
        Domicilio = domicilio;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String sexo) {
        Sexo = sexo;
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public String getHorario() {
        return Horario;
    }

    public void setHorario(String horario) {
        Horario = horario;
    }

    public String getFecIncidente() {
        return FecIncidente;
    }

    public void setFecIncidente(String fecIncidente) {
        FecIncidente = fecIncidente;
    }

    public String getNroAfiliado() {
        return NroAfiliado;
    }

    public void setNroAfiliado(String nroAfiliado) {
        NroAfiliado = nroAfiliado;
    }

    public String getAviso() {
        return Aviso;
    }

    public void setAviso(String aviso) {
        Aviso = aviso;
    }

    public String getLocalidad() {
        return Localidad;
    }

    public void setLocalidad(String localidad) {
        Localidad = localidad;
    }

    public String getPartido() {
        return Partido;
    }

    public void setPartido(String partido) {
        Partido = partido;
    }

    public String getEntreCalle1() {
        return EntreCalle1;
    }

    public void setEntreCalle1(String entreCalle1) {
        EntreCalle1 = entreCalle1;
    }

    public String getEntreCalle2() {
        return EntreCalle2;
    }

    public void setEntreCalle2(String entreCalle2) {
        EntreCalle2 = entreCalle2;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getSintomas() {
        return Sintomas;
    }

    public void setSintomas(String sintomas) {
        Sintomas = sintomas;
    }

    public String getPaciente() {
        return Paciente;
    }

    public void setPaciente(String paciente) {
        Paciente = paciente;
    }

    public String getPlanId() {
        return PlanId;
    }

    public void setPlanId(String planId) {
        PlanId = planId;
    }

    public Double getCoPago() {
        return CoPago;
    }

    public void setCoPago(Double coPago) {
        CoPago = coPago;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }
}
