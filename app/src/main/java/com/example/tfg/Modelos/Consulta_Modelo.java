package com.example.tfg.Modelos;

import com.google.firebase.firestore.PropertyName;

public class Consulta_Modelo {
    private String tipo_piel;
    private String sintomas;
    private String estado;
    private String valoracion_clinica;
    private String rutina_limpieza;
    private String rutina_hidratacion;
    private String rutina_tratamiento;
    private String rutina_proteccion;
    private String uid_Cliente;
    private String uid_Farmaceutico;
    private String correo_cliente;
    private long timestamp;

    public Consulta_Modelo() {}

    public Consulta_Modelo(String uidCliente, String correoCliente, String sintomas, String tipoPiel) {
        this.uid_Cliente = uidCliente;
        this.correo_cliente = correoCliente;
        this.sintomas = sintomas;
        this.tipo_piel = tipoPiel;
        this.estado = "PENDIENTE";
        this.timestamp = System.currentTimeMillis();
    }

    // GETTER Y SETTER
    public String getTipo_piel() { return tipo_piel; }
    public void setTipo_piel(String tipo_piel) { this.tipo_piel = tipo_piel; }

    public String getSintomas() { return sintomas; }
    public void setSintomas(String sintomas) { this.sintomas = sintomas; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    @PropertyName("valoracion")
    public String getValoracion_clinica() { return valoracion_clinica; }
    @PropertyName("valoracion")
    public void setValoracion_clinica(String valoracion_clinica) { this.valoracion_clinica = valoracion_clinica; }
    @PropertyName("limpieza")
    public String getRutina_limpieza() { return rutina_limpieza; }
    @PropertyName("limpieza")
    public void setRutina_limpieza(String rutina_limpieza) { this.rutina_limpieza = rutina_limpieza; }
    @PropertyName("hidratacion")
    public String getRutina_hidratacion() { return rutina_hidratacion; }
    @PropertyName("hidratacion")
    public void setRutina_hidratacion(String rutina_hidratacion) { this.rutina_hidratacion = rutina_hidratacion; }
    @PropertyName("tratamiento")
    public String getRutina_tratamiento() { return rutina_tratamiento; }
    @PropertyName("tratamiento")
    public void setRutina_tratamiento(String rutina_tratamiento) { this.rutina_tratamiento = rutina_tratamiento; }
    @PropertyName("proteccion")
    public String getRutina_proteccion() { return rutina_proteccion; }
    @PropertyName("proteccion")
    public void setRutina_proteccion(String rutina_proteccion) { this.rutina_proteccion = rutina_proteccion; }

    public String getUid_Cliente() { return uid_Cliente; }
    public void setUid_Cliente(String uid_Cliente) { this.uid_Cliente = uid_Cliente; }

    public String getCorreo_cliente() { return correo_cliente; }
    public void setCorreo_cliente(String correo_cliente) { this.correo_cliente = correo_cliente; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}