package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.GrupoController;

public class GrupoRoutes {
    private final GrupoController grupoController;

    public GrupoRoutes(GrupoController grupoController) {
        this.grupoController = grupoController;
    }

    public void register(Javalin app) {
        app.get("/grupos", grupoController::getAll);
        app.post("/grupos", grupoController::create);
        app.get("/grupos/{id}", grupoController::getById);
        app.get("/grupos/unirse/{codigoUnico}", grupoController::getByCodigoUnico);

        app.put("/grupos/{id}", grupoController::update);
        app.delete("/grupos/{id}", grupoController::delete);
        app.get("/grupos/docente/{idUsuario}", grupoController::getGruposComoDocente);
        app.get("/grupos/alumno/{idUsuario}", grupoController::getGruposComoAlumno);
        app.post("/grupos/unirse", grupoController::unirseAGrupo);
        app.get("/grupos/{idGrupo}/usuarios/{idUsuario}", grupoController::getGrupoYRol);


        // Ejemplo de más rutas:
        //app.put("/sesion/:id", sesionController::update);
        //app.delete("/sesion/:id", sesionController::delete);
    }
}
