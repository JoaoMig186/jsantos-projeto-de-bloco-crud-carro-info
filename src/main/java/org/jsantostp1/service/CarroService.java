package org.jsantostp1.service;

import org.jsantostp1.model.Carro;
import org.jsantostp1.model.Combustivel;
import org.jsantostp1.repository.CarroRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class CarroService {
    private final CarroRepository repository;

    private boolean contemHtml(String texto) {
        String limpo = Jsoup.clean(texto, Safelist.none());
        return !limpo.equals(texto);
    }

    public CarroService(CarroRepository repository) {
        this.repository = repository;
    }

    public String verificaAno(int ano) {
        if (ano < 1886 || ano > Calendar.getInstance().get(Calendar.YEAR)) {
            return "Ano inválido";
        } else {
            return "Ano válido";
        }
    }

    public String vereficarCavalos(int cavalos){
        if(cavalos <= 0){
            return "O número de cavalos deve ser maior que 0";
        } else {
            return "Cavalos corretos";
        }
    }

    public String vereficarCilindrada(double cilindradas){
        if(cilindradas <= 0){
            return "O número de cilindradas deve ser maior que 0";
        } else {
            return "Cilindradas corretas";
        }
    }

    public void cadastrarCarro(String marca, String modelo, int ano, List<Combustivel> combustiveis, int cavalos, Double cilindrada) {

        verificaAno(ano);
        vereficarCavalos(cavalos);
        vereficarCilindrada(cilindrada);

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

        if (marca == null || marca.isBlank() || modelo == null || modelo.isBlank() || contemHtml(modelo) || contemHtml(marca)) {
            throw new IllegalArgumentException("Marca ou modelo inválidos");
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
