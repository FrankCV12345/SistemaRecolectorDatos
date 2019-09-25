package com.example.srd.recursos;

public class luxometro {
    private int idluxometro;
    private String nombreluxometro, urlimgLuxometro;

    public luxometro() {
    }

    public luxometro(int idluxometro, String nombreluxometro, String urlimgLuxometro) {
        this.idluxometro = idluxometro;
        this.nombreluxometro = nombreluxometro;
        this.urlimgLuxometro = urlimgLuxometro;
    }

    public int getIdluxometro() {
        return idluxometro;
    }

    public void setIdluxometro(int idluxometro) {
        this.idluxometro = idluxometro;
    }

    public String getNombreluxometro() {
        return nombreluxometro;
    }

    public void setNombreluxometro(String nombreluxometro) {
        this.nombreluxometro = nombreluxometro;
    }

    public String getUrlimgLuxometro() {
        return urlimgLuxometro;
    }

    public void setUrlimgLuxometro(String urlimgLuxometro) {
        this.urlimgLuxometro = urlimgLuxometro;
    }
}
