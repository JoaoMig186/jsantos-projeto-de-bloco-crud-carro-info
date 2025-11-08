package tests;

import org.jsantostp1.model.Combustivel;
import org.jsantostp1.service.CarroService;
import org.jsantostp1.repository.CarroRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CarroServiceFuzzTest {

    private final CarroService carroService = new CarroService(new CarroRepository());
    private final Random random = new Random();

    @Test
    void fuzzTestCadastrarCarro() {
        String[] marcas = {"Ford", "Chevrolet", "Toyota", "Honda", "Nissan"};
        List<Combustivel> combustiveis = List.of(Combustivel.GASOLINA, Combustivel.DIESEL, Combustivel.ETANOL);

        for (int i = 0; i < 1000; i++) {
            String marca = marcas[random.nextInt(marcas.length)];
            String modelo = gerarModeloAleatorio();
            int ano = 1880 + random.nextInt(200);
            int cavalos = random.nextInt(500) - 50;
            double cilindrada = random.nextDouble() * 10 - 5;

            try {
                carroService.cadastrarCarro(marca, modelo, ano, combustiveis, cavalos, cilindrada);
                assertTrue(ano >= 1886 && ano <= 2025, "Ano inválido aceito: " + ano);
                assertTrue(cavalos > 0, "Cavalos inválidos aceitos: " + cavalos);
                assertTrue(cilindrada > 0, "Cilindrada inválida aceita: " + cilindrada);
                assertFalse(contemHtml(modelo), "Modelo com HTML aceito: " + modelo);
            } catch (IllegalArgumentException e) {
                assertNotNull(e.getMessage());
            } catch (Exception e) {
                fail("Falha inesperada para input: " + modelo + ", ano: " + ano + ", cavalos: " + cavalos + ", cilindrada: " + cilindrada);
            }
        }
    }

    private String gerarModeloAleatorio() {
        int tamanho = 1 + random.nextInt(20);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tamanho; i++) {
            char c = (char)(32 + random.nextInt(95));
            sb.append(c);
        }

        if (random.nextInt(10) == 0) {
            sb.append("<script>alert('hack');</script>");
        }
        return sb.toString();
    }

    private boolean contemHtml(String input) {
        return input != null && input.matches(".*<[^>]+>.*");
    }
}
