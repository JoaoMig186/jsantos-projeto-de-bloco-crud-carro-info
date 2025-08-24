package org.jsantostp1.repository;

import org.jsantostp1.model.Carro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarroRepository {
    private final List<Carro> carros = new ArrayList<>();
    private int proximoId = 0;

    public void adicionar(Carro carro) {
        carro.setId(proximoId++);
        carros.add(carro);
    }

    public List<Carro> listarTodos() {
        return new ArrayList<>(carros);
    }

    public Optional<Carro> buscarPorId(int id) {
        return carros.stream()
                .filter(carro -> carro.getId() == id)
                .findFirst();
    }

    public boolean atualizar(Carro carroAtualizado) {
        Optional<Carro> carroExistente = buscarPorId(carroAtualizado.getId());
        if (carroExistente.isPresent()) {
            Carro c = carroExistente.get();
            c.setMarca(carroAtualizado.getMarca());
            c.setModelo(carroAtualizado.getModelo());
            c.setAno(carroAtualizado.getAno());
            c.setCavalos(carroAtualizado.getCavalos());
            c.setCilindrada(carroAtualizado.getCilindrada());
            return true;
        }
        return false;
    }

    public boolean remover(int id) {
        return carros.removeIf(carro -> carro.getId() == id);
    }
}
