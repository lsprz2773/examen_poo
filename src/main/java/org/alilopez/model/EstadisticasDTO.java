package org.alilopez.model;

public class EstadisticasDTO {
    private int pomodorosTerminados;
    private int objetivosAlcanzados;
    private int intentosFallidos;
    private int tiempoEfectivo;

    public EstadisticasDTO(int pomodorosTerminados, int objetivosAlcanzados, int intentosFallidos, int tiempoEfectivo) {
        this.pomodorosTerminados = pomodorosTerminados;
        this.objetivosAlcanzados = objetivosAlcanzados;
        this.intentosFallidos = intentosFallidos;
        this.tiempoEfectivo = tiempoEfectivo;
    }

    public int getPomodorosTerminados() {
        return pomodorosTerminados;
    }

    public int getObjetivosAlcanzados() {
        return objetivosAlcanzados;
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public int getTiempoEfectivo() {
        return tiempoEfectivo;
    }
}