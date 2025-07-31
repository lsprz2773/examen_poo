package org.alilopez.model;

import java.time.LocalDateTime;

public class Sesion {
    private int idSesion;
    private int idUsuario;
    private LocalDateTime fechaCreacion;
    private int duracionReal;
    private int descansoReal;
    private int intentos;
    private  String estado;
    private int pomodoros;

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getDuracionReal() {
        return duracionReal;
    }

    public void setDuracionReal(int duracionReal) {
        this.duracionReal = duracionReal;
    }

    public int getDescansoReal() {
        return descansoReal;
    }

    public void setDescansoReal(int descansoReal) {
        this.descansoReal = descansoReal;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPomodoros() {
        return pomodoros;
    }

    public void setPomodoros(int pomodoros) {
        this.pomodoros = pomodoros;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
