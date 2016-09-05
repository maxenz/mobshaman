package com.paramedic.mobshaman.models;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;
import com.paramedic.mobshaman.helpers.Utils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

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
    private String ColorHexa;
    private int CurrentViaje;
    private byte HabSalida;
    private byte HabLlegada;
    private byte HabFinal;
    private byte HabCancelacion;
    private String Telefono;
    private String Institucion;
    private int ClasificacionId;
    private int FlgRename;
    private int FlgDerivacion;
    private String DerLocalidad;
    private String DerPartido;
    private String DerInstitucion;
    private String DerDomicilio;
    private String DerEntreCalle1;
    private String DerEntreCalle2;
    private String DerReferencia;
    private String DerLatitud;
    private String DerLongitud;
    private String Diagnostico;
    private String SintomasItems;
    private float DistanceToIncident;


    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getDatosGrales() {

        return Cliente + " / " + NroServicio + " / " + getSexoEdad();

    }

    public String getSexoEdad() {
        return Sexo + Edad;
    }

    public int getGradoColor() {

        String hexa = this.getColorHexa();
        if (!hexa.contains("#")) hexa = "#" + hexa;

        return Color.parseColor(hexa);

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

    public String getColorHexa() { return ColorHexa ;}

    public void setColorHexa(String colorHexa) {
        ColorHexa = colorHexa;
    }

    public byte getHabSalida() {
        return HabSalida;
    }

    public byte getHabLlegada() {
        return HabLlegada;
    }

    public byte getHabFinal() {
        return HabFinal;
    }

    public void setHabFinal(byte habFinal) {
        HabFinal = habFinal;
    }

    public void setHabLlegada(byte habLlegada) {
        HabLlegada = habLlegada;
    }

    public void setHabSalida(byte habSalida) {
        HabSalida = habSalida;
    }

    public int getCurrentViaje() {
        return CurrentViaje;
    }

    public void setCurrentViaje(int currentViaje) {
        CurrentViaje = currentViaje;
    }

    public byte getHabCancelacion() {
        return HabCancelacion;
    }

    public void setHabCancelacion(byte habCancelacion) {
        HabCancelacion = habCancelacion;
    }

    public int getClasificacionId() {
        return ClasificacionId;
    }

    public void setClasificacionId(int clasificacionId) {
        ClasificacionId = clasificacionId;
    }

    public int getFlgRename() {
        return FlgRename;
    }

    public void setFlgRename(int flgRename) {
        FlgRename = flgRename;
    }

    public int getFlgDerivacion() {
        return FlgDerivacion;
    }

    public void setFlgDerivacion(int flgDerivacion) {
        FlgDerivacion = flgDerivacion;
    }

    public String getDerLocalidad() {
        return DerLocalidad;
    }

    public void setDerLocalidad(String derLocalidad) {
        DerLocalidad = derLocalidad;
    }

    public String getDerPartido() {
        return DerPartido;
    }

    public void setDerPartido(String derPartido) {
        DerPartido = derPartido;
    }

    public String getDerInstitucion() {
        return DerInstitucion;
    }

    public void setDerInstitucion(String derInstitucion) {
        DerInstitucion = derInstitucion;
    }

    public String getDerDomicilio() {
        return DerDomicilio;
    }

    public void setDerDomicilio(String derDomicilio) {
        DerDomicilio = derDomicilio;
    }

    public String getDerEntreCalle1() {
        return DerEntreCalle1;
    }

    public void setDerEntreCalle1(String derEntreCalle1) {
        DerEntreCalle1 = derEntreCalle1;
    }

    public String getDerEntreCalle2() {
        return DerEntreCalle2;
    }

    public void setDerEntreCalle2(String derEntreCalle2) {
        DerEntreCalle2 = derEntreCalle2;
    }

    public String getDerReferencia() {
        return DerReferencia;
    }

    public void setDerReferencia(String derReferencia) {
        DerReferencia = derReferencia;
    }

    public String getDerLatitud() {
        return DerLatitud;
    }

    public void setDerLatitud(String derLatitud) {
        DerLatitud = derLatitud;
    }

    public String getDerLongitud() {
        return DerLongitud;
    }

    public void setDerLongitud(String derLongitud) {
        DerLongitud = derLongitud;
    }

    public String getDiagnostico() {
        return Diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        Diagnostico = diagnostico;
    }

    public String getSintomasItems() {
        return SintomasItems;
    }

    public void setSintomasItems(String sintomasItems) {
        SintomasItems = sintomasItems;
    }

    public String getInstitucion() {
        return Institucion;
    }

    public void setInstitucion(String institucion) {
        Institucion = institucion;
    }

    public float getDistanceToIncident() {
        return DistanceToIncident;
    }

    public void setDistanceToIncident(float distanceToIncident) {
        DistanceToIncident = distanceToIncident;
    }

    public LinkedList<TriageQuestion> getTriage() {

        LinkedList<TriageQuestion> triage = new LinkedList<TriageQuestion>();
        if (!Utils.empty(this.getSintomasItems())) {
            String[] items = this.getSintomasItems().split("\\n\\r");
            for (int i = 0; i < items.length; i++) {
                String[] vTriage = items[i].split(":");
                TriageQuestion tq = new TriageQuestion(vTriage[1], vTriage[0]);
                triage.add(tq);
            }
        }

        return triage;

    }

    public LatLng getLatLng() {
     return new LatLng(Double.parseDouble(this.Latitud), Double.parseDouble(this.Longitud));
    }



}
