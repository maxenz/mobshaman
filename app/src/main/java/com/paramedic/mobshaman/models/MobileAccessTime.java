package com.paramedic.mobshaman.models;

/**
 * Created by Maxo on 17/10/2017.
 */

public class MobileAccessTime {
    private String Legajo;
    private Long DNI;
    private String Movil;
    private int TipoMovimiento;
    private Double Latitud;
    private Double Longitud;
    private String Telefono;


    public MobileAccessTime(String legajo, Long dni, String movil, int tipoMovimiento, Double latitud, Double longitud, String telefono) {
        Legajo = legajo;
        DNI = dni;
        Movil = movil;
        TipoMovimiento = tipoMovimiento;
        Latitud = latitud;
        Longitud = longitud;
        Telefono = telefono;
    }

    public String getLegajo() {
        return Legajo;
    }

    public void setLegajo(String legajo) {
        Legajo = legajo;
    }

    public Long getDNI() {
        return DNI;
    }

    public void setDNI(Long DNI) {
        this.DNI = DNI;
    }

    public String getMovil() {
        return Movil;
    }

    public void setMovil(String movil) {
        Movil = movil;
    }

    public int getTipoMovimiento() {
        return TipoMovimiento;
    }

    public void setTipoMovimiento(int tipoMovimiento) {
        TipoMovimiento = tipoMovimiento;
    }

    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double latitud) {
        Latitud = latitud;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
        Longitud = longitud;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
}
