package com.example.srd.recursos;

import java.io.Serializable;

public class ambiente implements Serializable {
    private String nombreAmbiente;

    public ambiente(String nombreAmbiente) {
        this.nombreAmbiente = nombreAmbiente;
    }

    public String getNombreAmbiente() {
        return nombreAmbiente;
    }

    public void setNombreAmbiente(String nombreAmbiente) {
        this.nombreAmbiente = nombreAmbiente;
    }
}
