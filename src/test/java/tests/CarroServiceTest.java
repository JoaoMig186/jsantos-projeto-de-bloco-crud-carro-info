package tests;

import org.jsantostp1.model.Combustivel;
import org.jsantostp1.repository.CarroRepository;
import org.jsantostp1.service.CarroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarroServiceTest {

    private CarroService service;

    @BeforeEach
    void setUp() {
        service = new CarroService(new CarroRepository());
    }

    // -------------------------
    // TESTES DE ENTRADAS INVÁLIDAS
    // -------------------------

    @Test
    void deveLancarExcecaoParaAnoAnteriorA1886() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarCarro("Ford", "Model T", 1500, List.of(Combustivel.GASOLINA), 100, 1.6);
        });
        assertEquals("Ano inválido", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoParaAnoNoFuturo() {
        int anoFuturo = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) + 10;
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarCarro("Tesla", "Cybertruck", anoFuturo, List.of(Combustivel.ETANOL), 800, 0.0);
        });
        assertEquals("Ano inválido", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoParaCavalosNegativos() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarCarro("Chevrolet", "Onix", 2020, List.of(Combustivel.GASOLINA), -100, 1.0);
        });
        assertEquals("Número de cavalos inválido", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoParaCilindradaZero() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarCarro("Honda", "Civic", 2020, List.of(Combustivel.GASOLINA), 120, 0.0);
        });
        assertEquals("Cilindrada inválida", ex.getMessage());
    }

    @Test
    void deveLancarExcecaoSeNaoTiverCombustivel() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarCarro("Toyota", "Corolla", 2020, List.of(), 120, 1.8);
        });
        assertEquals("Combustíveis inválidos", ex.getMessage());
    }

    // -------------------------
    // TESTES DE ENTRADAS MALICIOSAS
    // -------------------------

    @Test
    void deveLancarExcecaoParaModeloComScriptMalicioso() {
        String script = "<script>alert('hack');</script>";
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarCarro("VW", script, 2020, List.of(Combustivel.GASOLINA), 110, 1.6);
        });
        assertTrue(ex.getMessage().contains("inválido") || ex.getMessage().contains("Combustíveis"));
    }

    @Test
    void deveLancarExcecaoParaCamposVazios() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            service.cadastrarCarro("", "", 2020, List.of(Combustivel.GASOLINA), 100, 1.6);
        });
        assertTrue(ex.getMessage().contains("inválido"));
    }

}
