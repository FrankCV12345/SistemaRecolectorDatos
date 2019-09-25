package com.example.srd.recursos;

public class sensor {
    private int idsensor,idUsuRegistro;
    private String nombreSensor,descripcion,abreviasion,urlImg;

    public sensor() {
    }

    public sensor(int idsensor, String abreviasion) {
        this.idsensor = idsensor;
        this.abreviasion = abreviasion;
    }

    public int getIdsensor() {
        return idsensor;
    }

    public void setIdsensor(int idsensor) {
        this.idsensor = idsensor;
    }

    public int getIdUsuRegistro() {
        return idUsuRegistro;
    }

    public void setIdUsuRegistro(int idUsuRegistro) {
        this.idUsuRegistro = idUsuRegistro;
    }

    public String getNombreSensor() {
        return nombreSensor;
    }

    public void setNombreSensor(String nombreSensor) {
        this.nombreSensor = nombreSensor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAbreviasion() {
        return abreviasion;
    }

    public void setAbreviasion(String abreviasion) {
        this.abreviasion = abreviasion;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
