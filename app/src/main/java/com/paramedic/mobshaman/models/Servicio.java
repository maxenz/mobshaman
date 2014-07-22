package com.paramedic.mobshaman.models;

/**
 * Created by maxo on 22/07/14.
 */
public class Servicio {

    private String cliente;
    private String grado;
    private String nroIncidente;
    private String domicilio;
    private String sexo;
    private String edad;
    private String horario;

    public Servicio(String cliente, String grado, String nroIncidente, String domicilio, String sexo,
                    String edad, String horario) {

        super();
        this.cliente = cliente;
        this.grado = grado;
        this.nroIncidente = nroIncidente;
        this.domicilio = domicilio;
        this.sexo = sexo;
        this.edad = edad;
        this.horario = horario;

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
}
