package com.example.tfg.Modelos;

public class Usuario_Modelo {

    private String uid;
    private String correo;
    private String rol;

    // 1. Constructor vacío obligatorio para que Firebase no dé error
    public Usuario_Modelo() {
    }

    // 2. Constructor para cuando queramos crear un usuario desde código
    public Usuario_Modelo(String uid, String correo, String rol) {
        this.uid = uid;
        this.correo = correo;
        this.rol = rol;
    }

    // 3. GETTERS Y SETTERS

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
