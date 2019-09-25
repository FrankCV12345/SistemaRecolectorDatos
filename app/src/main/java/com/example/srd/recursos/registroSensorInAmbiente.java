package com.example.srd.recursos;

import java.io.Serializable;

public class registroSensorInAmbiente implements Serializable {

    private int idSensor,cantidad,idRegSensorInAmbiente,idRegistro;
    private boolean estado;

    public registroSensorInAmbiente() {
    }

    public registroSensorInAmbiente(int idSensor, int cantidad, boolean estado) {
        this.idSensor = idSensor;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    public int getIdRegSensorInAmbiente() {
        return idRegSensorInAmbiente;
    }

    public void setIdRegSensorInAmbiente(int idRegSensorInAmbiente) {
        this.idRegSensorInAmbiente = idRegSensorInAmbiente;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(int idSensor) {
        this.idSensor = idSensor;
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
