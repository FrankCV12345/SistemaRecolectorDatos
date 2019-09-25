package com.example.srd.recursos;

import java.io.Serializable;

public class ambiente implements Serializable {
    private String nombreAmbiente;
    private int idAnbiente,idPisoDeAmbiente;

    public ambiente(String nombreAmbiente, int idAnbiente, int idPisoDeAmbiente) {
        this.nombreAmbiente = nombreAmbiente;
        this.idAnbiente = idAnbiente;
        this.idPisoDeAmbiente = idPisoDeAmbiente;
    }

    public String getNombreAmbiente() {
        return nombreAmbiente;
    }

    public void setNombreAmbiente(String nombreAmbiente) {
        this.nombreAmbiente = nombreAmbiente;
    }

    public int getIdAnbiente() {
        return idAnbiente;
    }

    public void setIdAnbiente(int idAnbiente) {
        this.idAnbiente = idAnbiente;
    }

    public int getIdPisoDeAmbiente() {
        return idPisoDeAmbiente;
    }

    public void setIdPisoDeAmbiente(int idPisoDeAmbiente) {
        this.idPisoDeAmbiente = idPisoDeAmbiente;
    }
}
