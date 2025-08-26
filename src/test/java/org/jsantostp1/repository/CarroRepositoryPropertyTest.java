package org.jsantostp1.repository;

import net.jqwik.api.*;
import org.jsantostp1.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CarroRepositoryPropertyTest {

    @Provide
    Arbitrary<Combustivel> combustiveis() {
        return Arbitraries.of(Combustivel.values());
    }

    @Provide
    Arbitrary<Carro> carros() {
        return Combinators.combine(
                Arbitraries.strings().withCharRange('A','Z').ofMinLength(2).ofMaxLength(10),
                Arbitraries.strings().withCharRange('a','z').ofMinLength(2).ofMaxLength(10),
                Arbitraries.integers().between(1950, 2030),
                Arbitraries.of(Combustivel.values()).set().ofMinSize(1).ofMaxSize(2),
                Arbitraries.integers().between(50, 2000),
                Arbitraries.doubles().between(0.8, 8.0)
        ).as((marca, modelo, ano, combustiveis, cavalos, cilindrada) ->
                new Carro(marca, modelo, ano, List.copyOf(combustiveis), cavalos, cilindrada));
    }

    @Property
    void idsDevemSerIncrementais(@ForAll("carros") Carro carro1, @ForAll("carros") Carro carro2) {
        CarroRepository repo = new CarroRepository();
        repo.adicionar(carro1);
        repo.adicionar(carro2);

        List<Carro> todos = repo.listarTodos();

        assertThat(todos.get(0).getId()).isEqualTo(1);
        assertThat(todos.get(1).getId()).isEqualTo(2);
    }

    @Property
    void buscarPorIdDeveRetornarCarroCorreto(@ForAll("carros") Carro carro) {
        CarroRepository repo = new CarroRepository();
        repo.adicionar(carro);

        Carro encontrado = repo.buscarPorId(1).orElseThrow();

        assertThat(encontrado.getMarca()).isEqualTo(carro.getMarca());
        assertThat(encontrado.getCombustiveis()).isNotEmpty();
    }

    @Property
    void carroDeveTerUmOuDoisCombustiveis(@ForAll("carros") Carro carro) {
        assertThat(carro.getCombustiveis().size()).isBetween(1, 2);
    }
}
