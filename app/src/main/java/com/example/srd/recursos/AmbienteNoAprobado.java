package com.example.srd.recursos;

public class AmbienteNoAprobado {

    private  int idAmbiente , idRegistroAmbiente;
    private String nombreAmbiente , nombrePiso;
    private double promedio;

    public double getPromedio() {
        return promedio;
    }

    public AmbienteNoAprobado() {
    }

    public int getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(int idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    public int getIdRegistroAmbiente() {
        return idRegistroAmbiente;
    }

    public void setIdRegistroAmbiente(int idRegistroAmbiente) {
        this.idRegistroAmbiente = idRegistroAmbiente;
    }

    public String getNombreAmbiente() {
        return nombreAmbiente;
    }

    public void setNombreAmbiente(String nombreAmbiente) {
        this.nombreAmbiente = nombreAmbiente;
    }

    public String getNombrePiso() {
        return nombrePiso;
    }

    public void setNombrePiso(String nombrePiso) {
        this.nombrePiso = nombrePiso;
    }
}
