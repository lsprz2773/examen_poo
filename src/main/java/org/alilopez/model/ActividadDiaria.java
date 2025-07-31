package org.alilopez.model;


import java.sql.Date;
import java.time.LocalDate;

public class ActividadDiaria {
    private int idActividad;
    private int idUsuario;
    private LocalDate fechaActividad;

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(LocalDate fechaActividad) {
        this.fechaActividad = fechaActividad;
    }
}
