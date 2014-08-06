package com.paramedic.mobshaman.models;

import org.simpleframework.xml.Element;

/**
 * Created by soporte on 06/08/2014.
 */
public class Diagnostico {

    @Element
    private String Descripcion;

    public Diagnostico() {
        super();
    }

    public Diagnostico(String descripcion) {
        this.Descripcion = descripcion;
    }

}
