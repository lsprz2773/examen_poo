package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.TareaController;

public class TareaRoutes {
    private final TareaController tareaController;
    public TareaRoutes(TareaController tareaController) {
        this.tareaController = tareaController;
    }
    public void register(Javalin app) {
        app.get("/tareas", tareaController::getAll);
        app.post("/tareas", tareaController::create);
        app.get("/tareas/{id}", tareaController::getById);
        app.get("/tareas/grupo/{id}", tareaController::getTareasByGrupo);
        // Ejemplo de más rutas:
        app.put("/tareas/{id}", tareaController::update);
        app.delete("/tareas/{id}", tareaController::delete);
        app.get("/tarea/{idTarea}/alumnos", tareaController::getAlumnosByTarea);  // Aquí usamos GET para obtener los alumnos de una tarea
        app.get("/grupos/{idGrupo}/tareas/{idTarea}/alumnos-completados", tareaController::getAlumnosConTareaCompletada);
        app.get("/grupos/{idGrupo}/tareas/{idTarea}/alumnos-no-entregados", tareaController::getAlumnosQueNoEntregaron);
        app.get("/usuarios/{idUsuario}/grupos/{idGrupo}/tareas-completadas", tareaController::getTareasCompletadasPorAlumno);
        app.get("/usuarios/{idUsuario}/grupos/{idGrupo}/tareas-pendientes", tareaController::getTareasPendientesPorAlumno);

    }
}
