package com.paramedic.mobshaman.models;

import android.graphics.drawable.Drawable;

import com.paramedic.mobshaman.R;

import java.io.Serializable;

/**
 * Created by maxo on 22/07/14.
 */
public class Servicio implements Serializable{

    private int id;
    private String cliente;
    private int grado;
    private String nroIncidente;
    private String domicilio;
    private String sexo;
    private String edad;
    private String horario;

    public Servicio(int id,String cliente, int grado, String nroIncidente,
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

    public int getId() {
        return this.id;
    }

    public int getGrado() {
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

        // ROJO : 1
        // AMARILLO : 2
        // VERDE : 3

        switch(grado) {
            case 1:
                return R.drawable.shape_grado_rojo;
            case 2:
                return R.drawable.shape_grado_amarillo;
            case 3:
                return R.drawable.shape_grado_verde;
            default:
                return R.drawable.shape_grado_verde;
        }

    }
}
