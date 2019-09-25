package com.example.srd.recursos;

import java.io.Serializable;

public class registroLuminariasInAmbiente implements Serializable {
    private int idLum , cantidad,idRegistroLum,idregistroAnbien;
    private boolean estado;

    public registroLuminariasInAmbiente(int idLum, int cantidad, boolean estado) {
        this.idLum = idLum;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    public int getIdRegistroLum() {
        return idRegistroLum;
    }

    public void setIdRegistroLum(int idRegistroLum) {
        this.idRegistroLum = idRegistroLum;
    }

    public int getIdregistroAnbien() {
        return idregistroAnbien;
    }

    public void setIdregistroAnbien(int idregistroAnbien) {
        this.idregistroAnbien = idregistroAnbien;
    }

    public int getIdLum() {
        return idLum;
    }

    public void setIdLum(int idLum) {
        this.idLum = idLum;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
