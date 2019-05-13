package com.example.srd.recursos;

public class Usuario {
    private String nombreUser,passwordUser;
    private Integer idUser;

    public Usuario(String nombreUser, String passwordUser, int idUser) {
        this.nombreUser = nombreUser;
        this.passwordUser = passwordUser;
        this.idUser = idUser;
    }

    public Usuario(String nombreUser, int idUser) {
        this.nombreUser = nombreUser;
        this.idUser = idUser;
    }

    public Usuario() {
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
}
