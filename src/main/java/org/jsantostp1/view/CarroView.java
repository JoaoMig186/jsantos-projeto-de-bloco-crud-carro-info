package org.jsantostp1.view;

import org.jsantostp1.model.Carro;
import org.jsantostp1.model.Combustivel;

import java.util.List;
import java.util.Map;

public class CarroView {
    public static String renderList(List<Carro> carros) {
        StringBuilder html = new StringBuilder("""
                <!DOCTYPE html>
                <html lang="pt">
                <head>
                    <meta charset="UTF-8">
                    <title>Lista de Carros</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                </head>
                <body class="container mt-5">
                    <h1>Lista de Carros</h1>
                    <a href="/carros/new" class="btn btn-primary mb-3">Adicionar Novo Carro</a>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Marca</th>
                                <th>Modelo</th>
                                <th>Ano</th>
                                <th>Cavalos</th>
                                <th>Cilindrada</th>
                                <th>Combustíveis</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                """);
        for (Carro carro : carros) {
            String combustiveis = carro.getCombustiveis().toString();
            html.append(String.format("""
                    <tr>
                        <td>%d</td>
                        <td>%s</td>
                        <td>%s</td>
                        <td>%d</td>
                        <td>%d</td>
                        <td>%.1f</td>
                        <td>%s</td>
                        <td>
                            <a href="/carros/edit/%d" class="btn btn-sm btn-warning edit-btn">Editar</a>
                            <form action="/carros/delete/%d" method="post" style="display:inline;">
                                <button type="submit" class="btn btn-sm btn-danger">Deletar</button>
                            </form>
                        </td>
                    </tr>
                    """,
                    carro.getId(),
                    carro.getMarca(),
                    carro.getModelo(),
                    carro.getAno(),
                    carro.getCavalos(),
                    carro.getCilindrada(),
                    combustiveis,
                    carro.getId(),
                    carro.getId()
            ));
        }
        html.append("""
                        </tbody>
                    </table>
                </body>
                </html>
                """);
        return html.toString();
    }

    public static String renderForm(Map<String, Object> model) {
        Object id = model.get("id");
        String action = id != null ? "/carros/edit/" + id : "/carros";
        String title = id != null ? "Editar Carro" : "Novo Carro";

        String marca = (String) model.getOrDefault("marca", "");
        String modelo = (String) model.getOrDefault("modelo", "");
        int ano = (int) model.getOrDefault("ano", 1886);
        int cavalos = (int) model.getOrDefault("cavalos", 1);
        Object cilindradaObj = model.get("cilindrada");
        double cilindrada = 0;

        if (cilindradaObj instanceof Number) {
            cilindrada = ((Number) cilindradaObj).doubleValue();
        } else if (cilindradaObj instanceof String s && !s.isBlank()) {
            try {
                cilindrada = Double.parseDouble(s);
            } catch (NumberFormatException e) {
            }
        }

        List<?> combustiveisSelecionados = (List<?>) model.getOrDefault("combustiveis", List.of());

        StringBuilder combustiveisHtml = new StringBuilder();
        for (Combustivel c : Combustivel.values()) {
            boolean checked = combustiveisSelecionados.contains(c);
            combustiveisHtml.append(String.format("""
            <div class="form-check">
                <input class="form-check-input" type="checkbox" 
                       id="combustivel_%s" name="combustiveis" value="%s" %s>
                <label class="form-check-label" for="combustivel_%s">%s</label>
            </div>
        """, c.name(), c.name(), checked ? "checked" : "", c.name(), c.name()));
        }

        return String.format("""
            <!DOCTYPE html>
            <html lang="pt">
            <head>
                <meta charset="UTF-8">
                <title>%s</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            </head>
            <body class="container mt-5">
                <h1>%s</h1>
                <form action="%s" method="post">
                    <div class="mb-3">
                        <label for="marca" class="form-label">Marca</label>
                        <input type="text" class="form-control" id="marca" name="marca" value="%s" required>
                    </div>
                    <div class="mb-3">
                        <label for="modelo" class="form-label">Modelo</label>
                        <input type="text" class="form-control" id="modelo" name="modelo" value="%s" required>
                    </div>
                    <div class="mb-3">
                        <label for="ano" class="form-label">Ano</label>
                        <input type="number" class="form-control" id="ano" min="1886" max="2100" name="ano" value="%d" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Combustíveis</label>
                        %s
                    </div>
                    <div class="mb-3">
                        <label for="cavalos" class="form-label">Cavalos</label>
                        <input type="number" class="form-control" id="cavalos" min="1" name="cavalos" value="%d" required>
                    </div>
                    <div class="mb-3">
                        <label for="cilindrada" class="form-label">Cilindrada</label>
                        <input type="number" step="0.1" class="form-control" id="cilindrada" min="1" name="cilindrada" value="%.1f" required>
                    </div>
                    <button type="submit" class="btn btn-success">Salvar</button>
                    <a href="/carros" class="btn btn-secondary">Cancelar</a>
                </form>
            </body>
            </html>
            """, title, title, action, marca, modelo, ano, combustiveisHtml, cavalos, cilindrada);
    }
}
