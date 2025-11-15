package tests;

import org.jsantostp1.model.Carro;
import org.jsantostp1.model.Combustivel;
import org.jsantostp1.repository.CarroRepository;
import org.jsantostp1.service.CarroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import java.util.Calendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class ControllerTest {
    private CarroRepository repository;
    private CarroService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CarroRepository.class);
        service = new CarroService(repository);
    }

    @Test
    void deveCadastrarCarroValido() {
        List<Combustivel> combustiveis = List.of(Combustivel.GASOLINA);

        service.cadastrarCarro("Fiat", "Uno", 2020, combustiveis, 75, 1.0);

        verify(repository, times(1)).adicionar(any(Carro.class));
    }

    @Test
    void deveBuscarPorIdExistente() {
        Carro carro = new Carro(1, "VW", "Gol", 2020, List.of(Combustivel.GASOLINA), 75, 1.0);
        when(repository.buscarPorId(1)).thenReturn(Optional.of(carro));

        Optional<Carro> resultado = repository.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getId());
        assertEquals("VW", resultado.get().getMarca());
    }

    @Test
    void deveRetornarVazioQuandoIdNaoExistir() {
        when(repository.buscarPorId(999)).thenReturn(Optional.empty());

        Optional<Carro> resultado = repository.buscarPorId(999);

        assertFalse(resultado.isPresent());
    }

    @Test
    void deveAtualizarCarroExistente() {
        Carro carroAtualizado = new Carro(1, "VW", "Gol", 2020, List.of(Combustivel.GASOLINA), 75, 1.0);

        when(repository.atualizar(any(Carro.class))).thenReturn(true);
        when(repository.buscarPorId(1)).thenReturn(Optional.of(carroAtualizado));

        boolean atualizado = repository.atualizar(carroAtualizado);

        assertTrue(atualizado);

        Optional<Carro> resultado = repository.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("VW", resultado.get().getMarca());
        assertEquals("Gol", resultado.get().getModelo());
        assertEquals(2020, resultado.get().getAno());
    }


    @Test
    void deveLancarExcecaoParaAnoInvalido() {
        List<Combustivel> combustiveis = List.of(Combustivel.GASOLINA);

        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrarCarro("Ford", "Modelo T", 1800, combustiveis, 100, 1.6)
        );
    }

    @Test
    void deveLancarExcecaoParaAnoFuturo() {
        int proximoAno = Calendar.getInstance().get(Calendar.YEAR) + 1;
        List<Combustivel> combustiveis = List.of(Combustivel.GASOLINA);

        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrarCarro("Tesla", "Model Y", proximoAno, combustiveis, 400, 2.0)
        );
    }

    @Test
    void deveLancarExcecaoParaCavalosInvalidos() {
        List<Combustivel> combustiveis = List.of(Combustivel.GASOLINA);

        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrarCarro("Honda", "Civic", 2018, combustiveis, 0, 1.8)
        );
    }

    @Test
    void deveLancarExcecaoParaCilindradaInvalida() {
        List<Combustivel> combustiveis = List.of(Combustivel.GASOLINA);

        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrarCarro("Toyota", "Corolla", 2020, combustiveis, 120, -1.6)
        );
    }

    @Test
    void deveLancarExcecaoParaListaDeCombustiveisVazia() {
        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrarCarro("VW", "Gol", 2020, List.of(), 100, 1.6)
        );
    }

    @Test
    void deveLancarExcecaoParaListaDeCombustiveisNula() {
        assertThrows(IllegalArgumentException.class, () ->
                service.cadastrarCarro("VW", "Gol", 2020, null, 100, 1.6)
        );
    }
}
