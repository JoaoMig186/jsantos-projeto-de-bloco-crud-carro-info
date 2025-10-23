package org.jsantostp1.service;

import org.jsantostp1.model.Carro;
import org.jsantostp1.model.Combustivel;
import org.jsantostp1.repository.CarroRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class CarroService {
    private final CarroRepository repository;

    public CarroService(CarroRepository repository) {
        this.repository = repository;
    }

    public void cadastrarCarro(String marca, String modelo, int ano, List<Combustivel> combustiveis, int cavalos, Double cilindrada) {
        if (ano < 1886 || ano > Calendar.getInstance().get(Calendar.YEAR)) {
            throw new IllegalArgumentException("Ano inválido");
        }
        if (cavalos <= 0) {
            throw new IllegalArgumentException("Número de cavalos inválido");
        }
        if (cilindrada <= 0) {
            throw new IllegalArgumentException("Cilindrada inválida");
        }
        if (combustiveis == null || combustiveis.isEmpty()) {
            throw new IllegalArgumentException("Combustíveis inválidos");
        }

        Carro carro = new Carro(marca, modelo, ano, combustiveis, cavalos, cilindrada);
        repository.adicionar(carro);
    }

    public List<Carro> listarCarros() {
        return repository.listarTodos();
    }

    public Optional<Carro> buscarCarroPorId(int id) {
        return repository.buscarPorId(id);
    }

    public boolean atualizarCarro(int id, String marca, String modelo, int ano, List<Combustivel> combustiveis, int cavalos, double cilindrada) {
        Optional<Carro> original = repository.buscarPorId(id);
        if (original.isEmpty()) {
            return false;
        }

        Carro atualizado = new Carro(id, marca, modelo, ano, combustiveis, cavalos, cilindrada);
        return repository.atualizar(atualizado);
    }

    public boolean removerCarro(int id) {
        return repository.remover(id);
    }
}
