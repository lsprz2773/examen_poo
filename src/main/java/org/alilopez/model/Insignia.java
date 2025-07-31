package org.alilopez.model;

import java.time.LocalDateTime;

public class Insignia {
    private int idInsignia;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private int nivel;

    public int getIdInsignia() {
        return idInsignia;
    }

    public void setIdInsignia(int idInsignia) {
        this.idInsignia = idInsignia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
