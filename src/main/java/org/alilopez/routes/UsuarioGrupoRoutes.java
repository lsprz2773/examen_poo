package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.UsuarioGrupoController;

public class UsuarioGrupoRoutes {
    private final UsuarioGrupoController usuarioGrupoController;
    public UsuarioGrupoRoutes(UsuarioGrupoController usuarioGrupoController) {
        this.usuarioGrupoController = usuarioGrupoController;
    }
    public void register(Javalin app) {
        app.get("/grupos/{id}/usuarios", usuarioGrupoController::getByGrupo);
        app.post("/grupos/usuarios", usuarioGrupoController::create);
        app.delete("/grupos/{idGrupo}/usuarios/{idUsuario}", usuarioGrupoController::delete);
    }
}
