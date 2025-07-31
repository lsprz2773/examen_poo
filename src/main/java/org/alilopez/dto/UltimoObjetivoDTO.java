package org.alilopez.dto;

import java.time.LocalDateTime;

public class UltimoObjetivoDTO {
    private int idObjetivo;
    private int idUsuario;
    private String nombre;
    private String descripcion;
    private int totalPomodoros;
    private LocalDateTime fechaCreacion;
    private int duracionPomodoro;
    private int duracionDescanso;
    private int idSesion;

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public int getTotalPomodoros() {
        return totalPomodoros;
    }

    public void setTotalPomodoros(int totalPomodoros) {
        this.totalPomodoros = totalPomodoros;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getDuracionPomodoro() {
        return duracionPomodoro;
    }

    public void setDuracionPomodoro(int duracionPomodoro) {
        this.duracionPomodoro = duracionPomodoro;
    }

    public int getDuracionDescanso() {
        return duracionDescanso;
    }

    public void setDuracionDescanso(int duracionDescanso) {
        this.duracionDescanso = duracionDescanso;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }
}
