package com.example.srd.recursos;

import java.io.Serializable;
import java.util.List;

public class registroAmbiente implements Serializable {
    private int idAmbiente ,idUus,idLuxometro,idRegistro;
    private String fecha;
    private boolean estado;
    private Double alturaMedicion;
    private List<medicion> lstaMediciones ;
    private List<registroLuminariasInAmbiente> lstregluminariasInAmbiente;
    private List<registroSensorInAmbiente> lstregistroSensorInAmbiente;
    private int statuusHttp;

    public int getStatuusHttp() {
        return statuusHttp;
    }

    public void setStatuusHttp(int statuusHttp) {
        this.statuusHttp = statuusHttp;
    }

    public registroAmbiente() {
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getIdLuxometro() {
        return idLuxometro;
    }

    public void setIdLuxometro(int idLuxometro) {
        this.idLuxometro = idLuxometro;
    }

    public int getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(int idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    public int getIdUus() {
        return idUus;
    }

    public void setIdUus(int idUus) {
        this.idUus = idUus;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getAlturaMedicion() {
        return alturaMedicion;
    }

    public void setAlturaMedicion(Double alturaMedicion) {
        this.alturaMedicion = alturaMedicion;
    }

    public List<medicion> getLstaMediciones() {
        return lstaMediciones;
    }

    public void setLstaMediciones(List<medicion> lstaMediciones) {
        this.lstaMediciones = lstaMediciones;
    }

    public List<registroLuminariasInAmbiente> getLstregluminariasInAmbiente() {
        return lstregluminariasInAmbiente;
    }

    public void setLstregluminariasInAmbiente(List<registroLuminariasInAmbiente> lstregluminariasInAmbiente) {
        this.lstregluminariasInAmbiente = lstregluminariasInAmbiente;
    }

    public List<registroSensorInAmbiente> getLstregistroSensorInAmbiente() {
        return lstregistroSensorInAmbiente;
    }

    public void setLstregistroSensorInAmbiente(List<registroSensorInAmbiente> lstregistroSensorInAmbiente) {
        this.lstregistroSensorInAmbiente = lstregistroSensorInAmbiente;
    }



}
