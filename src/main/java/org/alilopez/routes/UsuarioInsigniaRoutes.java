package org.alilopez.routes;

import io.javalin.Javalin;
import org.alilopez.controller.UsuarioInsigniaController;

public class UsuarioInsigniaRoutes {
    private final UsuarioInsigniaController usuarioInsigniaController;
    public UsuarioInsigniaRoutes(UsuarioInsigniaController usuarioInsigniaController) {
        this.usuarioInsigniaController = usuarioInsigniaController;
    }
    public void register(Javalin app) {
        app.get("/insignias/{idUsuario}", usuarioInsigniaController::getInsigniasByUsuario);
        app.post("/insignias", usuarioInsigniaController::assignInsigniasToUsuario);
        app.put("/insignias/{idUsuario}", usuarioInsigniaController::updateInsigniasFromUsuario);
    }
}
