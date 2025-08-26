package org.jsantostp1.controller;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import org.jsantostp1.model.Combustivel;
import org.jsantostp1.service.CarroService;
import org.jsantostp1.repository.CarroRepository;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.*;

public class CarroControllerPropertyTest {

    @Provide
    Arbitrary<String> marcasValidas() {
        return Arbitraries.strings()
                .withCharRange('A', 'Z')
                .ofMinLength(2).ofMaxLength(10);
    }

    @Provide
    Arbitrary<String> modelosValidos() {
        return Arbitraries.strings()
                .withCharRange('a', 'z')
                .ofMinLength(2).ofMaxLength(10);
    }

    @Property
    void deveCadastrarCarroPelaController(
            @ForAll("marcasValidas") String marca,
            @ForAll("modelosValidos") String modelo,
            @ForAll @IntRange(min = 1990, max = 2030) int ano,
            @ForAll @IntRange(min = 50, max = 500) int cavalos,
            @ForAll @DoubleRange(min = 1.0, max = 5.0) double cilindrada
    ) {
        String fakeInput = marca + "\n" +
                modelo + "\n" +
                ano + "\n" +
                "1\n0\n" +
                cavalos + "\n" +
                cilindrada + "\n";

        Scanner fakeScanner = new Scanner(new ByteArrayInputStream(fakeInput.getBytes(StandardCharsets.UTF_8)));

        CarroRepository repository = new CarroRepository();
        CarroService service = new CarroService(repository);

        CarroController controller = new CarroController(service, fakeScanner);

        controller.cadastrar();

        var carros = service.listarCarros();
        assertThat(carros).hasSize(1);

        var carro = carros.get(0);
        assertThat(carro.getMarca()).isEqualTo(marca);
        assertThat(carro.getModelo()).isEqualTo(modelo);
        assertThat(carro.getAno()).isEqualTo(ano);
        assertThat(carro.getCavalos()).isEqualTo(cavalos);
        assertThat(carro.getCilindrada()).isEqualTo(cilindrada);
        assertThat(carro.getCombustiveis()).containsExactly(Combustivel.values()[0]);
    }

    @Property
    void deveAtualizarCarroPelaController(
            @ForAll("marcasValidas") String marcaInicial,
            @ForAll("modelosValidos") String modeloInicial,
            @ForAll("marcasValidas") String novaMarca,
            @ForAll("modelosValidos") String novoModelo,
            @ForAll @IntRange(min = 1990, max = 2030) int ano,
            @ForAll @IntRange(min = 1990, max = 2030) int novoAno,
            @ForAll @IntRange(min = 50, max = 500) int cavalos,
            @ForAll @IntRange(min = 50, max = 500) int novosCavalos,
            @ForAll @DoubleRange(min = 1.0, max = 5.0) double cilindrada,
            @ForAll @DoubleRange(min = 1.0, max = 5.0) double novaCilindrada
    ) {
        String cadastroInput = marcaInicial + "\n" +
                modeloInicial + "\n" +
                ano + "\n" +
                "1\n0\n" +
                cavalos + "\n" +
                cilindrada + "\n";

        String updateInput = "1\n" +
                novaMarca + "\n" +
                novoModelo + "\n" +
                novoAno + "\n" +
                "1\n0\n" +
                novosCavalos + "\n" +
                novaCilindrada + "\n";

        String fakeInput = cadastroInput + updateInput;

        Scanner fakeScanner = new Scanner(new ByteArrayInputStream(fakeInput.getBytes(StandardCharsets.UTF_8)));

        CarroRepository repository = new CarroRepository();
        CarroService service = new CarroService(repository);
        CarroController controller = new CarroController(service, fakeScanner);

        controller.cadastrar();
        controller.atualizar();

        var carro = service.listarCarros().get(0);
        assertThat(carro.getMarca()).isEqualTo(novaMarca);
        assertThat(carro.getModelo()).isEqualTo(novoModelo);
        assertThat(carro.getAno()).isEqualTo(novoAno);
        assertThat(carro.getCavalos()).isEqualTo(novosCavalos);
        assertThat(carro.getCilindrada()).isEqualTo(novaCilindrada);
        assertThat(carro.getCombustiveis()).containsExactly(Combustivel.values()[0]);
    }

    @Property
    void deveRemoverCarroPelaController(
            @ForAll("marcasValidas") String marca,
            @ForAll("modelosValidos") String modelo,
            @ForAll @IntRange(min = 1990, max = 2030) int ano,
            @ForAll @IntRange(min = 50, max = 500) int cavalos,
            @ForAll @DoubleRange(min = 1.0, max = 5.0) double cilindrada
    ) {
        String cadastroInput = marca + "\n" +
                modelo + "\n" +
                ano + "\n" +
                "1\n0\n" +
                cavalos + "\n" +
                cilindrada + "\n";

        String removerInput = "1\n";

        String fakeInput = cadastroInput + removerInput;

        Scanner fakeScanner = new Scanner(new ByteArrayInputStream(fakeInput.getBytes(StandardCharsets.UTF_8)));

        CarroRepository repository = new CarroRepository();
        CarroService service = new CarroService(repository);
        CarroController controller = new CarroController(service, fakeScanner);

        controller.cadastrar();
        controller.remover();

        assertThat(service.listarCarros()).isEmpty();
    }

    @Property
    void deveListarCarrosPelaController(
            @ForAll("marcasValidas") String marca1,
            @ForAll("modelosValidos") String modelo1,
            @ForAll("marcasValidas") String marca2,
            @ForAll("modelosValidos") String modelo2,
            @ForAll @IntRange(min = 1990, max = 2030) int ano,
            @ForAll @IntRange(min = 1990, max = 2030) int ano2,
            @ForAll @IntRange(min = 50, max = 500) int cavalos,
            @ForAll @IntRange(min = 50, max = 500) int cavalos2,
            @ForAll @DoubleRange(min = 1.0, max = 5.0) double cilindrada,
            @ForAll @DoubleRange(min = 1.0, max = 5.0) double cilindrada2
    ) {
        String fakeInput =
                marca1 + "\n" + modelo1 + "\n" + ano + "\n" + "1\n0\n" + cavalos + "\n" + cilindrada + "\n" +
                        marca2 + "\n" + modelo2 + "\n" + ano2 + "\n" + "1\n0\n" + cavalos2 + "\n" + cilindrada2 + "\n";

        Scanner fakeScanner = new Scanner(new ByteArrayInputStream(fakeInput.getBytes(StandardCharsets.UTF_8)));

        CarroRepository repository = new CarroRepository();
        CarroService service = new CarroService(repository);
        CarroController controller = new CarroController(service, fakeScanner);

        controller.cadastrar();
        controller.cadastrar();

        List varCarros = service.listarCarros();
        assertThat(varCarros).hasSize(2);
    }

    @Property
    void deveListarCarrosCorretamente() {
        CarroRepository repository = new CarroRepository();
        CarroService service = new CarroService(repository);
        CarroController controller = new CarroController(service, new Scanner(System.in));

        assertThat(service.listarCarros()).isEmpty();

        service.cadastrarCarro("Ford", "Ka", 2020, List.of(Combustivel.GASOLINA), 100, 1.0);

        assertThat(service.listarCarros()).hasSize(1);
    }
}
