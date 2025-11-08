package tests;

import org.jsantostp1.model.Combustivel;
import org.jsantostp1.service.CarroService;
import org.jsantostp1.repository.CarroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarroServiceFailTest {

    private CarroService service;

    @BeforeEach
    void setup() {
        service = new CarroService(new CarroRepository());
    }

    @Test
    void naoDeveCadastrarCarroComMarcaNula() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.cadastrarCarro(null, "Modelo", 2020, List.of(Combustivel.GASOLINA), 100, 1.0));
        assertEquals("Marca ou modelo inválidos", ex.getMessage());
    }

    @Test
    void naoDeveCadastrarCarroComModeloHtml() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.cadastrarCarro("Ford", "<script>alert(1)</script>", 2020, List.of(Combustivel.GASOLINA), 100, 1.0));
        assertEquals("Marca ou modelo inválidos", ex.getMessage());
    }

    @Test
    void naoDeveCadastrarCarroComAnoInvalido() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.cadastrarCarro("Ford", "Ka", 1800, List.of(Combustivel.GASOLINA), 100, 1.0));
        assertEquals("Ano inválido", ex.getMessage());
    }

    @Test
    void naoDeveCadastrarCarroSemCombustivel() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.cadastrarCarro("Ford", "Ka", 2020, List.of(), 100, 1.0));
        assertEquals("Combustíveis inválidos", ex.getMessage());
    }
}
