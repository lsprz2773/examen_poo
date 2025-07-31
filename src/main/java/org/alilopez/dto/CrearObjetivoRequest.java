    package org.alilopez.dto;

    public class CrearObjetivoRequest {
        public int idUsuario;
        public String nombre;
        public String descripcion;
        public int totalPomodoros;
        public float duracionPomodoro;
        public float duracionDescanso;

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

        public int getIdUsuario() {
            return idUsuario;
        }

        public void setIdUsuario(int idUsuario) {
            this.idUsuario = idUsuario;
        }

        public float getDuracionPomodoro() {
            return duracionPomodoro;
        }

        public void setDuracionPomodoro(float duracionPomodoro) {
            this.duracionPomodoro = duracionPomodoro;
        }

        public float getDuracionDescanso() {
            return duracionDescanso;
        }

        public void setDuracionDescanso(float duracionDescanso) {
            this.duracionDescanso = duracionDescanso;
        }
    }

