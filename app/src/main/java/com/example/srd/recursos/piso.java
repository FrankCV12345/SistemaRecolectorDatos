package com.example.srd.recursos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class piso implements Serializable {
    private Integer idPiso;
    private String nombrePiso;

    public piso(Integer idPiso, String nombrePiso) {
        this.idPiso = idPiso;
        this.nombrePiso = nombrePiso;    }

    public piso() {
    }

    public Integer getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(Integer idPiso) {
        this.idPiso = idPiso;
    }

    public String getNombrePiso() {
        return nombrePiso;
    }

    public void setNombrePiso(String nombrePiso) {
        this.nombrePiso = nombrePiso;
    }

}
