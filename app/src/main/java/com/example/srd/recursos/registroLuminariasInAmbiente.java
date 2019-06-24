package com.example.srd;

public class registroLuminariasInAmbiente {
    private int idLum , cantidad;
    private boolean estado;

    public registroLuminariasInAmbiente(int idLum, int cantidad, boolean estado) {
        this.idLum = idLum;
        this.cantidad = cantidad;
        this.estado = estado;
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
