package org.jsantostp1.service;

import org.jsantostp1.model.Carro;
import org.jsantostp1.repository.CarroRepository;

import java.util.List;
import java.util.Optional;

public class CarroService {
    private final CarroRepository repository;

    public CarroService(CarroRepository repository) {
        this.repository = repository;
    }

    public void cadastrarCarro(String marca, String modelo, int ano, int cavalos, Double cilindrada) {
        Carro carro = new Carro(marca, modelo, ano, cavalos, cilindrada);
        repository.adicionar(carro);
    }

    public List<Carro> listarCarros() {
        return repository.listarTodos();
    }

    public Optional<Carro> buscarCarroPorId(int id) {
        return repository.buscarPorId(id);
    }

    public boolean atualizarCarro(int id, String novaMarca, String novoModelo, int novoAno, int novosCavalos, Double novaCilindrada) {
        Carro carroAtualizado = new Carro(novaMarca, novoModelo, novoAno, novosCavalos, novaCilindrada);
        carroAtualizado.setId(id);
        return repository.atualizar(carroAtualizado);
    }

    public boolean removerCarro(int id) {
        return repository.remover(id);
    }
}
