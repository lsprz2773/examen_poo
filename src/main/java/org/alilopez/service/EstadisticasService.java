package org.alilopez.service;

import org.alilopez.model.EstadisticasDTO;
import org.alilopez.repository.EstadisticasRepository;
import java.time.LocalDate;
import java.util.*;

public class EstadisticasService {
    private final EstadisticasRepository repo;

    public EstadisticasService(EstadisticasRepository repo) {
        this.repo = repo;
    }

    public EstadisticasDTO obtenerEstadisticasGenerales(int idUsuario) {
        int pomodoros = repo.contarPomodorosTerminados(idUsuario);
        int objetivos = repo.contarObjetivosAlcanzados(idUsuario);
        int fallos = repo.contarIntentosFallidos(idUsuario);
        int tiempo = repo.calcularTiempoTotalMinutos(idUsuario);
        return new EstadisticasDTO(pomodoros, objetivos, fallos, tiempo);
    }




    public int obtenerTiempoTotalMinutos(int idUsuario) {
        return repo.calcularTiempoTotalMinutos(idUsuario);
    }

    public Map<String, Object> obtenerTiempoPorDiaChart(int idUsuario) {
        Map<LocalDate, Integer> datosPorFecha = (Map<LocalDate, Integer>) repo.obtenerTiempoPorDia(idUsuario);

        List<String> labels = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        LocalDate hoy = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate fecha = hoy.minusDays(i);
            labels.add(fecha.toString());
            data.add(datosPorFecha.getOrDefault(fecha, 0));
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("labels", labels);
        resultado.put("data", data);
        return resultado;
    }

    public Map<String, Object> obtenerPomodorosPorDiaChart(int idUsuario) {
        Map<LocalDate, Integer> datosPorFecha = repo.obtenerPomodorosPorDia(idUsuario);

        List<String> labels = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        LocalDate hoy = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate fecha = hoy.minusDays(i);
            labels.add(fecha.toString());
            data.add(datosPorFecha.getOrDefault(fecha, 0));
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("labels", labels);
        resultado.put("data", data);
        return resultado;
    }

    public Map<String, Object> obtenerObjetivosPorDiaChart(int idUsuario) {
        Map<LocalDate, Integer> datosPorFecha = repo.obtenerObjetivosPorDia(idUsuario);

        List<String> labels = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        LocalDate hoy = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate fecha = hoy.minusDays(i);
            labels.add(fecha.toString());
            data.add(datosPorFecha.getOrDefault(fecha, 0));
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("labels", labels);
        resultado.put("data", data);
        return resultado;
    }

    public Map<String, Object> obtenerFallosPorDiaChart(int idUsuario) {
        Map<LocalDate, Integer> datosPorFecha = repo.obtenerFallosPorDia(idUsuario);

        List<String> labels = new ArrayList<>();
        List<Integer> data = new ArrayList<>();

        LocalDate hoy = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            LocalDate fecha = hoy.minusDays(i);
            labels.add(fecha.toString());
            data.add(datosPorFecha.getOrDefault(fecha, 0));
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("labels", labels);
        resultado.put("data", data);
        return resultado;
    }


}