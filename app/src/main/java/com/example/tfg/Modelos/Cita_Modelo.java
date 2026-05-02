package com.example.tfg.Modelos;

public class Cita_Modelo {
    private String paciente_nombre;
    private String paciente_id;
    private String fecha;
    private String hora;
    private String estado;
    private String motivo;
    private long timestamp;

    public Cita_Modelo() {}

    public Cita_Modelo(String paciente_nombre, String paciente_id, String fecha, String hora, String motivo) {
        this.paciente_nombre = paciente_nombre;
        this.paciente_id = paciente_id;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = "Pendiente";
        this.timestamp = System.currentTimeMillis();
    }

    // GETTERS Y SETTERS
    public String getPaciente_nombre() { return paciente_nombre; }
    public void setPaciente_nombre(String paciente_nombre) { this.paciente_nombre = paciente_nombre; }

    public String getPaciente_id() { return paciente_id; }
    public void setPaciente_id(String paciente_id) { this.paciente_id = paciente_id; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}