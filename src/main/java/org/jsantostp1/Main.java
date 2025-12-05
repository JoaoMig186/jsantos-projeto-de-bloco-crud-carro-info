package org.jsantostp1;

import io.javalin.Javalin;
import org.jsantostp1.controller.CarroController;
import org.jsantostp1.repository.CarroRepository;
import org.jsantostp1.service.CarroService;
import org.jsantostp1.util.Rede;

import java.io.IOException;

public class Main {

    public static Javalin startServer() throws IOException {
        Rede rede = new Rede();

        CarroRepository carroRepository = new CarroRepository();
        CarroService carroService = new CarroService(carroRepository);

        Javalin app = Javalin.create().start(7000);

        new CarroController(app);

        app.get("/", ctx -> {
            ctx.html("<h1>Bem-vindo ao sistema de carros!</h1><a href='/carros'>Ver lista</a>");
        });

        rede.verificarConexao("http://localhost:7000/");

        return app;
    }

    public static void main(String[] args) throws IOException {
        startServer();
    }
}
