package org.jsantostp1.repository;

import org.jsantostp1.model.Carro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarroRepository {
    private final List<Carro> carros = new ArrayList<>();
    private int proximoId = 0;

    public void adicionar(Carro carro) {
        Carro carroComId = new Carro(
                ++proximoId,
                carro.getMarca(),
                carro.getModelo(),
                carro.getAno(),
                carro.getCombustiveis(),
                carro.getCavalos(),
                carro.getCilindrada()
        );
        carros.add(carroComId);
    }

    public List<Carro> listarTodos() {
        return new ArrayList<>(carros);
    }

    public Optional<Carro> buscarPorId(int id) {
        return carros.stream()
                .filter(carro -> carro.getId() == id)
                .findFirst();
    }

    public boolean atualizar(Carro novoCarro) {
        for (int i = 0; i < carros.size(); i++) {
            Carro existente = carros.get(i);
            if (existente.getId() == novoCarro.getId()) {
                carros.set(i, novoCarro);
                return true;
            }
        }
        return false;
    }

    public boolean remover(int id) {
        return carros.removeIf(carro -> carro.getId() == id);
    }
}
