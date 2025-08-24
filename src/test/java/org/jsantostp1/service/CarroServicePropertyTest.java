package org.jsantostp1.service;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import org.jsantostp1.repository.CarroRepository;
import org.jsantostp1.model.Carro;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CarroServicePropertyTest {

    // Geração de marcas válidas
    @Provide
    Arbitrary<String> marcasValidas() {
        return Arbitraries.strings()
                .withCharRange('A', 'Z')
                .ofMinLength(2)
                .ofMaxLength(20);
    }

    // Geração de modelos válidos
    @Provide
    Arbitrary<String> modelosValidos() {
        return Arbitraries.strings()
                .withCharRange('a', 'z')
                .ofMinLength(2)
                .ofMaxLength(20);
    }

    // Propriedade: deve cadastrar corretamente carros com dados válidos
    @Property
    void deveCadastrarCarroComDadosValidos(
            @ForAll("marcasValidas") String marca,
            @ForAll("modelosValidos") String modelo,
            @ForAll @IntRange(min = 1886, max = 2030) int ano,
            @ForAll @IntRange(min = 1, max = 2000) int cavalos,
            @ForAll @DoubleRange(min = 0.1, max = 8.0) double cilindrada
    ) {
        CarroRepository repository = new CarroRepository();
        CarroService service = new CarroService(repository);

        service.cadastrarCarro(marca, modelo, ano, cavalos, cilindrada);

        List<Carro> carros = service.listarCarros();

        assertThat(carros).hasSize(1);
        Carro carro = carros.get(0);

        assertThat(carro.getMarca()).isEqualTo(marca);
        assertThat(carro.getModelo()).isEqualTo(modelo);
        assertThat(carro.getAno()).isEqualTo(ano);
        assertThat(carro.getCavalos()).isEqualTo(cavalos);
        assertThat(carro.getCilindrada()).isEqualTo(cilindrada);
    }

    // Propriedade: IDs devem ser únicos e incrementais
    @Property
    void idsDevemSerUnicosEIncrementais(
            @ForAll @IntRange(min = 2, max = 10) int quantidade
    ) {
        CarroRepository repository = new CarroRepository();
        CarroService service = new CarroService(repository);

        for (int i = 0; i < quantidade; i++) {
            service.cadastrarCarro("Marca" + i, "Modelo" + i, 2020, 100 + i, 1.6 + i);
        }

        List<Carro> carros = service.listarCarros();

        assertThat(carros).hasSize(quantidade);

        for (int i = 0; i < quantidade; i++) {
            assertThat(carros.get(i).getId()).isEqualTo(i + 1);
        }
    }
}
