package org.alilopez.model;

import java.sql.Date;
import java.time.LocalDate;

public class RachaUsuario {
    private int idUsuario;
    private int rachaActual;
    private int rachaMaxima;
    private LocalDate fechaUltimoDia;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getRachaActual() {
        return rachaActual;
    }

    public void setRachaActual(int rachaActual) {
        this.rachaActual = rachaActual;
    }

    public int getRachaMaxima() {
        return rachaMaxima;
    }

    public void setRachaMaxima(int rachaMaxima) {
        this.rachaMaxima = rachaMaxima;
    }

    public LocalDate getFechaUltimoDia() {
        return fechaUltimoDia;
    }

    public void setFechaUltimoDia(LocalDate fechaUltimoDia) {
        this.fechaUltimoDia = fechaUltimoDia;
    }
}
