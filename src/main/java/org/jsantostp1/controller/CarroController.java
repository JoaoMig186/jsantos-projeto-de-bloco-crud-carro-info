package org.jsantostp1.controller;

import io.javalin.Javalin;
import org.jsantostp1.model.Combustivel;
import org.jsantostp1.repository.CarroRepository;
import org.jsantostp1.service.CarroService;
import org.jsantostp1.view.CarroView;

import java.util.*;

public class CarroController {
    private final CarroService service;

    public CarroController(Javalin app){
        this.service = new CarroService(new CarroRepository());

        app.get("/carros", ctx ->
                ctx.html(CarroView.renderList(service.listarCarros()))
        );

        app.get("carros/new", ctx ->
                ctx.html(CarroView.renderForm(new HashMap<>()))
        );

        app.post("/carros", ctx ->{
            String marca= ctx.formParam("marca");
            String modelo = ctx.formParam("modelo");
            int ano = Integer.parseInt(ctx.formParam("ano"));
            List<Combustivel> combustiveis = ctx.formParams("combustiveis").stream()
                    .map(s -> Combustivel.valueOf(s.toUpperCase()))
                    .toList();
            if (combustiveis == null || combustiveis.isEmpty()) {
                throw new IllegalArgumentException("Selecione pelo menos um tipo de combustível.");
            }
            int cavalos = Integer.parseInt(ctx.formParam("cavalos"));
            double cilindrada = Double.parseDouble(ctx.formParam("cilindrada"));

            service.cadastrarCarro(marca, modelo, ano, combustiveis, cavalos, cilindrada);
            ctx.redirect("/carros");
        });

        app.get("/carros/edit/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class).get();

            service.buscarCarroPorId(id).ifPresentOrElse(carro -> {
                Map<String, Object> model = new HashMap<>();
                model.put("id", carro.getId());
                model.put("marca", carro.getMarca());
                model.put("modelo", carro.getModelo());
                model.put("ano", carro.getAno());
                model.put("combustiveis", carro.getCombustiveis());
                model.put("cavalos", carro.getCavalos());
                model.put("cilindrada", carro.getCilindrada());

                ctx.html(CarroView.renderForm(model));
            }, () -> ctx.status(404).result("Carro não encontrado"));
        });

        app.post("/carros/edit/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class).get();

            String marca = ctx.formParam("marca");
            String modelo = ctx.formParam("modelo");
            int ano = Integer.parseInt(ctx.formParam("ano"));
            List<Combustivel> combustiveis = ctx.formParams("combustiveis").stream()
                    .map(s -> Combustivel.valueOf(s.toUpperCase()))
                    .toList();
            int cavalos = Integer.parseInt(ctx.formParam("cavalos"));
            double cilindrada = Double.parseDouble(ctx.formParam("cilindrada"));

            boolean atualizado = service.atualizarCarro(id, marca, modelo, ano, combustiveis, cavalos, cilindrada);

            if (atualizado) {
                ctx.redirect("/carros");
            } else {
                ctx.status(404).result("Carro não encontrado para atualização");
            }
        });

        app.post("/carros/delete/{id}", ctx -> {
            int id = ctx.pathParamAsClass("id", Integer.class).get();
            boolean removido = service.removerCarro(id);

            if (removido) {
                ctx.redirect("/carros");
            } else {
                ctx.status(404).result("Carro não encontrado para exclusão");
            }
        });

    }

}
